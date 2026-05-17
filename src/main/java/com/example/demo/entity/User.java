package com.example.demo.entity;

import java.time.LocalDateTime;

public class User {

	private Integer id;
	private String email;
	private String password;
	private String displayName;
	private String postalCode;
	private String address;
	private String phoneNumber;
	private Integer totalSpent;
	private Integer convertedSpent;
	private LocalDateTime createdAt;

	// Constructor
	public User() {
		this.totalSpent = 0;
		this.convertedSpent = 0;
	}

	// Getter and Setter
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Integer getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(Integer totalSpent) {
		this.totalSpent = totalSpent;
	}

	public Integer getConvertedSpent() {
		return convertedSpent;
	}

	public void setConvertedSpent(Integer convertedSpent) {
		this.convertedSpent = convertedSpent;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}