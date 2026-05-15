package com.example.demo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.example.demo.entity.UserCoupon;

@Mapper
public interface UserCouponMapper {

	@Select("SELECT uc.*, c.name, c.code, c.discount_rate, c.min_amount " +
			"FROM user_coupons uc " +
			"JOIN coupons c ON uc.coupon_id = c.id " +
			"WHERE uc.user_id = #{userId}")
	List<UserCoupon> findByUserId(Integer userId);
}
