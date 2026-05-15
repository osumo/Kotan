package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Integer productId;
    private String name;
    private Integer price;
    private Integer quantity;
    private String imageUrl;

    public int getSubtotal() {
        return (price != null ? price : 0) * quantity;
    }
}
