package dao;

import dbConnection.CrudUtil;
import model.Order;
import model.OrderItems;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDao {
    // Method to create a new order
    public static void createOrder(Order order) throws ClassNotFoundException {
        try {
            String sql = "INSERT INTO orders (orderUUID, firstName, lastName, email, phoneNumber, deliverDate, deliverTime, deliveryMethod, restaurantSelect, streetAddress, zip, city, paymentMethod, total, customerId) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            CrudUtil.execute(sql,
                    order.getOrderUUID(),
                    order.getFirstName(),
                    order.getLastName(),
                    order.getEmail(),
                    order.getPhoneNumber(),
                    order.getDeliverDate(),
                    order.getDeliverTime(),
                    order.getDeliveryMethod(),
                    order.getRestaurantSelect() != null ? order.getRestaurantSelect() : null,
                    order.getStreetAddress() != null ? order.getStreetAddress() : null,
                    order.getZip() != null ? order.getZip() : null,
                    order.getCity() != null ? order.getCity() : null,
                    order.getPaymentMethod(),
                    order.getTotal(),
                    order.getCustomerId()
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    // Method to get the last inserted order ID
    public static int getLastOrderId() throws ClassNotFoundException {
        try {
            String sql = "SELECT LAST_INSERT_ID()";
            ResultSet rs = CrudUtil.execute(sql);
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve last order ID.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the last inserted order ID
    public static String getUUID(int id) throws ClassNotFoundException {
        try {
            String sql = "SELECT orderUUID FROM orders WHERE id = ?";
            ResultSet rs = CrudUtil.execute(sql, id);
            if (rs.next()) {
                return rs.getString("orderUUID");
            } else {
                throw new SQLException("Unable to retrieve order UUID.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to add order items
    public static void addOrderItems(OrderItems orderItems) throws ClassNotFoundException {
        try {
            String sql = "INSERT INTO orderItems (orderId, item, quantity) VALUES (?, ?, ?)";
            CrudUtil.execute(sql, orderItems.getOrderId(), orderItems.getItem(), orderItems.getQuantity());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
