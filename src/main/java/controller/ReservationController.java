package controller;

import com.google.gson.Gson;
import dao.*;
import factory.UserFactory;
import jakarta.mail.MessagingException;
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

@WebServlet("/reservation/*")
public class ReservationController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing.");
            return;
        }

        switch (pathInfo) {
            case "/confirm":
                confirmReservation(req, resp);
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
                getReservationPage(req, resp);
                break;
            case "/check":
                checkAvailability(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }

    }
    private void getReservationPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            // Get restaurant list
            List<Restaurant> restaurantList = RestaurantDao.getAllRestaurants();
            req.setAttribute("restaurantList", restaurantList);

            String pageTitle = "ABC Restaurant Check Availability";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/reservation.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving.");
        }
    }

    private void checkAvailability(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int restaurantId = Integer.parseInt(req.getParameter("restaurantId"));
        String date = req.getParameter("date");
        String time = req.getParameter("time");

        try {
            String availabilityStatus = ReservationDao.checkAvailability(restaurantId, date, time);

            if ("available".equals(availabilityStatus)) {
                req.setAttribute("title", "ABC Restaurant Reservation Checkout");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/reservation-checkout.jsp");
                dispatcher.forward(req, resp);
            } else {
                req.setAttribute("errorMessage", availabilityStatus);
                getReservationPage(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while checking availability.");
        }
    }

    private void confirmReservation(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String firstName = user.getFirstName();
        String email = user.getEmail();

        Reservation reservation = new Reservation();
        reservation.setName(req.getParameter("name"));
        reservation.setDate(req.getParameter("date"));
        reservation.setTime(req.getParameter("time"));
        reservation.setPhoneNumber(req.getParameter("phoneNumber"));
        reservation.setRestaurantId(Integer.parseInt(req.getParameter("restaurantId")));
        reservation.setCustomerId(Integer.parseInt(req.getParameter("customerId")));
        reservation.setNoOfPeople(Integer.parseInt(req.getParameter("noOfPeople")));
        try {
            ReservationDao.createReservation(reservation);
            int id = ReservationDao.getLastOrderId();
            String ReservationId = ReservationDao.getReservationId(id);
            Setting setting = SettingDao.getSettingById();
            String serverEmail = setting.getServerEmail();
            String serverPassword = setting.getServerPassword();
            String subject = "Reservation Confirmation";
            String messageContent = "Dear " + firstName + ",\n\nThank you for your reservation!\n\nYour Reservation ID is: "+ReservationId+"\n\nBest regards,\nABC Restaurant";
            EmailUtil.sendEmail(email, subject, messageContent,serverEmail,serverPassword);
            req.setAttribute("reservationId", ReservationId);
            String pageTitle = "ABC Restaurant Reservation Confirmation";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/reservation-confirm.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException | MessagingException e) {
            e.printStackTrace();
        }
    }
}
