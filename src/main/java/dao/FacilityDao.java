package dao;

import dbConnection.CrudUtil;
import model.Facility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacilityDao {

    // Method to create a new facility
    public static void createFacility(Facility facility) throws ClassNotFoundException {
        try {
            String sql = "INSERT INTO facilities (restaurantId, name, description, category, imagePath) VALUES (?, ?, ?, ?, ?)";
            CrudUtil.execute(sql, facility.getRestaurantId(), facility.getName(), facility.getDescription(), facility.getCategory(), facility.getImagePath());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to update an existing facility
    public static void updateFacility(Facility facility) throws ClassNotFoundException {
        try {
            String sql = "UPDATE facilities SET restaurantId = ?, name = ?, description = ?, category = ?, imagePath = ?, status = ? WHERE id = ?";
            CrudUtil.execute(sql, facility.getRestaurantId(), facility.getName(), facility.getDescription(), facility.getCategory(), facility.getImagePath(), facility.getStatus(), facility.getId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to delete a facility
    public static boolean deleteFacility(int id) throws ClassNotFoundException {
        try {
            String sql = "DELETE FROM facilities WHERE id = ?";
            return CrudUtil.execute(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to retrieve a facility
    public static Facility getFacilityById(int id) throws ClassNotFoundException {
        try {
            String sql = "SELECT * FROM facilities WHERE id = ?";
            ResultSet resultSet = CrudUtil.execute(sql, id);

            if (resultSet.next()) {
                Facility facility = new Facility();
                facility.setId(resultSet.getInt("id"));
                facility.setRestaurantId(resultSet.getInt("restaurantId"));
                facility.setName(resultSet.getString("name"));
                facility.setDescription(resultSet.getString("description"));
                facility.setCategory(resultSet.getString("category"));
                facility.setImagePath(resultSet.getString("imagePath"));
                facility.setStatus(resultSet.getString("status"));
                System.out.println(facility);
                return facility;

            } else {
                return null;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to retrieve a list of facilities
    public static List<Facility> getFacilityList(String search, String filterFacilityType, int page, int size) throws ClassNotFoundException {
        try {
            int offset = (page - 1) * size;
            StringBuilder sql = new StringBuilder("SELECT * FROM facilities WHERE 1=1");

            if (search != null && !search.isEmpty()) {
                sql.append(" AND (name LIKE ? OR category LIKE ?)");
            }

            if (filterFacilityType != null && !filterFacilityType.isEmpty()) {
                sql.append(" AND category = ?");
            }

            sql.append(" LIMIT ? OFFSET ?");

            List<Object> params = new ArrayList<>();
            if (search != null && !search.isEmpty()) {
                String searchParam = "%" + search + "%";
                params.add(searchParam);
                params.add(searchParam);
            }

            if (filterFacilityType != null && !filterFacilityType.isEmpty()) {
                params.add(filterFacilityType);
            }

            params.add(size);
            params.add(offset);

            ResultSet resultSet = CrudUtil.execute(sql.toString(), params.toArray());
            List<Facility> facilityList = new ArrayList<>();

            while (resultSet.next()) {
                Facility facility = new Facility();
                facility.setId(resultSet.getInt("id"));
                facility.setRestaurantId(resultSet.getInt("restaurantId"));
                facility.setName(resultSet.getString("name"));
                facility.setDescription(resultSet.getString("description"));
                facility.setCategory(resultSet.getString("category"));
                facility.setImagePath(resultSet.getString("imagePath"));
                facility.setStatus(resultSet.getString("status"));
                facilityList.add(facility);
            }
            return facilityList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the count of facilities
    public static int getFacilityCount(String search, String filterFacilityType) throws ClassNotFoundException, SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM facilities WHERE 1=1");

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (name LIKE ? OR category LIKE ?)");
        }

        if (filterFacilityType != null && !filterFacilityType.isEmpty()) {
            sql.append(" AND category = ?");
        }

        List<Object> params = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            String searchParam = "%" + search + "%";
            params.add(searchParam);
            params.add(searchParam);
        }

        if (filterFacilityType != null && !filterFacilityType.isEmpty()) {
            params.add(filterFacilityType);
        }

        ResultSet resultSet = CrudUtil.execute(sql.toString(), params.toArray());
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }
}
