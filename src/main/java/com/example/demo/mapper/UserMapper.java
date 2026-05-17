package com.example.demo.mapper;

import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;
import com.example.demo.entity.User;

@Mapper
public interface UserMapper {

	@Select("SELECT * FROM users WHERE email = #{email}")
	Optional<User> findByEmail(String email);

	@Insert("INSERT INTO users (email, password, display_name, total_spent, converted_spent) " +
			"VALUES (#{email}, #{password}, #{displayName}, #{totalSpent}, #{convertedSpent})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(User user);

	@Update("UPDATE users SET postal_code = #{postalCode}, address = #{address}, phone_number = #{phoneNumber} WHERE id = #{id}")
	void updateAddressInfo(User user);

	@Update("UPDATE users SET total_spent = total_spent + #{amount} WHERE id = #{userId}")
	void addTotalSpent(@Param("userId") Integer userId, @Param("amount") Integer amount);
}
