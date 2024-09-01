package model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
public class Payment {
    private int id;
    private int orderId;
    private String paymentMethod;  // "Credit Card", "Debit Card", "Cash", "Online"
    private String paymentStatus;  // "Pending", "Completed", "Failed"
    private double amount;
    private Timestamp paymentDate;
}
