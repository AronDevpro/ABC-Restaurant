package model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Order {
    private int id;
    private String orderUUID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String deliverDate;
    private String deliverTime;
    private String deliveryMethod;
    private String restaurantSelect;
    private String streetAddress;
    private String zip;
    private String city;
    private double total;
    private int customerId;
    private String status;

    public Order() {
        this.orderUUID = UUID.randomUUID().toString();
    }
}
