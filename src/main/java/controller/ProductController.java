package controller;

import com.google.gson.Gson;
import dao.ProductDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Product;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/products/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class ProductController extends HttpServlet {
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
                createProduct(req, resp);
                break;
            case "/update":
                updateProduct(req, resp);
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
                getProductList(req, resp);
                break;
            case "/view":
                getProductById(req, resp);
                break;
            case "/delete":
                deleteProduct(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }
    }

    private void createProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part filePart = req.getPart("image");
        String fileName = filePart.getSubmittedFileName();
        String externalUploadPath = "D:\\workspace\\ABC-Restaurant\\src\\main\\webapp\\assets\\uploads\\products";
        File uploadDir = new File(externalUploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String filePath = externalUploadPath + File.separator + fileName;
        filePart.write(filePath);
        String relativePath = "/uploads/products/" + fileName;
        Product product = new Product();
        product.setName(req.getParameter("name"));
        product.setDescription(req.getParameter("description"));
        product.setCategory(req.getParameter("category"));
        product.setPrice(Double.parseDouble(req.getParameter("price")));
        product.setImagePath(relativePath);

        try {
            ProductDao.createProduct(product);
            resp.sendRedirect(req.getContextPath() + "/admin/products/");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while creating the product.");
        }
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Product product = ProductDao.getProductById(id);
            if (product == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found.");
                return;
            }
            String relativePath = product.getImagePath();
            System.out.println(relativePath);

            Part filePart = req.getPart("image");
            System.out.println(filePart);
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                String externalUploadPath = "D:\\workspace\\ABC-Restaurant\\src\\main\\webapp\\assets\\uploads\\products";
                File uploadDir = new File(externalUploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String filePath = externalUploadPath + File.separator + fileName;
                filePart.write(filePath);
                relativePath = "/uploads/products/" + fileName;
            }
            System.out.println(relativePath);

            product.setName(req.getParameter("name"));
            product.setDescription(req.getParameter("description"));
            product.setCategory(req.getParameter("category"));
            product.setPrice(Double.parseDouble(req.getParameter("price")));
            product.setImagePath(relativePath);
            product.setStatus(req.getParameter("status"));

            ProductDao.updateProduct(product);

            resp.sendRedirect(req.getContextPath() + "/admin/products/");
        } catch (ServletException | ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating the product.");
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

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        try {
            boolean deleted = ProductDao.deleteProduct(id);
            if (deleted) {
                resp.sendRedirect(req.getContextPath() + "/admin/products/");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while deleting the product.");
        }
    }

    private void getProductList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
            String filterProductType = req.getParameter("filterProductType");

            List<Product> productList = ProductDao.getProductList(search, filterProductType, page, size);
            int totalProduct = ProductDao.getProductCount(search, filterProductType);
            int totalPages = (int) Math.ceil((double) totalProduct / size);

            req.setAttribute("productList", productList);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("size", size);
            req.setAttribute("search", search);
            req.setAttribute("filterProductType", filterProductType);

            String pageTitle = "Manage Products";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/manageProducts.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the product list.");
        }
    }
}
