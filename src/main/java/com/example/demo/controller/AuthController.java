package com.example.demo.controller;

import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.entity.UserCoupon;
import com.example.demo.form.LoginForm;
import com.example.demo.form.RegisterForm;
import com.example.demo.mapper.UserCouponMapper;
import com.example.demo.mapper.UserMapper;

import java.util.List;

@Controller
public class AuthController {

	private final UserMapper userMapper;
	private final UserCouponMapper userCouponMapper;

	// Constructor Injection
	public AuthController(UserMapper userMapper, UserCouponMapper userCouponMapper) {
		this.userMapper = userMapper;
		this.userCouponMapper = userCouponMapper;
	}

	// =========================
	// ユーザー登録
	// =========================

	@GetMapping("/register")
	public String registerForm(Model model) {

		model.addAttribute("registerForm", new RegisterForm());

		return "register";
	}

	@PostMapping("/register")
	public String register(
			@Valid @ModelAttribute RegisterForm registerForm,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {

		// メール重複チェック
		if (userMapper.findByEmail(registerForm.getEmail()).isPresent()) {
			result.rejectValue(
					"email",
					"error.email",
					"このメールアドレスは既に登録されています");
		}

		if (result.hasErrors()) {
			return "register";
		}

		User user = new User();
		user.setDisplayName(registerForm.getDisplayName());
		user.setEmail(registerForm.getEmail());

		// 本来はPasswordEncoder推奨
		user.setPassword(registerForm.getPassword());

		userMapper.insert(user);

		redirectAttributes.addFlashAttribute("successMessage", "アカウントが正常に作成されました。ログインしてください。");

		return "redirect:/login";
	}

	// =========================
	// ログイン
	// =========================

	@GetMapping("/login")
	public String loginForm(Model model) {

		model.addAttribute("loginForm", new LoginForm());

		return "login";
	}

	@PostMapping("/login")
	public String login(
			@Valid @ModelAttribute LoginForm loginForm,
			BindingResult result,
			HttpSession session,
			Model model) {

		if (result.hasErrors()) {
			return "login";
		}

		Optional<User> optionalUser = userMapper.findByEmail(loginForm.getEmail());

		if (optionalUser.isEmpty()) {

			model.addAttribute("loginError", "メールアドレスまたはパスワードが違います");

			return "login";
		}

		User user = optionalUser.get();

		if (!user.getPassword().equals(loginForm.getPassword())) {

			model.addAttribute("loginError", "メールアドレスまたはパスワードが違います");

			return "login";
		}

		// セッション保存
		session.setAttribute("loginUser", user);

		return "redirect:/";
	}

	// =========================
	// アカウント画面
	// =========================

	@GetMapping("/account")
	public String account(HttpSession session, Model model) {

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}

		// MyBatisのMapperを使用してデータを取得
		List<UserCoupon> userCoupons = userCouponMapper.findByUserId(loginUser.getId());

		model.addAttribute("user", loginUser);
		model.addAttribute("userCoupons", userCoupons);

		return "account";
	}

	@PostMapping("/account/address")
	public String updateAddress(
			@RequestParam("postalCode") String postalCode,
			@RequestParam("address") String address,
			@RequestParam("phoneNumber") String phoneNumber,
			HttpSession session,
			RedirectAttributes redirectAttributes) {

		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}

		loginUser.setPostalCode(postalCode);
		loginUser.setAddress(address);
		loginUser.setPhoneNumber(phoneNumber);

		userMapper.updateAddressInfo(loginUser);
		session.setAttribute("loginUser", loginUser); // Sync session

		redirectAttributes.addFlashAttribute("successMessage", "お届け先情報を更新しました。");

		return "redirect:/account";
	}

	// =========================
	// ログアウト
	// =========================

	@GetMapping("/logout")
	public String logout(HttpSession session) {

		session.invalidate();

		return "redirect:/login";
	}
}