<%@ page import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // user details from session
    User user = (User) session.getAttribute("user");
    String firstName = (user != null) ? user.getFirstName() : "";
    String lastName = (user != null) ? user.getLastName() : "";
    String email = (user != null) ? user.getEmail() : "";
    Integer customerId = (user != null) ? user.getId() : null;

    //parameters from session
    String restaurantId = request.getParameter("restaurantId");
    String date = request.getParameter("date");
    String time = request.getParameter("time");
%>
<!doctype html>
<html>
<%@ include file="template/header.jsp"%>
<body>
<%@ include file="template/topNav.jsp"%>
<div class="container">
    <div class="row">
        <div class="col-12 col-md-8 mb-3 mb-md-0">
            <div class="container py-3" style="border:1px solid #e3e0e0; border-radius: 5px;">
                <form method="POST" action="confirm">
                    <input type="text" hidden name="date" value="<%= date %>">
                    <input type="text" hidden name="time" value="<%= time %>">
                    <input type="number" hidden name="restaurantId" value="<%= restaurantId %>">
                    <input type="number" hidden name="customerId" value="<%= customerId %>">
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" placeholder="Enter Your First Name" name="name" id="name" value="<%= firstName %> <%= lastName %>">
                        <label for="name">Full Name</label>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="form-floating mb-3">
                                <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="Enter Your Phone Number">
                                <label for="phoneNumber">Phone Number</label>
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-floating mb-3">
                                <select class="form-select" id="noOfPeople" name="noOfPeople">
                                    <option value="1">One</option>
                                    <option value="2" selected>Two</option>
                                    <option value="3">Three</option>
                                    <option value="4">Four</option>
                                    <option value="5">Five</option>
                                    <option value="6">Six</option>
                                    <option value="7">Seven</option>
                                    <option value="8">Eight</option>
                                    <option value="9">Nine</option>
                                    <option value="10">Ten</option>
                                </select>
                                <label for="noOfPeople">Number Of People</label>
                            </div>
                        </div>
                    </div>

                    <button class="btn btn-primary col-12" type="submit">Complete Reservation</button>
                </form>
            </div>
        </div>

        <div class="col-12 col-md-4">
            <div class="container pt-3" style="border:1px solid #e3e0e0; border-radius: 5px;">
                <h5>Reservation Summary</h5>
                <p id="selectedRestaurant">Selected Restaurant: Loading...</p>
                <p>Selected Date: <%= date %></p>
                <p>Selected Time: <%= time %></p>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const restaurantId = '<%= restaurantId %>';
        const selectedRestaurantElement = document.getElementById('selectedRestaurant');

        if (restaurantId) {
            fetch('<%= request.getContextPath() %>/customer/get-restaurant?id=' + restaurantId)
                .then(response => response.json())
                .then(data => {
                    selectedRestaurantElement.textContent = 'Selected Restaurant: ' + data.name;
                })
                .catch(error => {
                    console.error('Error fetching restaurant details:', error);
                    selectedRestaurantElement.textContent = 'Selected Restaurant: Error';
                });
        } else {
            selectedRestaurantElement.textContent = 'Selected Restaurant: Not available';
        }
    });
</script>

<%@ include file="template/footer.jsp"%>
