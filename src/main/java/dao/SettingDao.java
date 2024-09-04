package dao;

import dbConnection.CrudUtil;
import model.Offer;
import model.Setting;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingDao {
    // Method to update an existing settings
    public static void updateSettings(Setting setting) throws ClassNotFoundException {
        try {
            String sql = "UPDATE settings SET siteName = ?, description = ?, logoPath = ?, siteEmail = ?, siteStreetAddress = ?, siteZip = ?, siteCity = ?, serverEmail = ?, serverPassword = ? WHERE id = 1";
            CrudUtil.execute(sql, setting.getSiteName(), setting.getDescription(), setting.getLogoPath(), setting.getSiteEmail(), setting.getSiteStreetAddress(),setting.getSiteZip(),setting.getSiteCity(),setting.getServerEmail(),setting.getServerPassword());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Setting getSettingById() throws ClassNotFoundException {
        try {
            String sql = "SELECT * FROM settings WHERE id = 1";
            ResultSet resultSet = CrudUtil.execute(sql);

            if (resultSet.next()) {
                Setting setting = new Setting();
                setting.setSiteName(resultSet.getString("siteName"));
                setting.setDescription(resultSet.getString("description"));
                setting.setLogoPath(resultSet.getString("logoPath"));
                setting.setSiteEmail(resultSet.getString("siteEmail"));
                setting.setSiteStreetAddress(resultSet.getString("siteStreetAddress"));
                setting.setSiteZip(resultSet.getString("siteZip"));
                setting.setSiteCity(resultSet.getString("siteCity"));
                setting.setServerEmail(resultSet.getString("serverEmail"));
                setting.setServerPassword(resultSet.getString("serverPassword"));
                return setting;

            } else {
                return null;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
