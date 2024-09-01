<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html>
<%@ include file="template/header.jsp"%>
<body>
<%@ include file="template/topNav.jsp"%>

<div class="container text-center" style="border:1px solid #e3e0e0; border-radius: 5px; padding-top: 50px; padding-bottom: 50px">
  <h1>Order Confirmation</h1>
  <p>Thank you for your order!</p>
  <p>Your order ID is: ${orderUUID}</p>
</div>

<%@ include file="template/footer.jsp"%>
