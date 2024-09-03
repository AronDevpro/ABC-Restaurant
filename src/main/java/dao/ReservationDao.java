package dao;

import dbConnection.CrudUtil;
import dbConnection.OutputParam;
import model.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao {

    // Method to check availability
    public static String checkAvailability(int restaurantId, String date, String time) throws SQLException, ClassNotFoundException {
        String resultMessage = "";

        String sql = "{CALL check_availability(?, ?, ?, ?, ?)}";

        Object[] results = CrudUtil.executeStoredProcedure(
                sql,
                restaurantId,
                date,
                time,
                new OutputParam(java.sql.Types.INTEGER),
                new OutputParam(java.sql.Types.VARCHAR)
        );

        int result = (int) results[3];
        resultMessage = (String) results[4];

        if (result == 1) {
            return "available";
        } else {
            return resultMessage;
        }
    }

    // Method to create reservation
    public static void createReservation(Reservation reservation) throws ClassNotFoundException {
        try {
            String sql = "INSERT INTO reservation (reservationId, name, phoneNumber, date, time, noOfPeople, customerId, restaurantId) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            CrudUtil.execute(sql,
                    reservation.getReservationId(),
                    reservation.getName(),
                    reservation.getPhoneNumber(),
                    reservation.getDate(),
                    reservation.getTime(),
                    reservation.getNoOfPeople(),
                    reservation.getCustomerId(),
                    reservation.getRestaurantId()
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the last order id
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

    // Method to get the reservation id
    public static String getReservationId(int id) throws ClassNotFoundException {
        try {
            String sql = "SELECT reservationId FROM reservation WHERE id = ?";
            ResultSet rs = CrudUtil.execute(sql, id);
            if (rs.next()) {
                return rs.getString("reservationId");
            } else {
                throw new SQLException("Unable to retrieve Reservation Id.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the reservations with user id
    public static List<Reservation> getReservationsByCustomerId(int customerId, String search, int page, int size) throws ClassNotFoundException {
        List<Reservation> reservations = new ArrayList<>();
        try {
            int offset = (page - 1) * size;
            StringBuilder sql = new StringBuilder("SELECT * FROM reservation WHERE customerId = ?");

            if (search != null && !search.isEmpty()) {
                sql.append(" AND (reservationId LIKE ?)");
            }

            sql.append(" LIMIT ? OFFSET ?");

            List<Object> params = new ArrayList<>();
            params.add(customerId);
            if (search != null && !search.isEmpty()) {
                String searchParam = "%" + search + "%";
                params.add(searchParam);
            }
            params.add(size);
            params.add(offset);

            ResultSet rs = CrudUtil.execute(sql.toString(), params.toArray());
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(rs.getInt("id"));
                reservation.setDate(rs.getString("date"));
                reservation.setNoOfPeople(rs.getInt("noOfPeople"));
                reservation.setStatus(rs.getString("status"));
                reservation.setRestaurantId(rs.getInt("restaurantId"));
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservations;
    }

    // Method to get the count of reservations
    public static int getReservationCount(int customerId, String search) throws ClassNotFoundException, SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM reservation WHERE customerId = ?");

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (reservationId LIKE ?)");
        }

        List<Object> params = new ArrayList<>();
        params.add(customerId);
        if (search != null && !search.isEmpty()) {
            String searchParam = "%" + search + "%";
            params.add(searchParam);
        }

        ResultSet resultSet = CrudUtil.execute(sql.toString(), params.toArray());
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    // Method to get the list of reservations
    public static List<Reservation> getReservationList(String search, String filterStatusType, int page, int size) throws ClassNotFoundException {
        try {
            int offset = (page - 1) * size;
            StringBuilder sql = new StringBuilder("SELECT * FROM reservation WHERE 1=1");

            if (search != null && !search.isEmpty()) {
                sql.append(" AND (id LIKE ? OR reservationId LIKE ? OR name LIKE ?)");
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
            List<Reservation> reservationList = new ArrayList<>();

            while (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(resultSet.getInt("id"));
                reservation.setName(resultSet.getString("name"));
                reservation.setPhoneNumber(resultSet.getString("phoneNumber"));
                reservation.setDate(resultSet.getString("date"));
                reservation.setTime(resultSet.getString("time"));
                reservation.setRestaurantId(resultSet.getInt("restaurantId"));
                reservation.setNoOfPeople(resultSet.getInt("noOfPeople"));
                reservation.setStatus(resultSet.getString("status"));
                reservationList.add(reservation);
            }
            return reservationList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the count
    public static int getReservationCount(String search, String filterStatusType) throws ClassNotFoundException, SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM reservation WHERE 1=1");

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (id LIKE ? OR reservationId LIKE ? OR name LIKE ?)");
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

    public static void updateStatus(int id, String status) throws ClassNotFoundException {
        try {
            String sql = "UPDATE reservation SET status = ? WHERE id = ?";
            CrudUtil.execute(sql, status, id);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
