package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Offer {
    private int id;
    private String offerName;
    private String promoCode;
    private double discountPercentage;
}
