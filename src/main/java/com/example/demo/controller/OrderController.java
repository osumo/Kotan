package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.User;
import com.example.demo.entity.UserCoupon;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.UserCouponMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.service.EmailService;

@Controller
public class OrderController {

	private final UserMapper userMapper;
	private final UserCouponMapper userCouponMapper;
	private final OrderMapper orderMapper;
	private final EmailService emailService;

	@Autowired
	public OrderController(UserMapper userMapper, UserCouponMapper userCouponMapper, 
			OrderMapper orderMapper, EmailService emailService) {
		this.userMapper = userMapper;
		this.userCouponMapper = userCouponMapper;
		this.orderMapper = orderMapper;
		this.emailService = emailService;
	}

	@GetMapping("/order")
	public String viewOrderForm(HttpSession session, Model model) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}

		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null || cart.getItems().isEmpty()) {
			return "redirect:/cart";
		}

		// Get active user coupons
		List<UserCoupon> userCoupons = userCouponMapper.findByUserId(loginUser.getId());

		model.addAttribute("user", loginUser);
		model.addAttribute("cart", cart);
		model.addAttribute("userCoupons", userCoupons);

		return "order";
	}

	@PostMapping("/order")
	public String placeOrder(
			@RequestParam("postalCode") String postalCode,
			@RequestParam("address") String address,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam(value = "saveAddress", defaultValue = "false") boolean saveAddress,
			@RequestParam(value = "userCouponId", required = false) Integer userCouponId,
			HttpSession session,
			RedirectAttributes redirectAttributes) {

		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}

		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null || cart.getItems().isEmpty()) {
			return "redirect:/cart";
		}

		// 1. Update default address in DB if the checkbox is checked
		if (saveAddress) {
			loginUser.setPostalCode(postalCode);
			loginUser.setAddress(address);
			loginUser.setPhoneNumber(phoneNumber);
			userMapper.updateAddressInfo(loginUser);
			session.setAttribute("loginUser", loginUser); // Sync session
		}

		// 2. Coupon calculation
		int discountAmount = 0;
		if (userCouponId != null && userCouponId > 0) {
			List<UserCoupon> userCoupons = userCouponMapper.findByUserId(loginUser.getId());
			UserCoupon appliedCoupon = userCoupons.stream()
					.filter(uc -> uc.getId().equals(userCouponId))
					.findFirst()
					.orElse(null);

			if (appliedCoupon != null) {
				// Verify minimum purchase requirements
				if (cart.getTotalPrice() >= appliedCoupon.getMinAmount()) {
					discountAmount = (int) (cart.getTotalPrice() * (appliedCoupon.getDiscountRate() / 100.0));
				}
			}
		}

		int finalTotalPrice = Math.max(0, cart.getTotalPrice() - discountAmount);

		// 3. Save Order inside orders table
		Order order = new Order(loginUser.getId(), finalTotalPrice);
		orderMapper.insertOrder(order); // Generates auto-incremented id

		// 4. Save items inside order_items table
		for (CartItem item : cart.getItems()) {
			OrderItem orderItem = new OrderItem(order.getId(), item.getProductId(), item.getQuantity(), item.getPrice());
			orderMapper.insertOrderItem(orderItem);
		}

		// 5. Consume coupon if applied
		if (userCouponId != null && userCouponId > 0 && discountAmount > 0) {
			UserCoupon userCoupon = userCouponMapper.findById(userCouponId);
			if (userCoupon != null) {
				orderMapper.insertAppliedCoupon(order.getId(), userCoupon.getCouponId());
				userCouponMapper.deleteById(userCouponId); // Remove from inventory
			}
		}

		// 6. Generate order confirmation number
		String orderNo = "KT-" + (System.currentTimeMillis() % 1000000) + "-" + (int) (Math.random() * 900 + 100);

		// Generate dynamic confirmation link for demo review approval
		String confirmUrl = "http://localhost:8080/order/confirm?userId=" + loginUser.getId() 
				+ "&amount=" + finalTotalPrice + "&orderNo=" + orderNo;

		// 7. Dispatch the styled HTML email using Gmail SMTP
		emailService.sendOrderEmail(loginUser, cart, orderNo, discountAmount, finalTotalPrice, confirmUrl);

		// 8. Clean up cart
		cart.clear();
		session.setAttribute("cart", cart);

		// Pass order details to the redirect complete page safely via Flash attributes
		redirectAttributes.addFlashAttribute("orderNo", orderNo);
		redirectAttributes.addFlashAttribute("finalTotalPrice", finalTotalPrice);
		redirectAttributes.addFlashAttribute("discountAmount", discountAmount);
		redirectAttributes.addFlashAttribute("postalCode", postalCode);
		redirectAttributes.addFlashAttribute("address", address);
		redirectAttributes.addFlashAttribute("isCompleted", true);

		return "redirect:/order/complete";
	}

	@GetMapping("/order/complete")
	public String viewOrderComplete(HttpSession session, Model model) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}
		return "order_complete";
	}

	@GetMapping("/order/confirm")
	public String confirmOrder(
			@RequestParam("userId") Integer userId,
			@RequestParam("amount") Integer amount,
			@RequestParam("orderNo") String orderNo,
			HttpSession session,
			Model model) {

		// Increment total_spent in DB (demo payment authorization mock)
		userMapper.addTotalSpent(userId, amount);

		// Sync session user attributes
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser != null && loginUser.getId().equals(userId)) {
			loginUser.setTotalSpent(loginUser.getTotalSpent() + amount);
			session.setAttribute("loginUser", loginUser);
		}

		model.addAttribute("orderNo", orderNo);
		model.addAttribute("amount", amount);

		return "order_confirm_complete";
	}
}
