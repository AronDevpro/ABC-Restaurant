<%--
  Created by IntelliJ IDEA.
  User: Arosha
  Date: 9/1/2024
  Time: 9:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../template/header.jsp" %>
<%@ include file="../template/staffSidebar.jsp" %>

<div class="container">
    <nav aria-label="breadcrumb" class="m-3">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="/staff/">Dashboard</a></li>
            <li class="breadcrumb-item active" aria-current="page">Manage Reservation</li>
        </ol>
    </nav>
    <div class="row">
        <div class="col-12">
            <div class="card m-3">
                <div class="card-header">
                    <div class="row align-items-center">
                        <div class="col-5 col-lg-3">
                            <h4 class="fw-bold">Reservations</h4>
                        </div>
                        <div class="col-5 col-lg-6">
                            <form method="GET" action="" id="searchForm">
                                <div class="input-group">
                                    <input class="form-control" type="text" name="search" placeholder="Search"
                                           aria-label="Search" value="${param.search}" oninput="submitSearchForm()"/>
                                </div>
                            </form>
                        </div>
                        <div class="col-2 col-md-3 col-lg-3">
                            <form method="GET" action="" id="filterForm">
                                <select name="filterStatusType" class="form-select" onchange="submitSelectForm()">
                                    <option value="">All</option>
                                    <option value="pending" ${param.filterStatusType == 'pending' ? 'selected' : ''}>Pending</option>
                                    <option value="confirm" ${param.filterStatusType == 'confirm' ? 'selected' : ''}>Confirm</option>
                                </select>
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
                                <th>Name</th>
                                <th>Contact</th>
                                <th>Restaurant</th>
                                <th>Date</th>
                                <th>Time</th>
                                <th>No Of People</th>
                                <th>Status</th>
                                <th>Option</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="reservation" items="${reservationList}">
                                <tr>
                                    <td>${reservation.id}</td>
                                    <td>${reservation.name}</td>
                                    <td>${reservation.phoneNumber}</td>
                                    <td id="restaurantName-${reservation.restaurantId}">Loading...</td>
                                    <td>${reservation.date}</td>
                                    <td>${reservation.time}</td>
                                    <td>${reservation.noOfPeople}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${reservation.status == 'confirm'}">
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
                                    <td>
                                        <c:choose>
                                            <c:when test="${reservation.status == 'confirm'}">
                                            </c:when>
                                            <c:when test="${reservation.status == 'pending'}">
                                                <a href="${pageContext.request.contextPath}/staff/reservations/status?id=${reservation.id}&status=confirm" class="btn btn-primary">Confirm</a> |
                                                <a href="${pageContext.request.contextPath}/staff/reservations/status?id=${reservation.id}&status=cancel" class="btn btn-danger">Cancel</a>
                                            </c:when>
                                            <c:otherwise>
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

<%@ include file="../template/sidebarFooter.jsp" %>
