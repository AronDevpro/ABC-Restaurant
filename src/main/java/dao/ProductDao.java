package dao;

import dbConnection.CrudUtil;
import model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    // Method to create a new product
    public static void createProduct(Product product) throws ClassNotFoundException {
        try {
            String sql = "INSERT INTO products (name, description, category, imagePath, price) VALUES (?, ?, ?, ?, ?)";
            CrudUtil.execute(sql, product.getName(), product.getDescription(), product.getCategory(), product.getImagePath(), product.getPrice());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to update an existing product
    public static void updateProduct(Product product) throws ClassNotFoundException {
        try {
            String sql = "UPDATE products SET  name = ?, description = ?, category = ?, imagePath = ?, price = ?, status = ? WHERE id = ?";
            CrudUtil.execute(sql, product.getName(), product.getDescription(), product.getCategory(), product.getImagePath(), product.getPrice(), product.getStatus(), product.getId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to delete a product
    public static boolean deleteProduct(int id) throws ClassNotFoundException {
        try {
            String sql = "DELETE FROM products WHERE id = ?";
            return CrudUtil.execute(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to retrieve a product
    public static Product getProductById(int id) throws ClassNotFoundException {
        try {
            String sql = "SELECT * FROM products WHERE id = ?";
            ResultSet resultSet = CrudUtil.execute(sql, id);

            if (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setCategory(resultSet.getString("category"));
                product.setImagePath(resultSet.getString("imagePath"));
                product.setPrice(Double.valueOf(resultSet.getString("price")));
                product.setStatus(resultSet.getString("status"));
                return product;

            } else {
                return null;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to retrieve a list of products
    public static List<Product> getProductList(String search, String filterProductType, int page, int size) throws ClassNotFoundException {
        try {
            int offset = (page - 1) * size;
            StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1");

            if (search != null && !search.isEmpty()) {
                sql.append(" AND (name LIKE ? OR category LIKE ?)");
            }

            if (filterProductType != null && !filterProductType.isEmpty()) {
                sql.append(" AND category = ?");
            }

            sql.append(" LIMIT ? OFFSET ?");

            List<Object> params = new ArrayList<>();
            if (search != null && !search.isEmpty()) {
                String searchParam = "%" + search + "%";
                params.add(searchParam);
                params.add(searchParam);
                params.add(searchParam);
            }

            if (filterProductType != null && !filterProductType.isEmpty()) {
                params.add(filterProductType);
            }

            params.add(size);
            params.add(offset);

            ResultSet resultSet = CrudUtil.execute(sql.toString(), params.toArray());
            List<Product> productList = new ArrayList<>();

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setCategory(resultSet.getString("category"));
                product.setImagePath(resultSet.getString("imagePath"));
                product.setPrice(Double.valueOf(resultSet.getString("price")));
                product.setStatus(resultSet.getString("status"));
                productList.add(product);
            }
            return productList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the count of products
    public static int getProductCount(String search, String filterProductType) throws ClassNotFoundException, SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM products WHERE 1=1");

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (name LIKE ? OR category LIKE ?)");
        }

        if (filterProductType != null && !filterProductType.isEmpty()) {
            sql.append(" AND category = ?");
        }

        List<Object> params = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            String searchParam = "%" + search + "%";
            params.add(searchParam);
            params.add(searchParam);
            params.add(searchParam);
        }

        if (filterProductType != null && !filterProductType.isEmpty()) {
            params.add(filterProductType);
        }

        ResultSet resultSet = CrudUtil.execute(sql.toString(), params.toArray());
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }
}
