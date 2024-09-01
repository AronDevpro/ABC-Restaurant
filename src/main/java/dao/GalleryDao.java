package dao;

import dbConnection.CrudUtil;
import model.Gallery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GalleryDao {
    // Method to create a new gallery
    public static void createGallery(Gallery gallery) throws ClassNotFoundException {
        try {
            String sql = "INSERT INTO gallery ( title, description, category, imagePath) VALUES (?, ?, ?, ?)";
            CrudUtil.execute(sql, gallery.getTitle(), gallery.getDescription(), gallery.getCategory(), gallery.getImagePath());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to update an existing gallery
    public static void updateGallery(Gallery gallery) throws ClassNotFoundException {
        try {
            String sql = "UPDATE gallery SET title = ?, description = ?, category = ?, imagePath = ? WHERE id = ?";
            CrudUtil.execute(sql, gallery.getTitle(), gallery.getDescription(), gallery.getCategory(), gallery.getImagePath(), gallery.getId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to delete a gallery
    public static boolean deleteGallery(int id) throws ClassNotFoundException {
        try {
            String sql = "DELETE FROM gallery WHERE id = ?";
            return CrudUtil.execute(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to retrieve a gallery
    public static Gallery getGalleryById(int id) throws ClassNotFoundException {
        try {
            String sql = "SELECT * FROM gallery WHERE id = ?";
            ResultSet resultSet = CrudUtil.execute(sql, id);

            if (resultSet.next()) {
                Gallery gallery = new Gallery();
                gallery.setId(resultSet.getInt("id"));
                gallery.setTitle(resultSet.getString("title"));
                gallery.setDescription(resultSet.getString("description"));
                gallery.setCategory(resultSet.getString("category"));
                gallery.setImagePath(resultSet.getString("imagePath"));
                gallery.setStatus(resultSet.getString("status"));
                return gallery;

            } else {
                return null;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to retrieve a list of galleries
    public static List<Gallery> getGalleryList(String search, String filterGalleryType, int page, int size) throws ClassNotFoundException {
        try {
            int offset = (page - 1) * size;
            StringBuilder sql = new StringBuilder("SELECT * FROM gallery WHERE 1=1");

            if (search != null && !search.isEmpty()) {
                sql.append(" AND (title LIKE ? OR category LIKE ?)");
            }

            if (filterGalleryType != null && !filterGalleryType.isEmpty()) {
                sql.append(" AND category = ?");
            }

            sql.append(" LIMIT ? OFFSET ?");

            List<Object> params = new ArrayList<>();
            if (search != null && !search.isEmpty()) {
                String searchParam = "%" + search + "%";
                params.add(searchParam);
                params.add(searchParam);
            }

            if (filterGalleryType != null && !filterGalleryType.isEmpty()) {
                params.add(filterGalleryType);
            }

            params.add(size);
            params.add(offset);

            ResultSet resultSet = CrudUtil.execute(sql.toString(), params.toArray());
            List<Gallery> galleryList = new ArrayList<>();

            while (resultSet.next()) {
                Gallery gallery = new Gallery();
                gallery.setId(resultSet.getInt("id"));
                gallery.setTitle(resultSet.getString("title"));
                gallery.setDescription(resultSet.getString("description"));
                gallery.setCategory(resultSet.getString("category"));
                gallery.setImagePath(resultSet.getString("imagePath"));
                gallery.setStatus(resultSet.getString("status"));
                galleryList.add(gallery);
            }
            return galleryList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the count of galleries
    public static int getGalleryCount(String search, String filterGalleryType) throws ClassNotFoundException, SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM gallery WHERE 1=1");

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (title LIKE ? OR category LIKE ?)");
        }

        if (filterGalleryType != null && !filterGalleryType.isEmpty()) {
            sql.append(" AND category = ?");
        }

        List<Object> params = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            String searchParam = "%" + search + "%";
            params.add(searchParam);
            params.add(searchParam);
        }

        if (filterGalleryType != null && !filterGalleryType.isEmpty()) {
            params.add(filterGalleryType);
        }

        ResultSet resultSet = CrudUtil.execute(sql.toString(), params.toArray());
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }
}
