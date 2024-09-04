package dao;

import dbConnection.CrudUtil;
import model.Restaurant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDao {
    public static void createRestaurant(Restaurant restaurant) throws ClassNotFoundException {
        try {
            String sql = "INSERT INTO restaurant (id, name, description, openTime, closeTime, address, phoneNumber, image, capacity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            CrudUtil.execute(sql, restaurant.getId(), restaurant.getName(), restaurant.getDescription(), restaurant.getOpenTime(), restaurant.getCloseTime(), restaurant.getAddress(), restaurant.getPhoneNumber(), restaurant.getImage(), restaurant.getCapacity());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to update a restaurant
    public static void updateRestaurant(Restaurant restaurant) throws ClassNotFoundException {
        try {
            String sql = "UPDATE restaurant SET name = ?, description = ?, openTime = ?, closeTime = ?, address = ?, phoneNumber = ?, image = ?, capacity = ? WHERE id = ?";
            CrudUtil.execute(sql, restaurant.getName(), restaurant.getDescription(), restaurant.getOpenTime(), restaurant.getCloseTime(), restaurant.getAddress(), restaurant.getPhoneNumber(), restaurant.getImage(), restaurant.getCapacity(), restaurant.getId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to delete a restaurant
    public static boolean deleteRestaurant(int id) throws ClassNotFoundException {
        String sql = "DELETE FROM restaurant WHERE id = ?";
        try {
            return CrudUtil.execute(sql, id);
        } catch (SQLException e) {
            System.err.println("SQL error occurred while deleting restaurant: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Method to get a restaurant by ID
    public static Restaurant getRestaurantById(int id) throws ClassNotFoundException {
        try {
            String sql = "SELECT * FROM restaurant WHERE id = ?";
            ResultSet resultSet = CrudUtil.execute(sql, id);

            if (resultSet.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(resultSet.getInt("id"));
                restaurant.setName(resultSet.getString("name"));
                restaurant.setDescription(resultSet.getString("description"));
                restaurant.setOpenTime(resultSet.getString("openTime"));
                restaurant.setCloseTime(resultSet.getString("closeTime"));
                restaurant.setAddress(resultSet.getString("address"));
                restaurant.setPhoneNumber(resultSet.getString("phoneNumber"));
                restaurant.setStatus(resultSet.getString("status"));
                restaurant.setImage(resultSet.getString("image"));
                restaurant.setCapacity(resultSet.getInt("capacity"));

                return restaurant;
            } else {
                return null;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the list of restaurants
    public static List<Restaurant> getRestaurantList(String search, int page, int size) throws ClassNotFoundException {
        try {
            int offset = (page - 1) * size;
            StringBuilder sql = new StringBuilder("SELECT * FROM restaurant WHERE 1=1");

            if (search != null && !search.isEmpty()) {
                sql.append(" AND (name LIKE ? OR phoneNumber LIKE ?)");
            }

            sql.append(" LIMIT ? OFFSET ?");

            List<Object> params = new ArrayList<>();
            if (search != null && !search.isEmpty()) {
                String searchParam = "%" + search + "%";
                params.add(searchParam);
                params.add(searchParam);
            }

            params.add(size);
            params.add(offset);

            ResultSet resultSet = CrudUtil.execute(sql.toString(), params.toArray());
            List<Restaurant> list = new ArrayList<>();
            while (resultSet.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(resultSet.getInt("id"));
                restaurant.setName(resultSet.getString("name"));
                restaurant.setDescription(resultSet.getString("description"));
                restaurant.setOpenTime(resultSet.getString("openTime"));
                restaurant.setCloseTime(resultSet.getString("closeTime"));
                restaurant.setAddress(resultSet.getString("address"));
                restaurant.setPhoneNumber(resultSet.getString("phoneNumber"));
                restaurant.setCapacity(resultSet.getInt("capacity"));
                restaurant.setStatus(resultSet.getString("status"));
                list.add(restaurant);
            }
            return list;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the count
    public static int getRestaurantCount(String search) throws ClassNotFoundException, SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM restaurant WHERE 1=1");

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (name LIKE ? OR phoneNumber LIKE ?)");
        }

        List<Object> params = new ArrayList<>();

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

    // Method to retrieve all restaurants
    public static List<Restaurant> getAllRestaurants() throws ClassNotFoundException {
        try {
            String sql = "SELECT * FROM restaurant";
            ResultSet resultSet = CrudUtil.execute(sql);

            List<Restaurant> restaurantList = new ArrayList<>();
            while (resultSet.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(resultSet.getInt("id"));
                restaurant.setName(resultSet.getString("name"));
                restaurant.setImage(resultSet.getString("image"));
                restaurant.setOpenTime(resultSet.getString("openTime"));
                restaurant.setCloseTime(resultSet.getString("closeTime"));
                restaurant.setAddress(resultSet.getString("address"));
                restaurant.setPhoneNumber(resultSet.getString("phoneNumber"));
                restaurantList.add(restaurant);
            }
            return restaurantList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
