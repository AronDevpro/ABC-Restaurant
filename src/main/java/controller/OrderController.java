package controller;

import dao.CartDao;
import dao.OrderDao;
import dao.RestaurantDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/checkout/*")
public class OrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing.");
            return;
        }

        switch (pathInfo) {
            case "/create":
                completeOrder(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            getCartList(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
        }
    }

    private void getCartList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            CartDao cartDao = (CartDao) session.getAttribute("cart");

            if (cartDao == null) {
                cartDao = new CartDao();
                session.setAttribute("cart", cartDao);
            }

            Map<String, Cart> cartItems = cartDao.getItems();
            double cartTotal = cartDao.getTotal();

            req.setAttribute("cartItems", cartItems);
            req.setAttribute("cartTotal", cartTotal);

            List<Restaurant> restaurantList = RestaurantDao.getAllRestaurants();
            req.setAttribute("restaurantList", restaurantList);

            req.setAttribute("title", "Checkout");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/checkout.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the cart list.");
        }
    }

    private void completeOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        CartDao cartDao = (CartDao) session.getAttribute("cart");

        if (cartDao == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cart is empty.");
            return;
        }
        Order order = new Order();
        order.setFirstName(req.getParameter("firstName"));
        order.setLastName(req.getParameter("lastName"));
        order.setEmail(req.getParameter("email"));
        order.setPhoneNumber(req.getParameter("phoneNumber"));
        order.setDeliverDate(req.getParameter("deliverDate"));
        order.setDeliverTime(req.getParameter("deliverTime"));
        order.setDeliveryMethod(req.getParameter("deliveryMethod"));
        if ("takeAway".equals(req.getParameter("deliveryMethod"))) {
            order.setRestaurantSelect(req.getParameter("restaurantSelect"));
        }

        if ("deliver".equals(req.getParameter("deliveryMethod"))) {
            order.setStreetAddress(req.getParameter("streetAddress"));
            order.setCity(req.getParameter("city"));
            order.setZip(req.getParameter("zip"));
        }
        order.setPaymentMethod(req.getParameter("paymentMethod"));
        order.setTotal(Double.parseDouble(req.getParameter("total")));
        order.setCustomerId(Integer.parseInt(req.getParameter("customerId")));

        try {
            OrderDao.createOrder(order);

            int orderId = OrderDao.getLastOrderId();

            // Add order items
            Map<String, Cart> cartItems = cartDao.getItems();
            for (Map.Entry<String, Cart> entry : cartItems.entrySet()) {
                String item = entry.getValue().getName();
                int quantity = entry.getValue().getQuantity();
                OrderItems orderItem = new OrderItems(orderId, item, quantity);
                OrderDao.addOrderItems(orderItem);
            }

            cartDao.clearCart();

            String orderUUID = OrderDao.getUUID(orderId);
            req.setAttribute("orderUUID", orderUUID);
            req.setAttribute("title", "Order Confirmation");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/order-confirm.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while creating the order.");
        }
    }
}
