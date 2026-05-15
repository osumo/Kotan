package com.example.demo.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterForm {

	@NotBlank(message = "表示名を入力してください")
	private String displayName;

	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "有効なメールアドレス形式で入力してください")
	private String email;

	@NotBlank(message = "パスワードを入力してください")
	@Size(min = 8, message = "パスワードは8文字以上で入力してください")
	private String password;
}