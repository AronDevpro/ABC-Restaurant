<%@ page import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // user details from session
    User user = (User) session.getAttribute("user");
    Integer customerId = (user != null) ? user.getId() : null;
    String accountType = (user != null) ? user.getAccountType() : null;
%>
<!doctype html>
<html>
<%@ include file="../template/header.jsp"%>

<body>
<%@ include file="../template/topNav.jsp"%>

<div class="container text-center col-md-6 col-12" style="border:1px solid #e3e0e0; border-radius: 5px; padding-top: 20px; padding-bottom: 20px">
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <h1 class="mb-3 text-center">My Account</h1>
    <form action="update" method="POST">
        <input type="number" hidden name="customerId" value="<%= customerId %>">
        <input type="text" hidden name="accountType" value="<%= accountType %>">
        <div class="row">
            <div class="col">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="firstName" placeholder="Enter First Name" name="firstName">
                    <label for="firstName">First Name</label>
                </div>
            </div>
            <div class="col">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="lastName" placeholder="Enter Last Name" name="lastName">
                    <label for="lastName">Last Name</label>
                </div>
            </div>
        </div>

        <div class="form-floating mb-3">
            <input type="email" class="form-control" id="email" placeholder="Enter Email" readonly name="email">
            <label for="email">Email</label>
        </div>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="address" placeholder="Enter Address" name="address">
            <label for="address">Address</label>
        </div>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="phoneNumber" placeholder="Enter Phone Number" name="phoneNumber">
            <label for="phoneNumber">Phone Number</label>
        </div>
        <div class="form-floating mb-3">
            <input type="password" class="form-control" id="password" placeholder="Enter Password" name="password">
            <label for="password">Update Current Password</label>
        </div>
        <button class="btn btn-primary" type="submit">Update Account</button>
    </form>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const customerId = '<%= customerId %>';

        if (customerId) {
            fetch('<%= request.getContextPath() %>/admin/users/view?id=' + customerId)
                .then(response => response.json())
                .then(data => {
                    document.querySelector('input[name="firstName"]').value = data.firstName || '';
                    document.querySelector('input[name="lastName"]').value = data.lastName || '';
                    document.querySelector('input[name="email"]').value = data.email || '';
                    document.querySelector('input[name="phoneNumber"]').value = data.phoneNumber || '';
                    document.querySelector('input[name="address"]').value = data.address || '';
                })
                .catch(error => console.error('Error fetching user details:', error));
        }
    });
</script>
<%@ include file="../template/footer.jsp"%>
