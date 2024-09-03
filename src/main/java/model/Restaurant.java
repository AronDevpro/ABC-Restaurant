package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Restaurant {
    private int id;
    private String name;
    private String description;
    private String openTime;
    private String closeTime;
    private String address;
    private String phoneNumber;
    private String image;
    private int capacity;
    private String status;
    private String createdAt;
    private String updatedAt;
}
