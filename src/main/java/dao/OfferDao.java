package dao;

import dbConnection.CrudUtil;
import model.Offer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfferDao {
    // Method to create a new offer
    public static void createOffer(Offer offer) throws ClassNotFoundException {
        try {
            String sql = "INSERT INTO offers ( offerName, promoCode, discountPercentage, category, imagePath, status) VALUES (?, ?, ?, ?, ?, ?)";
            CrudUtil.execute(sql, offer.getOfferName(), offer.getPromoCode(), offer.getDiscountPercentage(), offer.getCategory(), offer.getImagePath(), offer.getStatus());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to update an existing offer
    public static void updateOffer(Offer offer) throws ClassNotFoundException {
        try {
            String sql = "UPDATE offers SET offerName = ?, promoCode = ?, discountPercentage = ?, category = ?, imagePath = ?, status = ? WHERE id = ?";
            CrudUtil.execute(sql, offer.getOfferName(), offer.getPromoCode(), offer.getDiscountPercentage(), offer.getCategory(), offer.getImagePath(), offer.getStatus(), offer.getId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to delete a offer
    public static boolean deleteOffer(int id) throws ClassNotFoundException {
        try {
            String sql = "DELETE FROM offers WHERE id = ?";
            return CrudUtil.execute(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to retrieve a offer
    public static Offer getOfferById(int id) throws ClassNotFoundException {
        try {
            String sql = "SELECT * FROM offers WHERE id = ?";
            ResultSet resultSet = CrudUtil.execute(sql, id);

            if (resultSet.next()) {
                Offer offer = new Offer();
                offer.setId(resultSet.getInt("id"));
                offer.setOfferName(resultSet.getString("offerName"));
                offer.setPromoCode(resultSet.getString("promoCode"));
                offer.setDiscountPercentage(resultSet.getDouble("discountPercentage"));
                offer.setCategory(resultSet.getString("category"));
                offer.setImagePath(resultSet.getString("imagePath"));
                offer.setStatus(resultSet.getString("status"));
                return offer;

            } else {
                return null;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to retrieve a list of offers
    public static List<Offer> getOfferList(String search, String filterOfferType, int page, int size) throws ClassNotFoundException {
        try {
            int offset = (page - 1) * size;
            StringBuilder sql = new StringBuilder("SELECT * FROM offers WHERE 1=1");

            if (search != null && !search.isEmpty()) {
                sql.append(" AND (offerName LIKE ? OR category LIKE ?)");
            }

            if (filterOfferType != null && !filterOfferType.isEmpty()) {
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

            if (filterOfferType != null && !filterOfferType.isEmpty()) {
                params.add(filterOfferType);
            }

            params.add(size);
            params.add(offset);

            ResultSet resultSet = CrudUtil.execute(sql.toString(), params.toArray());
            List<Offer> offerList = new ArrayList<>();

            while (resultSet.next()) {
                Offer offer = new Offer();
                offer.setId(resultSet.getInt("id"));
                offer.setOfferName(resultSet.getString("offerName"));
                offer.setPromoCode(resultSet.getString("promoCode"));
                offer.setDiscountPercentage(resultSet.getDouble("discountPercentage"));
                offer.setCategory(resultSet.getString("category"));
                offer.setImagePath(resultSet.getString("imagePath"));
                offer.setStatus(resultSet.getString("status"));
                offerList.add(offer);
            }
            return offerList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the count of offers
    public static int getOfferCount(String search, String filterOfferType) throws ClassNotFoundException, SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM offers WHERE 1=1");

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (offerName LIKE ? OR category LIKE ?)");
        }

        if (filterOfferType != null && !filterOfferType.isEmpty()) {
            sql.append(" AND category = ?");
        }

        List<Object> params = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            String searchParam = "%" + search + "%";
            params.add(searchParam);
            params.add(searchParam);
            params.add(searchParam);
        }

        if (filterOfferType != null && !filterOfferType.isEmpty()) {
            params.add(filterOfferType);
        }

        ResultSet resultSet = CrudUtil.execute(sql.toString(), params.toArray());
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }
}
