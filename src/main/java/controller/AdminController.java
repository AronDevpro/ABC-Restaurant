package controller;
import com.google.gson.Gson;
import dao.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet({"/admin","/admin/settings","/admin/settings/update","/admin/settings/view","/admin/report"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class AdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getServletPath();
        if (pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing.");
            return;
        }

        switch (pathInfo) {
            case "/admin/settings/update":
                updateSettings(req, resp);
                break;
            case "/admin/report":
                generateReport(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getServletPath();
        System.out.println(pathInfo);
        if (pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing.");
            return;
        }

        switch (pathInfo) {
            case "/admin":
                getDashboardPage(req, resp);
                break;
            case "/admin/settings":
                getSettingsPage(req, resp);
                break;
            case "/my-account":
                staffAccount(req, resp);
                break;
            case "/admin/settings/view":
                getSettings(req, resp);
                break;
            case "/admin/report":
                getReportPage(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }


    }

    private void getDashboardPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            int customerCount = UserDao.getCustomerCount("Customer");
            req.setAttribute("customerCount", customerCount);

            int staffCount = UserDao.getCustomerCount("Staff");
            req.setAttribute("staffCount", staffCount);

            int orderCount = OrderDao.getOrderCount();
            req.setAttribute("orderCount", orderCount);

            int reservationCount = ReservationDao.getReservationCount();
            req.setAttribute("reservationCount", reservationCount);

            List<Order> orderList = OrderDao.getOrdersByFilter();
            req.setAttribute("orderList", orderList);

            String pageTitle = "ABC Restaurant Admin Dashboard";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/index.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving orders.");
        }
    }

    private void getSettingsPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String pageTitle = "ABC Restaurant Site Settings";
        req.setAttribute("title", pageTitle);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/manageSettings.jsp");
        dispatcher.forward(req, resp);
    }

    private void staffAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String pageTitle = "ABC Restaurant My Account";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/staffs/my-account.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving.");
        }
    }

    private void updateSettings(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
        Setting setting = SettingDao.getSettingById();
        String relativePath = setting.getLogoPath();
        System.out.println(relativePath);

        Part filePart = req.getPart("image");
        System.out.println(filePart);
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            String externalUploadPath = "D:\\workspace\\ABC-Restaurant\\src\\main\\webapp\\assets\\uploads\\logo";
            File uploadDir = new File(externalUploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String filePath = externalUploadPath + File.separator + fileName;
            filePart.write(filePath);
            relativePath = "/uploads/logo/" + fileName;
        }
            System.out.println(relativePath);

            setting.setSiteName(req.getParameter("siteName"));
            setting.setDescription(req.getParameter("description"));
            setting.setSiteEmail(req.getParameter("siteEmail"));
            setting.setSiteStreetAddress(req.getParameter("siteStreetAddress"));
            setting.setSiteZip(req.getParameter("siteZip"));
            setting.setSiteCity(req.getParameter("siteCity"));
            setting.setLogoPath(relativePath);
            setting.setServerEmail(req.getParameter("serverEmail"));
            setting.setServerPassword(req.getParameter("serverPassword"));

            SettingDao.updateSettings(setting);
            resp.sendRedirect(req.getContextPath() + "/admin/settings");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating.");
        }
    }

    private void getSettings(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Setting setting = SettingDao.getSettingById();
            if (setting != null) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                String jsonResponse = new Gson().toJson(setting);
                resp.getWriter().write(jsonResponse);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "setting not found.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the setting.");
        }
    }

    private void getReportPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String pageTitle = "ABC Restaurant Reports";
        req.setAttribute("title", pageTitle);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/report.jsp");
        dispatcher.forward(req, resp);
    }

    private void generateReport(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String startDate = req.getParameter("startDate");
            String endDate = req.getParameter("endDate");
            String selectedReport = req.getParameter("report");

            if ("reservations".equalsIgnoreCase(selectedReport)) {
                List<ReservationReport> reservationReports = ReportDao.generateReservationReport(startDate, endDate);
                req.setAttribute("reservationReportList", reservationReports);
            } else if ("orders".equalsIgnoreCase(selectedReport)) {
                List<OrderReport> orderReports = ReportDao.generateOrderReport(startDate, endDate);
                req.setAttribute("orderReportList", orderReports);
            }

            String pageTitle = "ABC Restaurant Reports";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/report.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving orders.");
        }
    }
}
