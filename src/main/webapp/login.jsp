<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: Arosha
  Date: 8/11/2024
  Time: 11:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<%@ include file = "template/header.jsp"%>
<body>
<%
    session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null) {
        User user = (User) session.getAttribute("user");
        String accountType = user.getAccountType();
        if ("Admin".equals(accountType)) {
            response.sendRedirect(request.getContextPath() + "/admin/");
        } else if ("Staff".equals(accountType)) {
            response.sendRedirect(request.getContextPath() + "/staff/");
        } else {
            response.sendRedirect(request.getContextPath() + "/customer/");
        }
        return;
    }
%>
<%@ include file = "template/topNav.jsp"%>

<section>
    <div class="container p-4 p-md-0 p-lg-0">
        <div class="row mt-lg-4 justify-content-center">
            <div class="card shadow-lg col-lg-5 col-md-8 col-12 p-3 custom-background custom-border">
                <h3 class="text-center login-text mt-3 mb-3">Welcome Back!</h3>
                <form action="<%= request.getContextPath()%>/login" method="POST" class="login-validation login-form mt-3" novalidate>
                <div class="card p-3 mt-4 custom-border">
                    <div class="card-body">
                            <c:if test="${not empty param.error}">
                                <div class="alert alert-danger mt-3">
                                    <c:out value="${param.error}" />
                                </div>
                            </c:if>
                        <c:if test="${not empty param.success}">
                            <div class="alert alert-success mt-3">
                                <c:out value="${param.success}" />
                            </div>
                        </c:if>
                            <div class="form-floating mb-3">
                                <input type="email" class="form-control" id="email" name="email" placeholder="name@example.com" required>
                                <label for="email" class="form-label">Email Address</label>
                                <div class="invalid-feedback" id="error-email"></div>
                            </div>
                            <div class="form-floating mb-3">
                                <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
                                <label for="password" class="form-label">Password</label>
                                <div class="" id="error-password"></div>
                            </div>
                            <div class="form-check mb-3">
                                <input class="form-check-input" id="inputRememberPassword" type="checkbox" name="remember_password" value="" />
                                <label class="form-check-label" for="inputRememberPassword">Remember Password</label>
                            </div>
                            <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                                <a class="small" href="#">Forgot Password?</a>
                                <button type="submit" name="submit" class="btn btn-primary" >Login</button>
                            </div>
                        <div class="text-center">
                            <div class="small"><a href="/register">Create New Account</a></div>
                        </div>
                    </div>
                </div>
                </form>
            </div>
        </div>
    </div>
</section>

<script>
    <%@ include file = "assets/js/login.js"%>
</script>
<%@ include file = "template/footer.jsp"%>
