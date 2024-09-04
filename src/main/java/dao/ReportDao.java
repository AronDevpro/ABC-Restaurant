package dao;

import dbConnection.CrudUtil;
import model.OrderReport;
import model.ReservationReport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportDao {
    //    method to generate reservation report
    public static List<ReservationReport> generateReservationReport(String startDate, String endDate) throws SQLException, ClassNotFoundException, SQLException {
        String sql = "SELECT r.name AS restaurantName, res.date, COUNT(res.id) AS numberOfReservations " +
                "FROM reservation res " +
                "JOIN restaurant r ON res.restaurantId = r.id " +
                "WHERE res.date BETWEEN ? AND ? " +
                "GROUP BY r.name, res.date";
        ResultSet rs = CrudUtil.execute(sql, startDate, endDate);
        List<ReservationReport> reportList = new ArrayList<>();
        while (rs.next()) {
            ReservationReport report = new ReservationReport();
            report.setRestaurantName(rs.getString("restaurantName"));
            report.setDate(rs.getString("date"));
            report.setNumberOfReservations(rs.getInt("numberOfReservations"));
            reportList.add(report);
        }
        return reportList;
    }

    //    method to generate order report
    public static List<OrderReport> generateOrderReport(String startDate, String endDate) throws SQLException, ClassNotFoundException, SQLException {
    String sql = "SELECT o.restaurantSelect AS restaurantName,\n" +
            "       o.deliverDate,\n" +
            "       SUM(o.total) AS totalRevenue,\n" +
            "       COUNT(o.id) AS numberOfOrders\n" +
            "FROM Orders o\n" +
            "WHERE o.deliverDate BETWEEN ? AND ?\n" +
            "GROUP BY o.restaurantSelect, o.deliverDate";
    ResultSet rs = CrudUtil.execute(sql, startDate, endDate);
    List<OrderReport> reportList = new ArrayList<>();
    while (rs.next()) {
        OrderReport report = new OrderReport();
        report.setRestaurantName(rs.getString("restaurantName"));
        report.setDeliverDate(rs.getString("deliverDate"));
        report.setTotalRevenue(rs.getDouble("totalRevenue"));
        report.setNumberOfOrders(rs.getInt("numberOfOrders"));
        reportList.add(report);
    }
    return reportList;
}
}
