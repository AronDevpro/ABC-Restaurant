package model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
public class Order {
    private int id;
    private Date date;
    private Time time;
    private double totalAmount;
    private int customerId;
    private int restaurantId;
    private List<MenuItem> menuItems;
}
