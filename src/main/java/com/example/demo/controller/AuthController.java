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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.form.LoginForm;
import com.example.demo.form.RegisterForm;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

	private final UserRepository userRepository;

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
		if (userRepository.findByEmail(registerForm.getEmail()).isPresent()) {
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

		userRepository.save(user);

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

		Optional<User> optionalUser = userRepository.findByEmail(loginForm.getEmail());

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

		model.addAttribute("user", loginUser);

		return "account";
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