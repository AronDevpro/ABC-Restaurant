<%--
  Created by IntelliJ IDEA.
  User: Arosha
  Date: 9/3/2024
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<%@ include file="template/header.jsp"%>
<body>
<%@ include file="template/topNav.jsp"%>
<section class="p-4">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-10 text-center mt-5">
                <div class="four_zero_four_bg">
                    <h1 class="text-center ">404</h1>
                </div>
                <div>
                    <h3>
                        Look like you're lost
                    </h3>
                    <p>the page you are looking for not available!</p>
                    <a href="/" class="link_404">Go to Home</a>
                </div>
            </div>
        </div>
    </div>
</section>
<%@ include file = "template/footer.jsp"%>
