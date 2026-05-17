package com.example.demo.entity;

public class OrderItem {
	private Integer id;
	private Integer orderId;
	private Integer productId;
	private Integer quantity;
	private Integer priceAtPurchase;

	// Constructors
	public OrderItem() {}

	public OrderItem(Integer orderId, Integer productId, Integer quantity, Integer priceAtPurchase) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.priceAtPurchase = priceAtPurchase;
	}

	// Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPriceAtPurchase() {
		return priceAtPurchase;
	}

	public void setPriceAtPurchase(Integer priceAtPurchase) {
		this.priceAtPurchase = priceAtPurchase;
	}
}
