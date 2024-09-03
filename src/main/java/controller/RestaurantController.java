package controller;

import com.google.gson.Gson;
import dao.RestaurantDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Restaurant;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/restaurant/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class RestaurantController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        System.out.println(pathInfo);

        if (pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing.");
            return;
        }

        switch (pathInfo) {
            case "/create":
                createRestaurant(req, resp);
                break;
            case "/update":
                updateRestaurant(req, resp);
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
                getRestaurantList(req, resp);
                break;
            case "/view":
                getRestaurantById(req, resp);
                break;
            case "/delete":
                deleteRestaurant(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }
    }

    private void createRestaurant(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part filePart = req.getPart("image");
        String fileName = filePart.getSubmittedFileName();
        String externalUploadPath = "D:\\workspace\\ABC-Restaurant\\src\\main\\webapp\\assets\\uploads\\restaurants";
        File uploadDir = new File(externalUploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String filePath = externalUploadPath + File.separator + fileName;
        filePart.write(filePath);
        String relativePath = "/uploads/restaurants/" + fileName;
        Restaurant restaurant = new Restaurant();
        restaurant.setName(req.getParameter("name"));
        restaurant.setDescription(req.getParameter("description"));
        restaurant.setOpenTime(req.getParameter("openTime"));
        restaurant.setCloseTime(req.getParameter("closeTime"));
        restaurant.setAddress(req.getParameter("address"));
        restaurant.setPhoneNumber(req.getParameter("phoneNumber"));
        restaurant.setImage(relativePath);
        restaurant.setCapacity(Integer.parseInt(req.getParameter("capacity")));

        try {
            RestaurantDao.createRestaurant(restaurant);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("/admin/restaurant/");
    }

    private void updateRestaurant(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Restaurant restaurant = RestaurantDao.getRestaurantById(id);
            if (restaurant == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
                return;
            }
            String relativePath = restaurant.getImage();
            System.out.println(relativePath);

            Part filePart = req.getPart("image");
            System.out.println(filePart);
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                String externalUploadPath = "D:\\workspace\\ABC-Restaurant\\src\\main\\webapp\\assets\\uploads\\restaurants";
                File uploadDir = new File(externalUploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String filePath = externalUploadPath + File.separator + fileName;
                filePart.write(filePath);
                relativePath = "/uploads/restaurants/" + fileName;
            }

            restaurant.setName(req.getParameter("name"));
            restaurant.setDescription(req.getParameter("description"));
            restaurant.setOpenTime(req.getParameter("openTime"));
            restaurant.setCloseTime(req.getParameter("closeTime"));
            restaurant.setAddress(req.getParameter("address"));
            restaurant.setPhoneNumber(req.getParameter("phoneNumber"));
            restaurant.setCapacity(Integer.parseInt(req.getParameter("capacity")));
            restaurant.setImage(relativePath);

            RestaurantDao.updateRestaurant(restaurant);

            resp.sendRedirect(req.getContextPath() + "/admin/restaurant/");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating the Restaurant.");
        }
    }

    private void deleteRestaurant(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            boolean success = RestaurantDao.deleteRestaurant(id);
            if (success) {
                resp.sendRedirect(req.getContextPath() + "/admin/restaurant/");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Restaurant not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while deleting the Restaurant.");
        }
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

    private void getRestaurantList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            int page = 1;
            int size = 10;

            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }
            if (req.getParameter("size") != null) {
                size = Integer.parseInt(req.getParameter("size"));
            }

            String search = req.getParameter("search");

            List<Restaurant> list = RestaurantDao.getRestaurantList(search, page, size);
            int totalUsers = RestaurantDao.getRestaurantCount(search);
            int totalPages = (int) Math.ceil((double) totalUsers / size);

            req.setAttribute("restaurantList", list);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("size", size);
            req.setAttribute("search", search);

            String pageTitle = "Manage Restaurants";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/manageRestaurants.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the Restaurant list.");
        }
    }
}
