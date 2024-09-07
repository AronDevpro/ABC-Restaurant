package controller;

import dao.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;
import util.EmailUtil;

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

        HttpSession sessionUser = req.getSession();
        User user = (User) sessionUser.getAttribute("user");
        String firstName = user.getFirstName();
        String email = user.getEmail();

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
        if ("Take Away".equals(req.getParameter("deliveryMethod"))) {
            order.setRestaurantSelect(req.getParameter("restaurantSelect"));
        }
        if ("Dine In".equals(req.getParameter("deliveryMethod"))) {
            order.setRestaurantSelect(req.getParameter("restaurantSelect"));
        }

        if ("Deliver".equals(req.getParameter("deliveryMethod"))) {
            order.setStreetAddress(req.getParameter("streetAddress"));
            order.setCity(req.getParameter("city"));
            order.setZip(req.getParameter("zip"));
        }
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
                OrderItems orderItem = new OrderItems();
                orderItem.setOrderId(orderId);
                orderItem.setItem(item);
                orderItem.setQuantity(quantity);
                OrderDao.addOrderItems(orderItem);
            }
            cartDao.clearCart();

            // create transaction
            Transaction transaction= new Transaction();
            transaction.setOrderId(orderId);
            transaction.setPaymentMethod(req.getParameter("paymentMethod"));
            transaction.setTotal(Double.parseDouble(req.getParameter("total")));

            TransactionDao.createTransaction(transaction);

            String orderUUID = OrderDao.getUUID(orderId);

            Setting setting = SettingDao.getSettingById();
            String serverEmail = setting.getServerEmail();
            String serverPassword = setting.getServerPassword();
            String subject = "Order Confirmation";
            String messageContent = "Dear " + firstName + ",\n\nThank you for your order!\n\nYour Order ID is: "+orderUUID+"\n\nBest regards,\nABC Restaurant";
            EmailUtil.sendEmail(email, subject, messageContent,serverEmail,serverPassword);

            req.setAttribute("orderUUID", orderUUID);
            req.setAttribute("title", "Order Confirmation");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/order-confirm.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while creating the order.");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
