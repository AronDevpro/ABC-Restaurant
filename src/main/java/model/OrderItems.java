package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItems {
    private int orderId;
    private String item;
    private int quantity;


}
