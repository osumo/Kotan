package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

	// 担当者: D
	// 目的: 商品一覧ページの表示
	// 補足: DBから全商品(または条件に合う商品)のリストを取得し、Modelに詰める処理が今後必要です。
	//      一覧表示のためのループ処理(th:each)をビュー側で使えるようにList型のデータを渡してください。
	@GetMapping("/products")
	public String products() {
		return "products";
	}

	// 担当者: D
	// 目的: 商品詳細ページの表示
	// 補足: パス変数{id}を利用してDBから特定の商品情報を取得し、Modelに詰める処理が今後必要です。
	//      商品が見つからなかった場合のエラーハンドリング（例：一覧へ戻すなど）も検討してください。
	@GetMapping("/product/{id}")
	public String productDetail(@PathVariable("id") Integer id) {
		// TODO: idを使ったDB検索処理
		return "product_detail";
	}
}
