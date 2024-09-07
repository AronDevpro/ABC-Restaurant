package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private int id;
    private int orderId;
    private String paymentMethod;
    private double total;
    private String status;
}
