package factory;

import model.Admin;
import model.Customer;
import model.Staff;
import model.User;

public class UserFactory {
    public static User createUser(String userType) {
        switch (userType.toLowerCase()) {
            case "admin":
                return new Admin();
            case "staff":
                return new Staff();
            case "customer":
                return new Customer();
            default:
                throw new IllegalArgumentException("Unknown user type");
        }
    }
}
