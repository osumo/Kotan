package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Product;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {

    private final ProductService productService;
    private final HttpSession session;

    @Autowired
    public CartController(ProductService productService, HttpSession session) {
        this.productService = productService;
        this.session = session;
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        Cart cart = getCartFromSession();
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Integer productId, @RequestParam(value = "quantity", defaultValue = "1") int quantity) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            Cart cart = getCartFromSession();
            CartItem item = new CartItem(product.getId(), product.getName(), product.getPrice(), quantity, product.getImageUrl());
            cart.addItem(item);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("productId") Integer productId) {
        Cart cart = getCartFromSession();
        cart.removeItem(productId);
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    private Cart getCartFromSession() {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}
