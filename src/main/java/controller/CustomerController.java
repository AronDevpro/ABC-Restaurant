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

@WebServlet("/customer/*")
public class CustomerController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing.");
            return;
        }

        switch (pathInfo) {
            case "/create":
                createQuery(req, resp);
                break;
            case "/update":
                updateUser(req, resp);
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
                customerAccount(req, resp);
                break;
            case "/query":
                getQueryPage(req, resp);
                break;
            case "/orders":
                getOrderPage(req, resp);
                break;
            case "/queries":
                getAllQueryPage(req, resp);
                break;
            case "/reservations":
                getAllReservationPage(req, resp);
                break;
            case "/view":
                getOrderItems(req, resp);
                break;
            case "/get-user":
                getUserById(req, resp);
                break;
            case "/view-query":
                getQueryById(req, resp);
                break;
            case "/get-restaurant":
                getRestaurantById(req, resp);
                break;
            case "/logout":
                logoutUser(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }


    }
    private void createQuery(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        Query query = new Query();
        query.setSubject(req.getParameter("subject"));
        query.setDescription(req.getParameter("description"));
        query.setCustomerId(Integer.parseInt(req.getParameter("customerId")));
        query.setPhoneNumber(req.getParameter("phoneNumber"));
        String orderId = req.getParameter("orderId");
        if (orderId != null) {
            query.setOrderId(orderId);
        }

        try {
            QueriesDao.createQuery(query);
            String pageTitle = "ABC Restaurant - Query Successfully";
            String successMessage = "Query Created Successfully!";
            req.setAttribute("title", pageTitle);
            req.setAttribute("successMessage", successMessage);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/customers/query.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getQueryPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String pageTitle = "ABC Restaurant Customer Query";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/customers/query.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving.");
        }
    }

    private void customerAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String pageTitle = "ABC Restaurant My Account";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/customers/my-account.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving.");
        }
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Integer customerId = Integer.parseInt(req.getParameter("customerId"));
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String address = req.getParameter("address");
        String phoneNumber = req.getParameter("phoneNumber");
        String accountType = req.getParameter("accountType");

        User user = new User();
        user.setId(customerId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAddress(address);
        user.setPhoneNumber(phoneNumber);
        user.setAccountType(accountType);

        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(password);
        } else {
            try {
                User existingUser = UserDao.getUserById(customerId);
                user.setPassword(existingUser.getPassword());
            } catch (ClassNotFoundException e) {
                throw new ServletException("User not found", e);
            }
        }

        try {
            UserDao.updateUser(user);
            String pageTitle = "ABC Restaurant - Account Updated";
            String successMessage = "Account Updated Successfully!";
            req.setAttribute("title", pageTitle);
            req.setAttribute("successMessage", successMessage);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/customers/my-account.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating.");
        }
    }

    private void getOrderPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        Integer customerId = (user != null) ? user.getId() : null;

        if (customerId == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "User not logged in");
            return;
        }

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

            // Fetch orders
            List<Order> orders = OrderDao.getOrdersByCustomerId(customerId, search, page, size);

            // Get the total count of orders for pagination
            int totalOrders = OrderDao.getOrdersCount(customerId, search);
            int totalPages = (int) Math.ceil((double) totalOrders / size);

            req.setAttribute("orderList", orders);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("size", size);
            req.setAttribute("search", search);

            String pageTitle = "ABC Restaurant Orders";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/customers/my-orders.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving orders.");
        }
    }

    private void getOrderItems(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            List<OrderItems> orderItemsList = OrderDao.getOrderItemsByOrderId(id);
            if (orderItemsList != null && !orderItemsList.isEmpty()) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                String jsonResponse = new Gson().toJson(orderItemsList);
                resp.getWriter().write(jsonResponse);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Order Items not found.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the order items.");
        }
    }

    private void getAllQueryPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        Integer customerId = (user != null) ? user.getId() : null;

        if (customerId == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "User not logged in");
            return;
        }

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

            // Fetch orders
            List<Query> queries = QueriesDao.getOrdersByCustomerId(customerId, search, page, size);

            // Get the total count of orders for pagination
            int totalQueries = QueriesDao.getQueryCount(customerId, search);
            int totalPages = (int) Math.ceil((double) totalQueries / size);

            req.setAttribute("queryList", queries);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("size", size);
            req.setAttribute("search", search);

            String pageTitle = "ABC Restaurant Queries";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/customers/my-queries.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving orders.");
        }
    }

    private void getQueryById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Query query = QueriesDao.getQueryById(id);
            if (query != null) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                String jsonResponse = new Gson().toJson(query);
                resp.getWriter().write(jsonResponse);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Query not found.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the order items.");
        }
    }

    private void getAllReservationPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        Integer customerId = (user != null) ? user.getId() : null;

        if (customerId == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "User not logged in");
            return;
        }

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

            // Fetch orders
            List<Reservation> reservations = ReservationDao.getReservationsByCustomerId(customerId, search, page, size);

            // Get the total count of orders for pagination
            int totalReservation = ReservationDao.getReservationCount(customerId, search);
            int totalPages = (int) Math.ceil((double) totalReservation / size);

            req.setAttribute("reservationList", reservations);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("size", size);
            req.setAttribute("search", search);

            String pageTitle = "ABC Restaurant Reservation";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/customers/my-reservations.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving orders.");
        }
    }

    private void getUserById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int userId = Integer.parseInt(req.getParameter("id"));
            User user = UserDao.getUserById(userId);
            if (user != null) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                String jsonResponse = new Gson().toJson(user);
                resp.getWriter().write(jsonResponse);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the user.");
        }
    }

    private void logoutUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        resp.sendRedirect(req.getContextPath() + "/login");
    }

    private void getRestaurantById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Restaurant restaurant = RestaurantDao.getRestaurantById(id);
            if (restaurant != null) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                String jsonResponse = new Gson().toJson(restaurant);
                resp.getWriter().write(jsonResponse);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Restaurant not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the Restaurant.");
        }
    }
}
