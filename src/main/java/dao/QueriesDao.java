package dao;

import dbConnection.CrudUtil;
import model.Query;
import model.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueriesDao {

    // Method to create a new query
    public static void createQuery(Query query) throws ClassNotFoundException {
        try {
            String sql = "INSERT INTO queries (subject, description, phoneNumber, orderId, customerId) VALUES (?, ?, ?, ?, ?)";
            CrudUtil.execute(sql, query.getSubject(), query.getDescription(), query.getPhoneNumber(),query.getOrderId() != null ? query.getOrderId() : null, query.getCustomerId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to update an existing query
    public static void updateQuery(Query query) throws ClassNotFoundException {
        try {
            String sql = "UPDATE queries SET restaurantId = ?, name = ?, description = ?, category = ?, imagePath = ?, status = ?, updatedAt = CURRENT_TIMESTAMP WHERE id = ?";
            CrudUtil.execute(sql, query.getSubject());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to update status
    public static void updateStatusQuery(Query query) throws ClassNotFoundException {
        try {
            String sql = "UPDATE queries SET status = ? WHERE id = ?";
            CrudUtil.execute(sql, query.getStatus(), query.getId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the queries with user id
    public static List<Query> getOrdersByCustomerId(int customerId, String search, int page, int size) throws ClassNotFoundException {
        List<Query> query = new ArrayList<>();
        try {
            int offset = (page - 1) * size;
            StringBuilder sql = new StringBuilder("SELECT * FROM queries WHERE customerId = ?");

            if (search != null && !search.isEmpty()) {
                sql.append(" AND (subject LIKE ?)");
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
                Query queries = new Query();
                queries.setId(rs.getInt("id"));
                queries.setSubject(rs.getString("subject"));
                queries.setDescription(rs.getString("description"));
                queries.setResponse(rs.getString("response"));
                queries.setStatus(rs.getString("status"));
                query.add(queries);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return query;
    }

    // Method to get the count of queries
    public static int getQueryCount(int customerId, String search) throws ClassNotFoundException, SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM queries WHERE customerId = ?");

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (subject LIKE ?)");
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

    // Method to get the queries with user id
    public static Query getQueryById(int id) throws ClassNotFoundException {
        try {
            String sql = "SELECT * FROM queries WHERE id = ?";
            ResultSet rs = CrudUtil.execute(sql, id);
            if (rs.next()) {
                Query queries = new Query();
                queries.setId(rs.getInt("id"));
                queries.setSubject(rs.getString("subject"));
                queries.setDescription(rs.getString("description"));
                queries.setResponse(rs.getString("response"));
                queries.setStatus(rs.getString("status"));
                return queries;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the list of reservations
    public static List<Query> getQueryList(String search, String filterStatusType, int page, int size) throws ClassNotFoundException {
        try {
            int offset = (page - 1) * size;
            StringBuilder sql = new StringBuilder("SELECT * FROM queries WHERE 1=1");

            if (search != null && !search.isEmpty()) {
                sql.append(" AND (id LIKE ? OR customerId LIKE ? OR subject LIKE ?)");
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
            List<Query> queryList = new ArrayList<>();

            while (resultSet.next()) {
                Query query = new Query();
                query.setId(resultSet.getInt("id"));
                query.setSubject(resultSet.getString("subject"));
                query.setPhoneNumber(resultSet.getString("phoneNumber"));
                query.setOrderId(resultSet.getString("orderId"));
                query.setCustomerId(resultSet.getInt("customerId"));
                query.setStatus(resultSet.getString("status"));
                queryList.add(query);
            }
            return queryList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the count
    public static int getQueryCount(String search, String filterStatusType) throws ClassNotFoundException, SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM queries WHERE 1=1");

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (id LIKE ? OR customerId LIKE ? OR subject LIKE ?)");
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
            String sql = "UPDATE queries SET status = ? WHERE id = ?";
            CrudUtil.execute(sql, status, id);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateResponse(int id, String response, int StaffId) throws ClassNotFoundException {
        try {
            String status = "answered";
            String sql = "UPDATE queries SET response = ?, staffId =?, status = ? WHERE id = ?";
            CrudUtil.execute(sql, response,StaffId, status, id);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the pending count
    public static int getPendingQueriesCount() throws ClassNotFoundException, SQLException {
        String sql = "SELECT COUNT(*) FROM queries WHERE status = 'pending'";

        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }
}
