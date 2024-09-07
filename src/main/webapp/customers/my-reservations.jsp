<%@ page import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // user details from session
    User user = (User) session.getAttribute("user");
    Integer customerId = (user != null) ? user.getId() : null;
%>
<!doctype html>
<html>
<%@ include file="../template/header.jsp"%>

<body>
<%@ include file="../template/topNav.jsp"%>

<div class="container">
    <div class="row">
        <div class="col-12">
            <div class="card m-3">
                <div class="card-header">
                    <div class="row align-items-center">
                        <div class="col-6 col-lg-5">
                            <h4 class="fw-bold">My Reservations</h4>
                        </div>
                        <div class="col-6 col-lg-7">
                            <form method="GET" action="" id="searchForm">
                                <div class="input-group">
                                    <input class="form-control" type="text" name="search" placeholder="Search"
                                           aria-label="Search" value="${param.search}" oninput="submitSearchForm()"/>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover table-bordered">
                            <thead class="table-light">
                            <tr>
                                <th>#id</th>
                                <th>Restaurant</th>
                                <th>Date</th>
                                <th>No Of People</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="reservation" items="${reservationList}">
                                <tr>
                                    <td>${reservation.reservationId}</td>
                                    <td id="restaurantName-${reservation.restaurantId}">Loading...</td>
                                    <td>${reservation.date}</td>
                                    <td>${reservation.noOfPeople}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${reservation.status == 'completed'}">
                                                <span class="badge text-bg-success">${reservation.status}</span>
                                            </c:when>
                                            <c:when test="${reservation.status == 'pending'}">
                                                <span class="badge text-bg-primary">${reservation.status}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge text-bg-danger">${reservation.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="card-footer">
                    <nav aria-label="Page navigation">
                        <ul class="pagination">
                            <c:if test="${currentPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=${currentPage - 1}&size=${size}">Previous</a>
                                </li>
                            </c:if>
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="?page=${i}&size=${size}">${i}</a>
                                </li>
                            </c:forEach>
                            <c:if test="${currentPage < totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=${currentPage + 1}&size=${size}">Next</a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    <%@include file="../assets/js/my-reservation.js"%>
</script>
<%@ include file="../template/footer.jsp"%>
