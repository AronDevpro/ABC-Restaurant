package model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Cart {
    private String productId;
    private String name;
    private int quantity;
    private double price;
}
