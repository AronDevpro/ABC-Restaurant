package controller;

import com.google.gson.Gson;
import dao.OfferDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Offer;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/offers/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class OfferController extends HttpServlet {
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
                createOffer(req, resp);
                break;
            case "/update":
                updateOffer(req, resp);
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
                getOfferList(req, resp);
                break;
            case "/view":
                getOfferById(req, resp);
                break;
            case "/delete":
                deleteOffer(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }
    }

    private void createOffer(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part filePart = req.getPart("image");
        String fileName = filePart.getSubmittedFileName();
        String externalUploadPath = "D:\\workspace\\ABC-Restaurant\\src\\main\\webapp\\assets\\uploads\\offers";
        File uploadDir = new File(externalUploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String filePath = externalUploadPath + File.separator + fileName;
        filePart.write(filePath);
        String relativePath = "/uploads/offers/" + fileName;
        Offer offer = new Offer();
        offer.setOfferName(req.getParameter("offerName"));
        offer.setPromoCode(req.getParameter("promoCode"));
        offer.setDiscountPercentage(Double.parseDouble(req.getParameter("discountPercentage")));
        offer.setCategory(req.getParameter("category"));
        offer.setImagePath(relativePath);

        try {
            OfferDao.createOffer(offer);
            resp.sendRedirect(req.getContextPath() + "/admin/offers/");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while creating the offer.");
        }
    }

    private void updateOffer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Offer offer = OfferDao.getOfferById(id);
            if (offer == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Offer not found.");
                return;
            }
            String relativePath = offer.getImagePath();

            Part filePart = req.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                String externalUploadPath = "D:\\workspace\\ABC-Restaurant\\src\\main\\webapp\\assets\\uploads\\offers";
                File uploadDir = new File(externalUploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String filePath = externalUploadPath + File.separator + fileName;
                filePart.write(filePath);
                relativePath = "/uploads/offers/" + fileName;
            }
            System.out.println(relativePath);

            offer.setOfferName(req.getParameter("offerName"));
            offer.setPromoCode(req.getParameter("promoCode"));
            offer.setDiscountPercentage(Double.parseDouble(req.getParameter("discountPercentage")));
            offer.setCategory(req.getParameter("category"));
            offer.setImagePath(relativePath);
            offer.setStatus(req.getParameter("status"));

            OfferDao.updateOffer(offer);

            resp.sendRedirect(req.getContextPath() + "/admin/offers/");
        } catch (ServletException | ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating the offer.");
        }
    }

    private void getOfferById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Offer offer = OfferDao.getOfferById(id);
            if (offer != null) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                String jsonResponse = new Gson().toJson(offer);
                resp.getWriter().write(jsonResponse);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "offer not found.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the offer.");
        }
    }

    private void deleteOffer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        try {
            boolean deleted = OfferDao.deleteOffer(id);
            if (deleted) {
                resp.sendRedirect(req.getContextPath() + "/admin/offers/");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Offer not found.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while deleting the offer.");
        }
    }

    private void getOfferList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
            String filterOfferType = req.getParameter("filterOfferType");

            List<Offer> offerList = OfferDao.getOfferList(search, filterOfferType, page, size);
            int totalOffer = OfferDao.getOfferCount(search, filterOfferType);
            int totalPages = (int) Math.ceil((double) totalOffer / size);

            req.setAttribute("offerList", offerList);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("size", size);
            req.setAttribute("search", search);
            req.setAttribute("filterOfferType", filterOfferType);

            String pageTitle = "Manage Offer";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/manageOffers.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the offer list.");
        }
    }
}
