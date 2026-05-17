package com.example.demo.service;

import java.nio.charset.StandardCharsets;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.demo.entity.User;
import com.example.demo.model.Cart;

@Service
public class EmailService {

	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;

	// @Autowired: Spring Bootが必要な部品（メール送信機やHTML作成機）を自動で探し出し、合体させる合図。メール送信のために必要。
	@Autowired
	public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
		// 自動デリバリーされた部品を、このクラスの中でいつでも使えるようにはめ込んで固定（初期化）する
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
	}

	public void sendOrderEmail(User user, Cart cart, String orderNo, int discountAmount, int finalTotalPrice, String confirmUrl) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, 
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, 
					StandardCharsets.UTF_8.name());

			// Prepare Thymeleaf context variables
			Context context = new Context();
			context.setVariable("user", user);
			context.setVariable("cart", cart);
			context.setVariable("orderNo", orderNo);
			context.setVariable("discountAmount", discountAmount);
			context.setVariable("finalTotalPrice", finalTotalPrice);
			context.setVariable("confirmUrl", confirmUrl);

			// Render Thymeleaf template to String
			String htmlContent = templateEngine.process("mail/order_email", context);

			helper.setTo(user.getEmail());
			helper.setFrom("kotan.ec2026@gmail.com");
			helper.setSubject("【KOTAN】ご注文ありがとうございました（注文番号：" + orderNo + "）");
			helper.setText(htmlContent, true); // true indicates HTML content

			mailSender.send(message);
			System.out.println("注文完了メールを送信しました。宛先: " + user.getEmail());
		} catch (MessagingException e) {
			System.err.println("メール送信エラー: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
