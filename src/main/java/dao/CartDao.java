package dao;

import model.Cart;
import java.util.HashMap;
import java.util.Map;

public class CartDao {
    private Map<String, Cart> items = new HashMap<>();

    public void addItem(String productId, int quantity, String name, double price) {
        Cart cartItem = items.get(productId);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new Cart();
            cartItem.setProductId(productId);
            cartItem.setName(name);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(price);
            items.put(productId, cartItem);
        }
    }

    public Map<String, Cart> getItems() {
        return items;
    }

    public void removeItem(String productId) {
        items.remove(productId);
    }

    public double getTotal() {
        return items.values().stream()
                .mapToDouble(cartItem -> cartItem.getPrice() * cartItem.getQuantity())
                .sum();
    }

    public void clearCart() {
        items.clear();
    }
}
