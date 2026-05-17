package com.example.demo.entity;

import java.time.LocalDateTime;

public class Order {
	private Integer id;
	private Integer userId;
	private Integer totalPrice;
	private LocalDateTime orderedAt;

	// Constructors
	public Order() {}

	public Order(Integer userId, Integer totalPrice) {
		this.userId = userId;
		this.totalPrice = totalPrice;
		this.orderedAt = LocalDateTime.now();
	}

	// Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public LocalDateTime getOrderedAt() {
		return orderedAt;
	}

	public void setOrderedAt(LocalDateTime orderedAt) {
		this.orderedAt = orderedAt;
	}
}
