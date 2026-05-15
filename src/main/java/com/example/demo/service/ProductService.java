package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;

@Service
public class ProductService {

	private final ProductMapper productMapper;

	public ProductService(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	public List<Product> getProducts() {
		return productMapper.findAll();
	}

	public List<Product> searchProducts(String keyword, Integer categoryId, Integer minPrice, Integer maxPrice) {
		return productMapper.search(keyword, categoryId, minPrice, maxPrice);
	}

	public Product getProductById(Integer id) {
		return productMapper.findById(id);
	}
}
