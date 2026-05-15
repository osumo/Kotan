package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Product;

@Mapper
public interface ProductMapper {

	/** 全商品を取得する */
	List<Product> findAll();

	/** 検索条件を指定して商品を取得する */
	List<Product> search(@Param("keyword") String keyword, @Param("categoryId") Integer categoryId, @Param("minPrice") Integer minPrice, @Param("maxPrice") Integer maxPrice);

	/** IDで商品を1件取得する */
	Product findById(Integer id);
}