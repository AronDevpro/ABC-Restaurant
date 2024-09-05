<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../template/header.jsp" %>
<%@ include file="../template/sideBar.jsp" %>
<section>
    <div class="container-fluid">
        <nav aria-label="breadcrumb" class="m-3">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                <li class="breadcrumb-item active" aria-current="page">Report</li>
            </ol>
        </nav>
        <div class="container text-center mb-3" style="border:1px solid #e3e0e0; border-radius: 5px; padding-top: 20px; padding-bottom: 20px">
            <form method="POST" action="">
                <div class="row">
                    <div class="col">
                        <div class="form-floating">
                            <select class="form-select" id="report" name="report">
                                <option value="orders">Orders</option>
                                <option value="reservations">Reservations</option>
                            </select>
                            <label for="report">Select Report Preferred</label>
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-floating mb-3">
                            <input type="date" class="form-control" id="startDate" placeholder="date" name="startDate">
                            <label for="startDate">Start Date</label>
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-floating mb-3">
                            <input type="date" class="form-control" id="endDate" placeholder="date" name="endDate">
                            <label for="endDate">End Date</label>
                        </div>
                    </div>
                    <div class="col">
                        <button class="btn btn-primary col-12 h-75" type="submit">Generate Report</button>
                    </div>
                </div>
            </form>
        </div>
        <c:if test="${not empty reservationReportList}">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover table-bordered">
                    <thead class="table-light">
                    <tr>
                        <th>Restaurant Name</th>
                        <th>Date</th>
                        <th>Number of Reservation</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="reservation" items="${reservationReportList}">
                        <tr>
                            <td>${reservation.restaurantName}</td>
                            <td>${reservation.date}</td>
                            <td>${reservation.numberOfReservations}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        </c:if>
        <c:if test="${not empty orderReportList}">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover table-bordered">
                    <thead class="table-light">
                    <tr>
                        <th>Restaurant Name</th>
                        <th>Deliver Date</th>
                        <th>Total Revenue</th>
                        <th>Number of Orders</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${orderReportList}">
                        <tr>
                            <td>${order.restaurantName}</td>
                            <td>${order.deliverDate}</td>
                            <td>${order.totalRevenue}</td>
                            <td>${order.numberOfOrders}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        </c:if>
    </div>
</section>

<%@ include file="../template/sidebarFooter.jsp" %>