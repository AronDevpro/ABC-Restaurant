package model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Gallery {
    private int id;
    private String title;
    private String description;
    private String category;
    private String imagePath;
    private String status;
}
