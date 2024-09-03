package controller;
import com.google.gson.Gson;
import dao.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/staff/*")
public class StaffController extends HttpServlet {
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
//                createQuery(req, resp);
                break;
            case "/response":
                updateResponse(req, resp);
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
//                customerAccount(req, resp);
                break;
            case "/queries":
                getAllQueryPage(req, resp);
                break;
            case "/queries/status":
                queryStatusChange(req, resp);
                break;
            case "/reservations":
                getAllReservationPage(req, resp);
                break;
            case "/reservations/status":
                reservationStatusChange(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                break;
        }


    }

    private void getAllReservationPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

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

            // Get search parameters
            String search = req.getParameter("search");
            String filterStatusType = req.getParameter("filterStatusType");

            // Fetch orders
            List<Reservation> reservations = ReservationDao.getReservationList(search,filterStatusType, page, size);

            // Get the total count of orders for pagination
            int totalReservation = ReservationDao.getReservationCount(search,filterStatusType);
            int totalPages = (int) Math.ceil((double) totalReservation / size);

            req.setAttribute("reservationList", reservations);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("size", size);
            req.setAttribute("search", search);
            req.setAttribute("filterStatusType", filterStatusType);

            String pageTitle = "ABC Restaurant Reservation";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/staffs/manageReservations.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving orders.");
        }
    }

    private void reservationStatusChange(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String status = (req.getParameter("status"));

        try {
            ReservationDao.updateStatus(id,status);
            resp.sendRedirect(req.getContextPath() + "/staff/reservations");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while changing status");
        }
    }

    private void getAllQueryPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

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

            // Get search parameters
            String search = req.getParameter("search");
            String filterStatusType = req.getParameter("filterStatusType");

            // Fetch orders
            List<Query> queries = QueriesDao.getQueryList(search,filterStatusType, page, size);

            // Get the total count of queries for pagination
            int totalReservation = QueriesDao.getQueryCount(search,filterStatusType);
            int totalPages = (int) Math.ceil((double) totalReservation / size);

            req.setAttribute("queryList", queries);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("size", size);
            req.setAttribute("search", search);
            req.setAttribute("filterStatusType", filterStatusType);

            String pageTitle = "ABC Restaurant Queries";
            req.setAttribute("title", pageTitle);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/staffs/manageQueries.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving query.");
        }
    }

    private void queryStatusChange(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String status = (req.getParameter("status"));

        try {
            QueriesDao.updateStatus(id,status);
            resp.sendRedirect(req.getContextPath() + "/staff/queries");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while changing status");
        }
    }

    private void updateResponse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String response = (req.getParameter("response"));

        try {
            QueriesDao.updateResponse(id,response);
            resp.sendRedirect(req.getContextPath() + "/staff/queries");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while changing status");
        }
    }
}
