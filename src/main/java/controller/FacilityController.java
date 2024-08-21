package controller;

import com.google.gson.Gson;
import dao.FacilityDao;
import dao.RestaurantDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Facility;
import model.Restaurant;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/facilities/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class FacilityController extends HttpServlet {
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
                createFacility(req, resp);
                break;
            case "/update":
                updateFacility(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing.");
            return;
        }

        System.out.println(pathInfo);

        switch (pathInfo) {
            case "/":
                getFacilityList(req, resp);
                break;
            case "/view":
                getFacilityById(req, resp);
                break;
            case "/delete":
                deleteFacility(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }
    }

    private void createFacility(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part filePart = req.getPart("image");
        String fileName = filePart.getSubmittedFileName();
        String externalUploadPath = "D:\\workspace\\ABC-Restaurant\\src\\main\\webapp\\assets\\uploads";
        File uploadDir = new File(externalUploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String filePath = externalUploadPath + File.separator + fileName;
        filePart.write(filePath);
        String relativePath = "/uploads/" + fileName;
        Facility facility = new Facility();
        facility.setRestaurantId(Integer.parseInt(req.getParameter("restaurantId")));
        facility.setName(req.getParameter("name"));
        facility.setDescription(req.getParameter("description"));
        facility.setCategory(req.getParameter("category"));
        facility.setImagePath(relativePath);
        facility.setStatus(req.getParameter("status"));

        try {
            FacilityDao.createFacility(facility);
            resp.sendRedirect(req.getContextPath() + "/admin/facilities/");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while creating the facility.");
        }
    }

    private void updateFacility(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Facility facility = FacilityDao.getFacilityById(id);
            if (facility == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Facility not found.");
                return;
            }
            String relativePath = facility.getImagePath();
            System.out.println(relativePath);

            Part filePart = req.getPart("image");
            System.out.println(filePart);
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                String externalUploadPath = "D:\\workspace\\ABC-Restaurant\\src\\main\\webapp\\assets\\uploads";
                File uploadDir = new File(externalUploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String filePath = externalUploadPath + File.separator + fileName;
                filePart.write(filePath);
                relativePath = "/uploads/" + fileName;
            }
            System.out.println(relativePath);

            facility.setRestaurantId(Integer.parseInt(req.getParameter("restaurantId")));
            facility.setName(req.getParameter("name"));
            facility.setDescription(req.getParameter("description"));
            facility.setCategory(req.getParameter("category"));
            facility.setImagePath(relativePath);
            facility.setStatus(req.getParameter("status"));

            FacilityDao.updateFacility(facility);

            resp.sendRedirect(req.getContextPath() + "/admin/facilities/");
        } catch (ServletException | ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating the facility.");
        }
    }

    private void getFacilityById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Facility facility = FacilityDao.getFacilityById(id);
            if (facility != null) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                String jsonResponse = new Gson().toJson(facility);
                resp.getWriter().write(jsonResponse);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "facility not found.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the facility.");
        }
    }

    private void deleteFacility(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        try {
            boolean deleted = FacilityDao.deleteFacility(id);
            if (deleted) {
                resp.sendRedirect(req.getContextPath() + "/admin/facilities/");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Facility not found.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while deleting the facility.");
        }
    }

    private void getFacilityList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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

            String pageTitle = "Manage Facilities";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/manageFacilities.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the facility list.");
        }
    }
}
