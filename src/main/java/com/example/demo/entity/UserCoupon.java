package com.example.demo.entity;

import java.time.LocalDateTime;

public class UserCoupon {

	private Integer id;
	private Integer userId;
	private Integer couponId;
	private LocalDateTime expireAt;
	private LocalDateTime acquiredAt;

	private String name;
	private String code;
	private Integer discountRate;
	private Integer minAmount;

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

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public LocalDateTime getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(LocalDateTime expireAt) {
		this.expireAt = expireAt;
	}

	public LocalDateTime getAcquiredAt() {
		return acquiredAt;
	}

	public void setAcquiredAt(LocalDateTime acquiredAt) {
		this.acquiredAt = acquiredAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Integer discountRate) {
		this.discountRate = discountRate;
	}

	public Integer getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(Integer minAmount) {
		this.minAmount = minAmount;
	}
}
