<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html>
<%@ include file="template/header.jsp"%>
<body>
<%@ include file="template/topNav.jsp"%>

<div class="container">
<div class="container text-center mb-3" style="border:1px solid #e3e0e0; border-radius: 5px; padding-top: 50px; padding-bottom: 50px">
    <form method="get" action="check">
    <div class="row">
        <div class="col">
            <div class="form-floating">
                <select class="form-select" id="restaurant" name="restaurantId">
                    <c:forEach var="restaurant" items="${restaurantList}">
                        <option value="${restaurant.id}">${restaurant.name}</option>
                    </c:forEach>
                </select>
                <label for="restaurant">Select Restaurant</label>
            </div>
        </div>
        <div class="col">
            <div class="form-floating mb-3">
                <input type="date" class="form-control" id="date" placeholder="date" name="date">
                <label for="date">Date</label>
            </div>
        </div>
        <div class="col">
            <div class="form-floating mb-3">
                <input type="time" class="form-control" id="time" placeholder="time" name="time">
                <label for="time">Time</label>
            </div>
        </div>
        <div class="col">
            <button class="btn btn-primary col-12 h-75" type="submit">Check Availability</button>
        </div>
    </div>
    </form>
</div>

<c:if test="${not empty errorMessage}">
<div class="alert alert-danger">${errorMessage}</div>
</c:if>
</div>
<%@ include file="template/footer.jsp"%>
