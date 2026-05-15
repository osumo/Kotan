package com.example.demo.mapper;

import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import com.example.demo.entity.User;

@Mapper
public interface UserMapper {

	@Select("SELECT * FROM users WHERE email = #{email}")
	Optional<User> findByEmail(String email);

	@Insert("INSERT INTO users (email, password, display_name, total_spent, converted_spent) " +
			"VALUES (#{email}, #{password}, #{displayName}, #{totalSpent}, #{convertedSpent})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(User user);
}
