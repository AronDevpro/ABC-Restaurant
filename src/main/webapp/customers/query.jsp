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

<div class="container text-center col-md-6 col-12" style="border:1px solid #e3e0e0; border-radius: 5px; padding-top: 20px; padding-bottom: 20px">
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <h1 class="mb-3 text-center">Submit A Query</h1>
    <form action="create" method="POST">
        <input type="number" hidden name="customerId" value="<%= customerId %>">
        <div class="form-floating mb-3">
            <input type="text" class="form-control" name="subject" id="subject" placeholder="Enter the Subject">
            <label for="subject">Subject</label>
        </div>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" name="orderId" id="orderId" placeholder="Order Id here (optional)">
            <label for="orderId">Order Id (Optional)</label>
        </div>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" name="phoneNumber" id="phoneNumber" placeholder="Your Phone Number here">
            <label for="phoneNumber">Phone Number</label>
        </div>
        <div class="form-floating mb-3">
            <textarea class="form-control" placeholder="Your Description here" id="description" name="description" style="height: 100px"></textarea>
            <label for="description">Description</label>
        </div>
        <button class="btn btn-primary" type="submit">Submit Query</button>
    </form>
</div>

<%@ include file="../template/footer.jsp"%>
