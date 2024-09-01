<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html>
<%@ include file="template/header.jsp"%>

<body>
<%@ include file="template/topNav.jsp"%>

<div class="container text-center col-md-6 col-12" style="border:1px solid #e3e0e0; border-radius: 5px; padding-top: 20px; padding-bottom: 20px">
    <h1 class="mb-3 text-center">Contact Us</h1>
    <form action="${pageContext.request.contextPath}/admin/users/update" method="POST">
        <input type="hidden" name="id">
        <div class="form-floating mb-3">
            <input type="text" class="form-control" name="subject" id="subject" placeholder="Enter the Subject">
            <label for="subject">Subject</label>
        </div>
        <div class="row mb-3">
            <div class="col">
                <div class="form-floating">
                    <input type="text" class="form-control" name="email" id="email" placeholder="email">
                    <label for="email">Email Address</label>
                </div>
            </div>
            <div class="col">
                <div class="form-floating">
                    <input type="text" class="form-control" name="phoneNumber" id="phoneNumber" placeholder="Your Phone Number here">
                    <label for="phoneNumber">Phone Number</label>
                </div>
            </div>
        </div>


        <div class="form-floating mb-3">
            <textarea class="form-control" placeholder="Your Description here" id="description" style="height: 100px"></textarea>
            <label for="description">Description</label>
        </div>
        <button class="btn btn-primary" type="submit">Submit</button>
    </form>
</div>

<%@ include file="template/footer.jsp"%>
