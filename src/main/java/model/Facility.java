package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Facility {
    private int id;
    private int restaurantId;
    private String name;
    private String description;
    private String category;
    private String imagePath;
    private String status;
    private String createdAt;
    private String updatedAt;
}
