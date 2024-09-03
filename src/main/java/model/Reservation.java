package model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

@Getter
@Setter
public class Reservation {
    private int id;
    private String reservationId;
    private int customerId;
    private int restaurantId;
    private String name;
    private String phoneNumber;
    private String date;
    private String time;
    private int noOfPeople;
    private String status;

    public Reservation() {
        this.reservationId = UUID.randomUUID().toString();
    }
}
