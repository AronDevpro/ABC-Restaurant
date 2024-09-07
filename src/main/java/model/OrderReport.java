package model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderReport {
    private String deliverMethod;
    private String deliverDate;
    private Double totalRevenue;
    private int numberOfOrders;
}
