package model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Query {
    private int id;
    private String title;
    private String description;
    private String response;
    private int customerId;
    private int employeeId;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
