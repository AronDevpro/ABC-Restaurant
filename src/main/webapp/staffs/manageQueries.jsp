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
<%@ include file="../template/sideBar.jsp" %>

<div class="container">
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
                                <th>Subject</th>
                                <th>Phone Number</th>
                                <th>Order Id</th>
                                <th>Status</th>
                                <th>Option</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="query" items="${queryList}">
                                <tr>
                                    <td>${query.id}</td>
                                    <td id="customerName-${query.customerId}">Loading...</td>
                                    <td>${query.subject}</td>
                                    <td>${query.phoneNumber}</td>
                                    <td>${query.orderId}</td>
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
                                        <c:choose>
                                            <c:when test="${query.status == 'Answered'}">
                                            </c:when>
                                            <c:when test="${query.status == 'Pending'}">
                                                | <a href="${pageContext.request.contextPath}/staff/queries/status?id=${query.id}&status=Closed" class="btn btn-danger">Close</a>
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

<!-- Modal -->
<div class="modal fade" id="updateModel" tabindex="-1" aria-labelledby="updateModelLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="response" method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="updateModelLabel">Query</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <h3 id="subject" class="text-center"></h3>
                    <p id="description" class="my-3"></p>
                    <div id="responseSection">
                        <p id="responseText" class="text-end"></p>
                    </div>
                    <input type="number" hidden value="" name="id" id="queryId">
                    <div class="form-floating" id="responseTextArea">
                        <textarea class="form-control" placeholder="Response" id="response" name="response" style="height: 100px"></textarea>
                        <label for="response">Response</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" id="replyButton">Reply</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </form>
    </div>
</div>


<script>
    document.addEventListener('DOMContentLoaded', function() {
        document.querySelectorAll('td[id^="customerName-"]').forEach(function (td) {
            const id = td.id.split('-')[1];
            fetchCustomerName(id, td);
        });

        function fetchCustomerName(id, tdElement) {
            fetch('<%= request.getContextPath() %>/admin/users/view?id=' + id)
                .then(response => response.json())
                .then(data => {
                    tdElement.textContent = data.firstName;
                })
                .catch(error => {
                    console.error('Error fetching customer details:', error);
                    tdElement.textContent = 'Error';
                });
        }
    })
    function submitSelectForm() {
        document.getElementById('filterForm').submit();
    }

    document.querySelectorAll('.view-order-btn').forEach(function (button) {
        button.addEventListener('click', function () {
            const id = this.getAttribute('data-id');
            document.getElementById('queryId').value = id;

            // Fetch order items and update the modal content
            fetch('<%= request.getContextPath() %>/customer/view-query?id=' + id)
                .then(response => response.json())
                .then(data => {
                    document.getElementById('subject').textContent = data.subject;
                    document.getElementById('description').textContent = data.description;
                    if (data.response && data.response.trim() !== "") {
                        document.getElementById('responseText').textContent = data.response;
                        document.getElementById('responseTextArea').style.display = 'none';
                        document.getElementById('replyButton').style.display = 'none';
                    } else {
                        document.getElementById('responseTextArea').style.display = 'block';
                        document.getElementById('replyButton').style.display = 'inline-block';
                    }
                })
                .catch(error => console.error('Error fetching order item details:', error));
        });
    });
</script>

<%@ include file="../template/sidebarFooter.jsp" %>
