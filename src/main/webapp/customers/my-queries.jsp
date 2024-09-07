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
                            <h4 class="fw-bold">My Queries</h4>
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
                                <th>Subject</th>
                                <th>Status</th>
                                <th>Options</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="query" items="${queryList}">
                                <tr>
                                    <td>${query.id}</td>
                                    <td>${query.subject}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${query.status == 'Answered'}">
                                                <span class="badge text-bg-success">${query.status}</span>
                                            </c:when>
                                            <c:when test="${query.status == 'Pending'}">
                                                <span class="badge text-bg-primary">${query.status}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge text-bg-danger">${query.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <button class="btn btn-primary view-order-btn"
                                                data-id="${query.id}"
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
                <h1 class="modal-title fs-5" id="updateModelLabel">Query</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <h3 id="subject" class="text-center"></h3>
                <p id="description" class="my-3"></p>
                <p id="response" class="text-end"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script>
    <%@include file="../assets/js/my-queries.js"%>
</script>
<%@ include file="../template/footer.jsp"%>
