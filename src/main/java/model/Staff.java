package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Staff extends User{
    private String position;
    private String NIC;
}
