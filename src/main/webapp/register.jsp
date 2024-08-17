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
<div>
    <form action="<%= request.getContextPath()%>/user/register" method="POST">
        <div class="mb-3">
            <label class="form-label">First Name</label>
            <input type="text" class="form-control" placeholder="name@example.com" name="firstName">
        </div>
        <div class="mb-3">
            <label class="form-label">Last Name</label>
            <input type="text" class="form-control" placeholder="name@example.com" name="lastName">
        </div>
        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="text" class="form-control" placeholder="name@example.com" name="email">
        </div>
        <div class="mb-3">
            <label class="form-label">Password</label>
            <input type="text" class="form-control" placeholder="name@example.com" name="password">
        </div>
        <div class="mb-3">
            <label class="form-label">Address</label>
            <input type="text" class="form-control" placeholder="name@example.com" name="address">
        </div>
        <div class="mb-3">
            <label class="form-label">Phone Number</label>
            <input type="text" class="form-control" placeholder="name@example.com" name="phoneNumber">
        </div>
        <div class="mb-3">
            <label class="form-label">NIC</label>
            <input type="text" class="form-control" placeholder="name@example.com" name="nic">
        </div>
        <div class="mb-3">
            <label class="form-label">position</label>
            <input type="text" class="form-control" placeholder="name@example.com" name="position">
        </div>
        <select class="form-select" aria-label="Default select example" name="accountType">
            <option selected>Open this select menu</option>
            <option value="admin">Admin</option>
            <option value="customer">Customer</option>
            <option value="staff">Stuff</option>
        </select>
        <button type="submit" class="btn btn-primary">submit</button>

    </form>
</div>
<%@ include file = "template/footer.jsp"%>
</body>
</html>
