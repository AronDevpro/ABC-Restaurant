package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItems {
    private int orderId;
    private String item;
    private int quantity;

    public OrderItems(int orderId, String item, int quantity) {
        this.orderId = orderId;
        this.item = item;
        this.quantity = quantity;
    }
}
