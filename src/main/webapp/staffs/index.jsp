<%--
  Created by IntelliJ IDEA.
  User: Arosha
  Date: 8/13/2024
  Time: 8:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<%@ include file = "../template/header.jsp"%>
<%@ include file = "../template/staffSidebar.jsp"%>
<nav aria-label="breadcrumb" class="m-3">
    <ol class="breadcrumb">
        <li class="breadcrumb-item active" aria-current="page">Dashboard</li>
    </ol>
</nav>
<div class="row justify-content-center mt-5 align-content-center">
    <div class="col-12 col-lg-10">
        <div class="row text-center wow fadeInUp" data-wow-delay=".6s">
            <div class="col-12 col-md-4">
                <div class="card bg-light text-black mb-4">
                    <div class="card-header"><h5>Pending Orders</h5></div>
                    <div class="card-body bg-dark"><h4
                            class="text-light">${orderCount}</h4>
                    </div>
                </div>
            </div>
            <div class="col-12 col-md-4">
                <div class="card bg-light text-black mb-4">
                    <div class="card-header"><h5>Pending Reservations</h5></div>
                    <div class="card-body bg-dark"><h4
                            class="text-light">${reservationCount}</h4>
                    </div>
                </div>
            </div>
            <div class="col-12 col-md-4">
                <div class="card bg-light text-black mb-4">
                    <div class="card-header"><h5>Pending Queries</h5></div>
                    <div class="card-body bg-dark"><h4
                            class="text-light">${queryCount}</h4></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-12 col-lg-5">
        <div class="card-body wow fadeInUp" data-wow-delay=".6s">
            <div class="table-responsive">
                <table class="table table-hover table-bordered">
                    <thead class="table-light">
                    <tr>
                        <th>#id</th>
                        <th>Name</th>
                        <th>Date/Time</th>
                        <th>No of People</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="reservation" items="${reservationList}">
                        <tr>
                            <td>${reservation.id}</td>
                            <td>${reservation.name}</td>
                            <td>${reservation.date} ${reservation.time}</td>
                            <td>${reservation.noOfPeople}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="col-12 col-lg-5">
        <div class="card-body wow fadeInUp" data-wow-delay=".6s">
            <div class="table-responsive">
                <table class="table table-hover table-bordered">
                    <thead class="table-light">
                    <tr>
                        <th>#id</th>
                        <th>Name</th>
                        <th>Method</th>
                        <th>Amount</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${orderList}">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.firstName} ${order.lastName}</td>
                            <td>${order.deliveryMethod}</td>
                            <td>${order.total}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%@ include file = "../template/sidebarFooter.jsp"%>
