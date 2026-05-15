package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

@Controller
public class ProductController {

	// ←ここ（フィールド）
	private final ProductService productService;

	// ←ここ（コンストラクタ）
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	//商品一覧
	@GetMapping("/products")
	public String products(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) Integer minPrice,
			@RequestParam(required = false) Integer maxPrice,
			Model model) {
		List<Product> products = productService.searchProducts(keyword, categoryId, minPrice, maxPrice);
		model.addAttribute("products", products);
		return "products";
	}

	// 商品詳細ページの表示
	@GetMapping("/product/{id}")
	public String productDetail(@PathVariable Integer id, Model model) {
		// TODO: idを使ったDB検索処理
		Product product = productService.getProductById(id);

		if (product == null) {
			model.addAttribute("error", "商品が見つかりません");
			return "product-not-found";
		}

		model.addAttribute("product", product);
		return "product_detail";
	}
}
