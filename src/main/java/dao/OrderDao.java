package dao;

import dbConnection.CrudUtil;
import model.Order;
import model.OrderItems;
import model.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    // Method to get the orders with user id
    public static List<Order> getOrdersByCustomerId(int customerId, String search, int page, int size) throws ClassNotFoundException {
        List<Order> orders = new ArrayList<>();
        try {
            int offset = (page - 1) * size;
            StringBuilder sql = new StringBuilder("SELECT * FROM orders WHERE customerId = ?");

            if (search != null && !search.isEmpty()) {
                sql.append(" AND (orderUUID LIKE ? OR restaurantSelect LIKE ?)");
            }

            sql.append(" LIMIT ? OFFSET ?");

            List<Object> params = new ArrayList<>();
            params.add(customerId);
            if (search != null && !search.isEmpty()) {
                String searchParam = "%" + search + "%";
                params.add(searchParam);
                params.add(searchParam);
            }
            params.add(size);
            params.add(offset);

            ResultSet rs = CrudUtil.execute(sql.toString(), params.toArray());
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setOrderUUID(rs.getString("orderUUID"));
                order.setEmail(rs.getString("email"));
                order.setPhoneNumber(rs.getString("phoneNumber"));
                order.setDeliverDate(rs.getString("deliverDate"));
                order.setDeliverTime(rs.getString("deliverTime"));
                order.setDeliveryMethod(rs.getString("deliveryMethod"));
                order.setRestaurantSelect(rs.getString("restaurantSelect"));
                order.setPaymentMethod(rs.getString("paymentMethod"));
                order.setStatus(rs.getString("status"));
                order.setTotal(rs.getDouble("total"));
                order.setCustomerId(customerId);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    // Method to get the count of order
    public static int getOrdersCount(int customerId, String search) throws ClassNotFoundException, SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM orders WHERE customerId = ?");

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (orderUUID LIKE ? OR restaurantSelect LIKE ?)");
        }

        List<Object> params = new ArrayList<>();
        params.add(customerId);
        if (search != null && !search.isEmpty()) {
            String searchParam = "%" + search + "%";
            params.add(searchParam);
            params.add(searchParam);
        }

        ResultSet resultSet = CrudUtil.execute(sql.toString(), params.toArray());
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    // Method to get the order items by order id
    public static List<OrderItems> getOrderItemsByOrderId(int orderId) throws ClassNotFoundException {
        List<OrderItems> orderItemsList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM orderItems WHERE orderId = ?";
            ResultSet rs = CrudUtil.execute(sql, orderId);

            while (rs.next()) {
                OrderItems orderItems = new OrderItems();
                orderItems.setOrderId(rs.getInt("orderId"));
                orderItems.setItem(rs.getString("item"));
                orderItems.setQuantity(rs.getInt("quantity"));
                orderItemsList.add(orderItems);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderItemsList;
    }

    // Method to change status
    public static void updateStatus(int id, String status) throws ClassNotFoundException {
        try {
            String sql = "UPDATE orders SET status = ? WHERE id = ?";
            CrudUtil.execute(sql, status, id);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the list of orders
    public static List<Order> getOrderList(String search, String filterStatusType, int page, int size) throws ClassNotFoundException {
        try {
            int offset = (page - 1) * size;
            StringBuilder sql = new StringBuilder("SELECT * FROM orders WHERE 1=1");

            if (search != null && !search.isEmpty()) {
                sql.append(" AND (id LIKE ? OR email LIKE ? OR phoneNumber LIKE ?)");
            }

            if (filterStatusType != null && !filterStatusType.isEmpty()) {
                sql.append(" AND status = ?");
            }

            sql.append(" LIMIT ? OFFSET ?");

            List<Object> params = new ArrayList<>();
            if (search != null && !search.isEmpty()) {
                String searchParam = "%" + search + "%";
                params.add(searchParam);
                params.add(searchParam);
                params.add(searchParam);
            }

            if (filterStatusType != null && !filterStatusType.isEmpty()) {
                params.add(filterStatusType);
            }

            params.add(size);
            params.add(offset);

            ResultSet resultSet = CrudUtil.execute(sql.toString(), params.toArray());
            List<Order> orderList = new ArrayList<>();

            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setFirstName(resultSet.getString("firstName"));
                order.setLastName(resultSet.getString("lastName"));
                order.setPhoneNumber(resultSet.getString("phoneNumber"));
                order.setDeliveryMethod(resultSet.getString("deliveryMethod"));
                order.setDeliverDate(resultSet.getString("deliverDate"));
                order.setDeliverTime(resultSet.getString("deliverTime"));
                order.setStatus(resultSet.getString("status"));
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the count
    public static int getOrderCount(String search, String filterStatusType) throws ClassNotFoundException, SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM orders WHERE 1=1");

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (id LIKE ? OR email LIKE ? OR phoneNumber LIKE ?)");
        }

        if (filterStatusType != null && !filterStatusType.isEmpty()) {
            sql.append(" AND status = ?");
        }

        List<Object> params = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            String searchParam = "%" + search + "%";
            params.add(searchParam);
            params.add(searchParam);
            params.add(searchParam);
        }

        if (filterStatusType != null && !filterStatusType.isEmpty()) {
            params.add(filterStatusType);
        }

        ResultSet resultSet = CrudUtil.execute(sql.toString(), params.toArray());
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    // Method to get the orders by id
    public static Order getOrderById(int id) throws ClassNotFoundException {
        try {
            String sql = "SELECT * FROM orders WHERE id = ?";
            ResultSet resultSet = CrudUtil.execute(sql, id);
            if (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setFirstName(resultSet.getString("firstName"));
                order.setLastName(resultSet.getString("lastName"));
                order.setEmail(resultSet.getString("email"));
                order.setPhoneNumber(resultSet.getString("phoneNumber"));
                order.setDeliveryMethod(resultSet.getString("deliveryMethod"));
                order.setDeliverDate(resultSet.getString("deliverDate"));
                order.setDeliverTime(resultSet.getString("deliverTime"));
                order.setRestaurantSelect(resultSet.getString("restaurantSelect"));
                order.setStreetAddress(resultSet.getString("streetAddress"));
                order.setCity(resultSet.getString("zip"));
                order.setZip(resultSet.getString("city"));
                order.setPaymentMethod(resultSet.getString("paymentMethod"));
                order.setTotal(resultSet.getDouble("total"));
                order.setStatus(resultSet.getString("status"));
                return order;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the count
    public static int getOrderCount() throws ClassNotFoundException, SQLException {
        String sql = "SELECT COUNT(*) FROM orders WHERE 1=1";

        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    // Method to get the orders filter
    public static List<Order> getOrdersByFilter() throws ClassNotFoundException {
        List<Order> orders = new ArrayList<>();
        try {
            String sql = "SELECT * FROM orders ORDER BY createdAt DESC LIMIT 10";

            ResultSet rs = CrudUtil.execute(sql);
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setPhoneNumber(rs.getString("phoneNumber"));
                order.setFirstName(rs.getString("firstName"));
                order.setLastName(rs.getString("lastName"));
                order.setDeliveryMethod(rs.getString("deliveryMethod"));
                order.setPaymentMethod(rs.getString("paymentMethod"));
                order.setTotal(rs.getDouble("total"));
                order.setCustomerId(rs.getInt("customerId"));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    // Method to get the pending count
    public static int getPendingOrderCount() throws ClassNotFoundException, SQLException {
        String sql = "SELECT COUNT(*) FROM orders WHERE status = 'pending'";

        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }
}
