package controller;

import com.google.gson.Gson;
import dao.*;
import factory.UserFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;
import util.EmailUtil;

import java.io.IOException;
import java.util.List;

@WebServlet({"", "/register", "/login", "/contact", "/offers", "/facility", "/products","/settings"})
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();

        switch (servletPath) {
            case "/register":
                registerCustomer(req, resp);
                break;
            case "/login":
                loginUser(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();

        switch (servletPath) {
            case "":
                getMainPage(req, resp);
                break;
            case "/login":
                getLoginPage(req, resp);
                break;
            case "/register":
                getRegisterPage(req, resp);
                break;
            case "/contact":
                getContactPage(req, resp);
                break;
            case "/offers":
                getOfferPage(req, resp);
                break;
            case "/facility":
                getFacilityPage(req, resp);
                break;
            case "/products":
                getProductById(req, resp);
                break;
            case "/settings":
                getSettings(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found.");
                break;
        }
    }

    private void getMainPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            List<Gallery> galleryList = GalleryDao.getGalleryActiveList();
            req.setAttribute("galleryList", galleryList);

            List<Restaurant> restaurantList = RestaurantDao.getAllRestaurants();
            req.setAttribute("restaurantList", restaurantList);

            String pageTitle = "ABC Restaurant";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving.");
        }
    }

    private void getLoginPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String pageTitle = "ABC Restaurant Login";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving.");
        }
    }
    private void getRegisterPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String pageTitle = "ABC Restaurant Register";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("register.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving.");
        }
    }
    private void getContactPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String pageTitle = "ABC Restaurant Contact";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("contact.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving.");
        }
    }

    private void getOfferPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
            String filterOfferType = req.getParameter("filterUserType");

            List<Offer> offerList = OfferDao.getOfferList(search, filterOfferType, page, size);
            int totalOffer = OfferDao.getOfferCount(search, filterOfferType);
            int totalPages = (int) Math.ceil((double) totalOffer / size);

            req.setAttribute("offerList", offerList);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("size", size);
            req.setAttribute("search", search);
            req.setAttribute("filterUserType", filterOfferType);

            req.setAttribute("title", "ABC Restaurant Offers");
            RequestDispatcher dispatcher = req.getRequestDispatcher("offers.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving.");
        }
    }

    private void getFacilityPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
            String filterFacilityType = req.getParameter("filterUserType");

            List<Facility> facilityList = FacilityDao.getFacilityList(search, filterFacilityType, page, size);
            int totalFacility = FacilityDao.getFacilityCount(search, filterFacilityType);
            int totalPages = (int) Math.ceil((double) totalFacility / size);

            req.setAttribute("facilityList", facilityList);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("size", size);
            req.setAttribute("search", search);
            req.setAttribute("filterUserType", filterFacilityType);

            // Get restaurant list
            List<Restaurant> restaurantList = RestaurantDao.getAllRestaurants();
            req.setAttribute("restaurantList", restaurantList);

            req.setAttribute("title", "ABC Restaurant Facilities");
            RequestDispatcher dispatcher = req.getRequestDispatcher("facilities.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving.");
        }
    }

    private void registerCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String userType = "Customer";

        User user = UserFactory.createUser(userType);
        String email = req.getParameter("email");
        String firstName = req.getParameter("firstName");

        user.setFirstName(req.getParameter("firstName"));
        user.setLastName(req.getParameter("lastName"));
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));
        user.setAddress(req.getParameter("address"));
        user.setPhoneNumber(req.getParameter("phoneNumber"));
        user.setAccountType(userType);

        try {
            UserDao.registerCustomer(user);
            Setting setting = SettingDao.getSettingById();
            String serverEmail = setting.getServerEmail();
            String serverPassword = setting.getServerPassword();
            String subject = "Welcome to ABC Restaurant!";
            String messageContent = "Dear " + firstName + ",\n\nThank you for creating an account with us!\n\nBest regards,\nABC Restaurant";
            EmailUtil.sendEmail(email, subject, messageContent,serverEmail,serverPassword);
            resp.sendRedirect("/login");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred.");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
    }

    private void loginUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            User user = UserDao.login(email, password);

            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                String accountType = user.getAccountType();
                String redirectPage;

                switch (accountType) {
                    case "Admin":
                        redirectPage = "/admin";
                        break;
                    case "Customer":
                        redirectPage = "/customer/";
                        break;
                    default:
                        redirectPage = "/staff/";
                        break;
                }

                resp.sendRedirect(req.getContextPath() + redirectPage);
            } else {
                req.setAttribute("errorMessage", "Invalid email or password");
                resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Invalid email or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Something went wrong, please try again.");
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Something went wrong, please try again.");
        }
    }

    private void getProductById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Product product = ProductDao.getProductById(id);
            if (product != null) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                String jsonResponse = new Gson().toJson(product);
                resp.getWriter().write(jsonResponse);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "product not found.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the product.");
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
}
