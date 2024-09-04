package dao;

import dbConnection.CrudUtil;
import model.Admin;
import model.Staff;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    // method to register user based on account type
    public static void registerCustomer(User user) throws ClassNotFoundException {
        try {
            String sql = "INSERT INTO users (id, firstName, lastName, email, password, address, phoneNumber, accountType, NIC, position) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            CrudUtil.execute(sql, user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getAddress(), user.getPhoneNumber(),
                    user.getAccountType(), user instanceof Admin ? ((Admin) user).getNIC() : user instanceof Staff ? ((Staff) user).getNIC() : null,
                    user instanceof Staff ? ((Staff) user).getPosition() : null);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    // method to login a user
    public static User login(String email, String password) throws ClassNotFoundException {
        try {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            ResultSet resultSet = CrudUtil.execute(sql, email, password);

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setAddress(resultSet.getString("address"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setAccountType(resultSet.getString("accountType"));
                user.setStatus(resultSet.getString("status"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // method to update a user
    public static void updateUser(User user) throws ClassNotFoundException {
        try {
            String sql = "UPDATE users SET firstName = ?, lastName = ?, email = ?, password = ?, address = ?, phoneNumber = ?, accountType = ?, NIC = ?, position = ? WHERE id = ?";
            CrudUtil.execute(sql, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getAddress(), user.getPhoneNumber(),
                    user.getAccountType(), user instanceof Admin ? ((Admin) user).getNIC() : user instanceof Staff ? ((Staff) user).getNIC() : null,
                    user instanceof Staff ? ((Staff) user).getPosition() : null, user.getId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // method to delete a user
    public static boolean deleteUser(int userId) throws ClassNotFoundException {
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            return CrudUtil.execute(sql, userId);
        } catch (SQLException e) {
            System.err.println("SQL error occurred while deleting user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // method to get a user by ID
    public static User getUserById(int userId) throws ClassNotFoundException {
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            ResultSet resultSet = CrudUtil.execute(sql, userId);

            if (resultSet.next()) {
                String accountType = resultSet.getString("accountType");
                User user;

                // Instantiate the correct subclass based on accountType
                if ("Admin".equalsIgnoreCase(accountType)) {
                    user = new Admin();
                    ((Admin) user).setNIC(resultSet.getString("nic"));
                } else if ("Staff".equalsIgnoreCase(accountType)) {
                    user = new Staff();
                    ((Staff) user).setNIC(resultSet.getString("nic"));
                    ((Staff) user).setPosition(resultSet.getString("position"));
                } else {
                    user = new User();  // For general or unrecognized account types
                }

                // Set common user properties
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setAddress(resultSet.getString("address"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setAccountType(accountType);
                user.setStatus(resultSet.getString("status"));

                return user;
            } else {
                return null;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the list of users
    public static List<User> getUserList(String search, String filterUserType, int page, int size) throws ClassNotFoundException {
        try {
            int offset = (page - 1) * size;
            StringBuilder sql = new StringBuilder("SELECT * FROM users WHERE 1=1");

            if (search != null && !search.isEmpty()) {
                sql.append(" AND (firstName LIKE ? OR lastName LIKE ? OR email LIKE ?)");
            }

            if (filterUserType != null && !filterUserType.isEmpty()) {
                sql.append(" AND accountType = ?");
            }

            sql.append(" LIMIT ? OFFSET ?");

            List<Object> params = new ArrayList<>();
            if (search != null && !search.isEmpty()) {
                String searchParam = "%" + search + "%";
                params.add(searchParam);
                params.add(searchParam);
                params.add(searchParam);
            }

            if (filterUserType != null && !filterUserType.isEmpty()) {
                params.add(filterUserType);
            }

            params.add(size);
            params.add(offset);

            ResultSet resultSet = CrudUtil.execute(sql.toString(), params.toArray());
            List<User> userList = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setAccountType(resultSet.getString("accountType"));
                user.setStatus(resultSet.getString("status"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the count
    public static int getUserCount(String search, String filterUserType) throws ClassNotFoundException, SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM users WHERE 1=1");

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (firstName LIKE ? OR lastName LIKE ? OR email LIKE ?)");
        }

        if (filterUserType != null && !filterUserType.isEmpty()) {
            sql.append(" AND accountType = ?");
        }

        List<Object> params = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            String searchParam = "%" + search + "%";
            params.add(searchParam);
            params.add(searchParam);
            params.add(searchParam);
        }

        if (filterUserType != null && !filterUserType.isEmpty()) {
            params.add(filterUserType);
        }

        ResultSet resultSet = CrudUtil.execute(sql.toString(), params.toArray());
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    // Method to get the count
    public static int getCustomerCount(String accountType) throws ClassNotFoundException, SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE accountType = ?";

        ResultSet resultSet = CrudUtil.execute(sql, accountType);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }
}
