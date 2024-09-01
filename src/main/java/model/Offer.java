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
    private String category;
    private String imagePath;
    private String status;
}
