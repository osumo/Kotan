package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;

@Mapper
public interface OrderMapper {

	@Insert("INSERT INTO orders (user_id, total_price, ordered_at) VALUES (#{userId}, #{totalPrice}, #{orderedAt})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertOrder(Order order);

	@Insert("INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) " +
			"VALUES (#{orderId}, #{productId}, #{quantity}, #{priceAtPurchase})")
	void insertOrderItem(OrderItem orderItem);

	@Insert("INSERT INTO applied_coupons (order_id, coupon_id) VALUES (#{orderId}, #{couponId})")
	void insertAppliedCoupon(@Param("orderId") Integer orderId, @Param("couponId") Integer couponId);
}
