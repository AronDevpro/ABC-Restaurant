package model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Query {
    private int id;
    private String subject;
    private String description;
    private String phoneNumber;
    private String orderId;
    private String response;
    private int customerId;
    private int staffId;
    private String status;
    private Timestamp updatedAt;
}
