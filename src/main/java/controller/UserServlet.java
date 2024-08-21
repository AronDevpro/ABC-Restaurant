package controller;

import com.google.gson.Gson;
import dao.UserDao;
import factory.UserFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Admin;
import model.Staff;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        System.out.println(servletPath);

        if (servletPath == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing.");
            return;
        }

        switch (servletPath) {
            case "/user/customer":
                registerCustomer(req, resp);
                break;
            case "/user/create":
                registerUser(req, resp);
                break;
            case "/user/login":
                loginUser(req, resp);
                break;
            case "/user/update":
                updateUser(req, resp);
                break;
            case "/user":
                getUserList(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        System.out.println(servletPath);

        if (servletPath == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing.");
            return;
        }

        switch (servletPath) {
            case "/user":
                getUserList(req, resp);
                break;
            case "/user/view":
                getUserById(req, resp);
                break;
            case "/user/delete":
                deleteUser(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }


    }
    private void registerCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String userType = req.getParameter("accountType");

        User user = UserFactory.createUser(userType);

        user.setFirstName(req.getParameter("firstName"));
        user.setLastName(req.getParameter("lastName"));
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));
        user.setAddress(req.getParameter("address"));
        user.setPhoneNumber(req.getParameter("phoneNumber"));
        user.setAccountType(req.getParameter("accountType"));

        try {
            UserDao.registerCustomer(user);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String pageTitle = "Register Page";
        req.setAttribute("title", pageTitle);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/successMessage.jsp");
        dispatcher.forward(req, resp);
    }

    private void registerUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String userType = req.getParameter("accountType");

        User user = UserFactory.createUser(userType);

        user.setFirstName(req.getParameter("firstName"));
        user.setLastName(req.getParameter("lastName"));
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));
        user.setAddress(req.getParameter("address"));
        user.setPhoneNumber(req.getParameter("phoneNumber"));
        user.setAccountType(req.getParameter("accountType"));
        // Set specific properties based on user type
        if (user instanceof Admin) {
            Admin admin = (Admin) user;
            admin.setNIC(req.getParameter("nic"));
        } else if (user instanceof Staff) {
            Staff staff = (Staff) user;
            staff.setNIC(req.getParameter("nic"));
            staff.setPosition(req.getParameter("position"));
        }
        try {
            UserDao.registerCustomer(user);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("/user");
    }

    private void loginUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            User user = UserDao.login(email, password);

            if (user != null) {
                // Store user in session
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                // Check user account type and redirect accordingly
                String accountType = user.getAccountType();
                if ("Admin".equals(accountType)) {
                    resp.sendRedirect(req.getContextPath() + "/dashboard.jsp");
                } else if ("Customer".equals(accountType)) {
                    resp.sendRedirect(req.getContextPath() + "/dashboard1.jsp");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/dashboard2.jsp");
                }
            } else {
                resp.sendRedirect("/login.jsp?error=Invalid email or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/login.jsp?error=Something went wrong, please try again.");
        }
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int userId = Integer.parseInt(req.getParameter("id"));
            User user = UserDao.getUserById(userId);
            if (user == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
                return;
            }

            user.setFirstName(req.getParameter("firstName"));
            user.setLastName(req.getParameter("lastName"));
            user.setEmail(req.getParameter("email"));
            user.setPhoneNumber(req.getParameter("phoneNumber"));
            user.setAddress(req.getParameter("address"));
            user.setAccountType(req.getParameter("accountType"));

            if (user instanceof Admin) {
                ((Admin) user).setNIC(req.getParameter("nic"));
            } else if (user instanceof Staff) {
                ((Staff) user).setNIC(req.getParameter("nic"));
                ((Staff) user).setPosition(req.getParameter("position"));
            }

            UserDao.updateUser(user);

            resp.sendRedirect(req.getContextPath() + "/user");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating the user.");
        }
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int userId = Integer.parseInt(req.getParameter("id"));
            boolean success = UserDao.deleteUser(userId);
            if (success) {
                resp.sendRedirect(req.getContextPath() + "/user");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while deleting the user.");
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

    private void getUserList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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

            // Get search and filter parameters
            String search = req.getParameter("search");
            String filterUserType = req.getParameter("filterUserType");

            List<User> userList = UserDao.getUserList(search, filterUserType, page, size);
            int totalUsers = UserDao.getUserCount(search, filterUserType);
            int totalPages = (int) Math.ceil((double) totalUsers / size);

            req.setAttribute("userList", userList);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("size", size);
            req.setAttribute("search", search);
            req.setAttribute("filterUserType", filterUserType);

            String pageTitle = "Manage Users";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/manageUsers.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the user list.");
        }
    }
}
