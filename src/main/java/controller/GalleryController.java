package controller;

import com.google.gson.Gson;
import dao.GalleryDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Gallery;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/galleries/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class GalleryController extends HttpServlet {
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
                createGallery(req, resp);
                break;
            case "/update":
                updateGallery(req, resp);
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
                getGalleryList(req, resp);
                break;
            case "/view":
                getGalleryById(req, resp);
                break;
            case "/delete":
                deleteGallery(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }
    }

    private void createGallery(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part filePart = req.getPart("image");
        String fileName = filePart.getSubmittedFileName();
        String externalUploadPath = "D:\\workspace\\ABC-Restaurant\\src\\main\\webapp\\assets\\uploads\\gallery";
        File uploadDir = new File(externalUploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String filePath = externalUploadPath + File.separator + fileName;
        filePart.write(filePath);
        String relativePath = "/uploads/gallery/" + fileName;
        Gallery gallery = new Gallery();
        gallery.setTitle(req.getParameter("title"));
        gallery.setDescription(req.getParameter("description"));
        gallery.setCategory(req.getParameter("category"));
        gallery.setImagePath(relativePath);

        try {
            GalleryDao.createGallery(gallery);
            resp.sendRedirect(req.getContextPath() + "/admin/galleries/");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while creating the gallery.");
        }
    }

    private void updateGallery(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Gallery gallery = GalleryDao.getGalleryById(id);
            if (gallery == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Gallery not found.");
                return;
            }
            String relativePath = gallery.getImagePath();
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
                relativePath = "/uploads/gallery/" + fileName;
            }
            System.out.println(relativePath);

            gallery.setTitle(req.getParameter("title"));
            gallery.setDescription(req.getParameter("description"));
            gallery.setCategory(req.getParameter("category"));
            gallery.setImagePath(relativePath);
            gallery.setStatus(req.getParameter("status"));

            GalleryDao.updateGallery(gallery);

            resp.sendRedirect(req.getContextPath() + "/admin/galleries/");
        } catch (ServletException | ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating the gallery.");
        }
    }

    private void getGalleryById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Gallery gallery = GalleryDao.getGalleryById(id);
            if (gallery != null) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                String jsonResponse = new Gson().toJson(gallery);
                resp.getWriter().write(jsonResponse);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "gallery not found.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the gallery.");
        }
    }

    private void deleteGallery(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        try {
            boolean deleted = GalleryDao.deleteGallery(id);
            if (deleted) {
                resp.sendRedirect(req.getContextPath() + "/admin/galleries/");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Gallery not found.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while deleting the gallery.");
        }
    }

    private void getGalleryList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
            String filterGalleryType = req.getParameter("filterUserType");

            List<Gallery> galleryList = GalleryDao.getGalleryList(search, filterGalleryType, page, size);
            int totalGallery = GalleryDao.getGalleryCount(search, filterGalleryType);
            int totalPages = (int) Math.ceil((double) totalGallery / size);

            req.setAttribute("galleryList", galleryList);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("size", size);
            req.setAttribute("search", search);
            req.setAttribute("filterUserType", filterGalleryType);

            String pageTitle = "Manage Galleries";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/manageGalleries.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the gallery list.");
        }
    }
}
