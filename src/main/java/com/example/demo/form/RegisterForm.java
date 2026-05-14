package com.example.demo.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RegisterForm {

	@NotBlank
	private String displayName;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;
}