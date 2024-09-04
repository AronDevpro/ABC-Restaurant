package model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReservationReport {
    private String restaurantName;
    private String date;
    private int numberOfReservations;
}
