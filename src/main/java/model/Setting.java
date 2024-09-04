package model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Setting {
    private int id;
    private String siteName;
    private String description;
    private String logoPath;
    private String siteEmail;
    private String siteStreetAddress;
    private String siteZip;
    private String siteCity;
    private String serverEmail;
    private String serverPassword;
}
