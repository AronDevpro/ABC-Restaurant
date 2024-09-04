package controller;
import com.google.gson.Gson;
import dao.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/staff/*")
public class StaffController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing.");
            return;
        }

        switch (pathInfo) {
            case "/update-user":
                updateUser(req, resp);
                break;
            case "/response":
                updateResponse(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        System.out.println(pathInfo);
        if (pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing.");
            return;
        }

        switch (pathInfo) {
            case "/":
                getDashboardPage(req, resp);
                break;
            case "/queries":
                getAllQueryPage(req, resp);
                break;
            case "/queries/status":
                queryStatusChange(req, resp);
                break;
            case "/reservations":
                getAllReservationPage(req, resp);
                break;
            case "/reservations/status":
                reservationStatusChange(req, resp);
                break;
            case "/orders":
                getAllOrdersPage(req, resp);
                break;
            case "/orders/status":
                orderStatusChange(req, resp);
                break;
            case "/view":
                getOrderById(req, resp);
                break;
            case "/my-account":
                staffAccount(req, resp);
                break;
            case "/logout":
                logoutUser(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }


    }

    private void getAllReservationPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try {
            // Get pagination parameters
            int page = 1;
            int size = 10;

            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }
            if (req.getParameter("size") != null) {
                size = Integer.parseInt(req.getParameter("size"));
            }

            // Get search parameters
            String search = req.getParameter("search");
            String filterStatusType = req.getParameter("filterStatusType");

            // Fetch orders
            List<Reservation> reservations = ReservationDao.getReservationList(search,filterStatusType, page, size);

            // Get the total count of orders for pagination
            int totalReservation = ReservationDao.getReservationCount(search,filterStatusType);
            int totalPages = (int) Math.ceil((double) totalReservation / size);

            req.setAttribute("reservationList", reservations);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("size", size);
            req.setAttribute("search", search);
            req.setAttribute("filterStatusType", filterStatusType);

            String pageTitle = "ABC Restaurant Reservation";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/staffs/manageReservations.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving orders.");
        }
    }

    private void reservationStatusChange(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String status = (req.getParameter("status"));

        try {
            ReservationDao.updateStatus(id,status);
            resp.sendRedirect(req.getContextPath() + "/staff/reservations");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while changing status");
        }
    }

    private void getAllQueryPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try {
            // Get pagination parameters
            int page = 1;
            int size = 10;

            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }
            if (req.getParameter("size") != null) {
                size = Integer.parseInt(req.getParameter("size"));
            }

            // Get search parameters
            String search = req.getParameter("search");
            String filterStatusType = req.getParameter("filterStatusType");

            // Fetch orders
            List<Query> queries = QueriesDao.getQueryList(search,filterStatusType, page, size);

            // Get the total count of queries for pagination
            int totalReservation = QueriesDao.getQueryCount(search,filterStatusType);
            int totalPages = (int) Math.ceil((double) totalReservation / size);


            req.setAttribute("queryList", queries);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("size", size);
            req.setAttribute("search", search);
            req.setAttribute("filterStatusType", filterStatusType);

            String pageTitle = "ABC Restaurant Queries";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/staffs/manageQueries.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving query.");
        }
    }

    private void queryStatusChange(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String status = (req.getParameter("status"));

        try {
            QueriesDao.updateStatus(id,status);
            resp.sendRedirect(req.getContextPath() + "/staff/queries");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while changing status");
        }
    }

    private void updateResponse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        Integer staffId = (user != null) ? user.getId() : null;

        if (staffId == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "User not logged in");
            return;
        }
        int id = Integer.parseInt(req.getParameter("id"));
        String response = (req.getParameter("response"));

        try {
            QueriesDao.updateResponse(id,response, staffId);
            resp.sendRedirect(req.getContextPath() + "/staff/queries");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while changing status");
        }
    }

    private void getAllOrdersPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try {
            // Get pagination parameters
            int page = 1;
            int size = 10;

            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }
            if (req.getParameter("size") != null) {
                size = Integer.parseInt(req.getParameter("size"));
            }

            // Get search parameters
            String search = req.getParameter("search");
            String filterStatusType = req.getParameter("filterStatusType");

            // Fetch orders
            List<Order> orderList = OrderDao.getOrderList(search,filterStatusType, page, size);

            // Get the total count of orders for pagination
            int total = OrderDao.getOrderCount(search,filterStatusType);
            int totalPages = (int) Math.ceil((double) total / size);

            req.setAttribute("orderList", orderList);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("size", size);
            req.setAttribute("search", search);
            req.setAttribute("filterStatusType", filterStatusType);

            String pageTitle = "ABC Restaurant Orders";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/staffs/manageOrders.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving orders.");
        }
    }

    private void orderStatusChange(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String status = (req.getParameter("status"));

        try {
            OrderDao.updateStatus(id,status);
            resp.sendRedirect(req.getContextPath() + "/staff/orders");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while changing status");
        }
    }

    private void getOrderById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Order order = OrderDao.getOrderById(id);
            if (order != null) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                String jsonResponse = new Gson().toJson(order);
                resp.getWriter().write(jsonResponse);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the order items.");
        }
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

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Integer staffId = Integer.parseInt(req.getParameter("staffId"));
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String address = req.getParameter("address");
        String phoneNumber = req.getParameter("phoneNumber");
        String accountType = req.getParameter("accountType");
        String position = req.getParameter("position");
        String NIC = req.getParameter("NIC");

        Staff staff = new Staff();
        staff.setId(staffId);
        staff.setFirstName(firstName);
        staff.setLastName(lastName);
        staff.setEmail(email);
        staff.setAddress(address);
        staff.setPhoneNumber(phoneNumber);
        staff.setAccountType(accountType);
        staff.setPosition(position);
        staff.setNIC(NIC);

        if (password != null && !password.trim().isEmpty()) {
            staff.setPassword(password);
        } else {
            try {
                Staff existingUser = (Staff) UserDao.getUserById(staffId);
                staff.setPassword(existingUser.getPassword());
            } catch (ClassNotFoundException e) {
                throw new ServletException("User not found", e);
            }
        }

        try {
            UserDao.updateUser(staff);
            String pageTitle = "ABC Restaurant - Account Updated";
            String successMessage = "Account Updated Successfully!";
            req.setAttribute("title", pageTitle);
            req.setAttribute("successMessage", successMessage);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/staffs/my-account.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating.");
        }
    }

    private void getDashboardPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            int queryCount = QueriesDao.getPendingQueriesCount();
            req.setAttribute("queryCount", queryCount);

            int reservationCount = ReservationDao.getPendingReservationCount();
            req.setAttribute("reservationCount", reservationCount);

            int orderCount = OrderDao.getOrderCount();
            req.setAttribute("orderCount", orderCount);

            List<Order> orderList = OrderDao.getOrdersByFilter();
            req.setAttribute("orderList", orderList);

            List<Reservation> reservationList = ReservationDao.getReservationByFilter();
            req.setAttribute("reservationList", reservationList);

            String pageTitle = "ABC Restaurant Staff Dashboard";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/staffs/index.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving orders.");
        }
    }

    private void logoutUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
