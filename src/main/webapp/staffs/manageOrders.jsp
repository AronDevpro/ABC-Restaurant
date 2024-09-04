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
            <li class="breadcrumb-item active" aria-current="page">Manage Orders</li>
        </ol>
    </nav>
    <div class="row">
        <div class="col-12">
            <div class="card m-3">
                <div class="card-header">
                    <div class="row align-items-center">
                        <div class="col-5 col-lg-3">
                            <h4 class="fw-bold">Orders</h4>
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
                                    <option value="delivered" ${param.filterStatusType == 'delivered' ? 'selected' : ''}>Delivered</option>
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
                                <th>Phone Number</th>
                                <th>Date/Time</th>
                                <th>Method</th>
                                <th>Status</th>
                                <th>Option</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="order" items="${orderList}">
                                <tr>
                                    <td>${order.id}</td>
                                    <td>${order.firstName} ${order.lastName}</td>
                                    <td>${order.phoneNumber}</td>
                                    <td>${order.deliverDate} ${order.deliverTime}</td>
                                    <td>${order.deliveryMethod}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${order.status == 'delivered'}">
                                                <span class="badge text-bg-success">${order.status}</span>
                                            </c:when>
                                            <c:when test="${order.status == 'confirm'}">
                                                <span class="badge text-bg-success">${order.status}</span>
                                            </c:when>
                                            <c:when test="${order.status == 'pending'}">
                                                <span class="badge text-bg-primary">${order.status}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge text-bg-danger">${order.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <button class="btn btn-primary view-order-btn"
                                                data-id="${order.id}"
                                                data-bs-toggle="modal"
                                                data-bs-target="#updateModel">
                                            View
                                        </button>
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

<!-- Modal -->
<div class="modal fade" id="updateModel" tabindex="-1" aria-labelledby="updateModelLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="updateModelLabel">Order Summary</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col">
                        <p id="name"></p>
                    </div>
                    <div class="col">
                        <p id="phoneNumber"></p>
                    </div>
                </div>
                <p id="email" class="mb-3"></p>
                <div class="row">
                    <div class="col">
                        <p id="deliverDateTime" class="mb-3"></p>
                    </div>
                    <div class="col">
                        <p id="deliverMethod" class="mb-3"></p>
                    </div>
                </div>
                <div class="mb-3" id="restaurantArea">
                    <p id="restaurant" class="mb-3"></p>
                </div>
                <div class="col" id="addressArea">
                    <p id="streetAddress" class="mb-3"></p>
                </div>
                <p id="paymentMethod" class="mb-3"></p>
                <div class="mb-3">
                    <ul id="orderItemsList" class="px-0">
                    </ul>
                </div>
                <p id="total" class="mb-3"></p>
            </div>
            <div class="modal-footer">
                <a href="#" class="btn btn-primary" id="confirmButton">Confirm</a>
                <a href="#" class="btn btn-danger" id="cancelButton">Cancel</a>
                <a href="#" class="btn btn-success" id="deliveredButton">Delivered</a>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<%@ include file="../template/sidebarFooter.jsp" %>
