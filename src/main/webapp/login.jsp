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
    // Check if a session exists and if there is a "user" attribute in it
    session = request.getSession(false); // false means don't create a new session if one doesn't exist
    if (session != null && session.getAttribute("user") != null) {
        User user = (User) session.getAttribute("user");
        String accountType = user.getAccountType();

        // Redirect based on the account type
        if ("Admin".equals(accountType)) {
            response.sendRedirect(request.getContextPath() + "/dashboard3.jsp");
        } else if ("Customer".equals(accountType)) {
            response.sendRedirect(request.getContextPath() + "/dashboard1.jsp");
        } else {
            // Redirect to a default page or handle unknown account types
            response.sendRedirect(request.getContextPath() + "/dashboard2.jsp");
        }
        return; // Prevent further execution of the page
    }
%>
<%@ include file = "template/topNav.jsp"%>
<form action="<%= request.getContextPath()%>/user/login" method="POST">
    <div class="mb-3">
        <label for="email" class="form-label">Email address</label>
        <input type="text" class="form-control" id="email" name="email">
        <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
    </div>
    <div class="mb-3">
        <label for="password" class="form-label">Password</label>
        <input type="password" class="form-control" id="password" name="password">
    </div>
    <div class="mb-3 form-check">
        <input type="checkbox" class="form-check-input" id="exampleCheck1">
        <label class="form-check-label" for="exampleCheck1">Check me out</label>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
</body>
</html>
