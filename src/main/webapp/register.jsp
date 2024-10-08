<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Arosha
  Date: 8/10/2024
  Time: 6:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<%@ include file = "template/header.jsp"%>
<body>
<%@ include file="template/topNav.jsp"%>
<main>
    <div class="container p-4 p-md-0 p-lg-4">
        <div class="row mt-lg-4 justify-content-center">
            <div class="card shadow-lg col-lg-7 col-md-8 col-12 p-2 p-lg-3 p-md-3 custom-background custom-border">
                <h3 class="text-center login-text mt-3 mb-3">Create Account</h3>
                <div class="card px-lg-3 pt-lg-3 mt-4 custom-border">
                    <div class="card-body pb-0">
                        <c:if test="${not empty param.error}">
                            <div class="alert alert-danger mt-3">
                                <c:out value="${param.error}" />
                            </div>
                        </c:if>
                        <form class="registration-validation" action="<%= request.getContextPath()%>/register" method="POST" novalidate>
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <div class="form-floating mb-3 mb-md-0">
                                        <input class="form-control" id="firstName" type="text" name="firstName"
                                               placeholder="Enter your first name" minlength="3" required/>
                                        <label for="firstName">First Name</label>
                                        <div class="" id="fName-error"></div>
                                    </div>

                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating mb-3 mb-md-0">
                                        <input class="form-control" id="lastName" type="text" name="lastName"
                                               placeholder="Enter your last name" minlength="3" required/>
                                        <label for="lastName">Last Name</label>
                                        <div class="" id="lName-error"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-floating mb-3">
                                <input class="form-control" id="phoneNumber" name="phoneNumber" type="number"
                                       placeholder="Enter your Phone Number" pattern="[0-9]{10}" required/>
                                <label for="phoneNumber">Phone Number</label>
                                <div class="" id="phone-error"></div>
                            </div>

                            <div class="form-floating mb-3">
                                <input class="form-control" id="address" name="address" type="text"
                                       placeholder="Enter your address" required/>
                                <label for="address">Address</label>
                                <div class="" id="address-error"></div>
                            </div>

                            <div class="form-floating mb-3">
                                <input class="form-control" id="email" name="email" type="email"
                                       placeholder="name@example.com" required/>
                                <label for="email">Email address</label>
                                <div class="" id="email-error"></div>
                            </div>

                            <div class="form-floating mb-3 mb-md-0">
                                <input minlength="8" class="form-control" id="password" name="password" type="password"
                                       placeholder="Create a password" required/>
                                <label for="password">Password</label>
                                <div class="" id="password-error"></div>
                            </div>

                            <div class="mt-4 mb-0">
                                <div class="d-grid">
                                    <input type="submit" class="btn btn-primary btn-block" value="Create Account">
                                </div>
                            </div>
                        </form>
                        <div class="text-center py-4">
                            <div class="small">
                                <a href="/login">Have an account? Go to login</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<script>
    <%@ include file = "assets/js/register.js"%>
</script>
<%@ include file = "template/footer.jsp"%>
