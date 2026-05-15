package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem newItem) {
        for (CartItem item : items) {
            if (item.getProductId().equals(newItem.getProductId())) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }
        items.add(newItem);
    }

    public void removeItem(Integer productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
    }

    public int getTotalPrice() {
        return items.stream().mapToInt(CartItem::getSubtotal).sum();
    }

    public void clear() {
        items.clear();
    }
}
