package model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
public class Reservation {
    private int id;
    private int offerId;
    private Date date;
    private Time time;
    private int customerId;
    private int restaurantId;

}
