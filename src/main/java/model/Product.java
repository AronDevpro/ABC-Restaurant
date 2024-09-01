package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private int id;
    private String name;
    private String description;
    private String category;
    private String imagePath;
    private Double price;
    private String status;
    private String createdAt;
    private String updatedAt;
}
