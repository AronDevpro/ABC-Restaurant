package controller;

import com.google.gson.Gson;
import dao.CartDao;
import dao.ProductDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Product;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/products/*")
public class HomeProductController extends HttpServlet {
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
            case "/addToCart":
                addToCart(req, resp);
                break;
            case "/removeFromCart":
                removeFromCart(req, resp);
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
            case "/cart":
                getCarts(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
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
            String filterCategory = req.getParameter("filterCategory");

            List<Product> productList = ProductDao.getProductList(search, filterCategory, page, size);
            int totalProduct = ProductDao.getProductCount(search, filterCategory);
            int totalPages = (int) Math.ceil((double) totalProduct / size);

            req.setAttribute("productList", productList);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("size", size);
            req.setAttribute("search", search);
            req.setAttribute("filterCategory", filterCategory);

            String pageTitle = "Products List";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/product.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the product list.");
        }
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double price = Double.parseDouble(request.getParameter("price"));
        String name = request.getParameter("name");
        System.out.println(price);
        HttpSession session = request.getSession();
        CartDao cartDao = (CartDao) session.getAttribute("cart");

        if (cartDao == null) {
            cartDao = new CartDao();
            session.setAttribute("cart", cartDao);
        }

        try {
            cartDao.addItem(productId, quantity, name, price);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while adding the item to the cart.");
        }
    }

    private void getCarts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            CartDao cartDao = (CartDao) session.getAttribute("cart");

            if (cartDao == null) {
                cartDao = new CartDao();
                session.setAttribute("cart", cartDao);
            }

            Map<String, Cart> cartItems = cartDao.getItems();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            String jsonResponse = new Gson().toJson(cartItems);
            resp.getWriter().write(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the cart items.");
        }
    }
    private void removeFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");
        System.out.println(productId);

        HttpSession session = request.getSession();
        CartDao cartDao = (CartDao) session.getAttribute("cart");

        if (cartDao == null) {
            cartDao = new CartDao();
            session.setAttribute("cart", cartDao);
        }

        try {
            cartDao.removeItem(productId);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while removing the item from the cart.");
        }
    }
}
