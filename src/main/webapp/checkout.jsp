<%@ page import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // Retrieve the session and the user from the session
    User user = (User) session.getAttribute("user");
    String firstName = (user != null) ? user.getFirstName() : "";
    String lastName = (user != null) ? user.getLastName() : "";
    String email = (user != null) ? user.getEmail() : "";
    Integer customerId = (user != null) ? user.getId() : null;
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
                <form method="POST" action="${pageContext.request.contextPath}/checkout/create">
                    <input type="hidden" name="total" value="${cartTotal}">
                    <input type="hidden" name="customerId" value="<%= customerId %>">
                    <div class="row">
                        <div class="col">
                            <div class="form-floating mb-3">
                                <input type="text" class="form-control" placeholder="Enter Your First Name" name="firstName" id="firstName" value="<%= firstName %>">
                                <label for="firstName">First Name</label>
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-floating mb-3">
                                <input type="text" class="form-control" placeholder="Enter Your Last Name" name="lastName" id="lastName" value="<%= lastName %>">
                                <label for="lastName">Last Name</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="email" class="form-control" id="email" name="email" placeholder="Enter Your Email" value="<%= email %>">
                        <label for="email">Email address</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="Enter Your Phone Number">
                        <label for="phoneNumber">Phone Number</label>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="form-floating mb-3">
                                <input type="date" class="form-control" id="deliverDate" name="deliverDate">
                                <label for="deliverDate">Deliver Date</label>
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-floating mb-3">
                                <input type="time" class="form-control" id="deliverTime" name="deliverTime">
                                <label for="deliverTime">Deliver Time</label>
                            </div>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="deliveryMethod" id="dine-in" value="Dine In" onclick="toggleDeliveryFields('takeAway')">
                                <label class="form-check-label" for="dine-in">
                                    Dine-in
                                </label>
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="deliveryMethod" id="takeAway" value="Take Away" onclick="toggleDeliveryFields('takeAway')">
                                <label class="form-check-label" for="takeAway">
                                    Take Away
                                </label>
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="deliveryMethod" id="deliver" value="Deliver" onclick="toggleDeliveryFields('deliver')">
                                <label class="form-check-label" for="deliver">
                                    Deliver
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="form-floating mb-3" id="takeAwayContainer" style="display:none;">
                        <select class="form-select" id="restaurantSelect" name="restaurantSelect">
                            <option value="">Select Restaurant</option>
                            <c:forEach var="restaurant" items="${restaurantList}">
                                <option value="${restaurant.name}">${restaurant.name}</option>
                            </c:forEach>
                        </select>
                        <label for="restaurantSelect">Select Takeaway Restaurant</label>
                    </div>

                    <div id="addressContainer" style="display:none;">
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" placeholder="Enter Your Street Address" name="streetAddress" id="streetAddress">
                            <label for="streetAddress">Street Address</label>
                        </div>
                        <div class="row">
                            <div class="col">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" placeholder="Enter Your Zip" name="zip" id="zip">
                                    <label for="zip">Zip</label>
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" placeholder="Enter Your City" name="city" id="city">
                                    <label for="city">City</label>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="paymentMethod" id="cash" value="cash">
                                <label class="form-check-label" for="cash">
                                    By Cash
                                </label>
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="paymentMethod" id="card" value="card">
                                <label class="form-check-label" for="card">
                                    By Card
                                </label>
                            </div>
                        </div>
                    </div>

                    <div id="cardPaymentContainer" style="display:none;">
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" placeholder="Enter Your Card Number" name="cardNo" id="cardNo">
                            <label for="cardNo">Card Number</label>
                        </div>
                        <div class="row mb-3">
                            <div class="col">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" placeholder="Enter Expire Date" name="exp" id="exp">
                                    <label for="exp">Expire Date</label>
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" placeholder="Enter CVV" name="cvv" id="cvv">
                                    <label for="cvv">CVV</label>
                                </div>
                            </div>
                        </div>
                    </div>

                    <button class="btn btn-primary col-12" type="submit">Complete Checkout</button>
                </form>
            </div>
        </div>

        <div class="col-12 col-md-4">
            <div class="container pt-3" style="border:1px solid #e3e0e0; border-radius: 5px;">
                <h5>Cart Summary</h5>
                <c:forEach var="entry" items="${cartItems}">
                    <p class="d-flex justify-content-between align-items-center py-0">
                        <span><span class="badge bg-primary rounded-pill">${entry.value.quantity}</span>
                            ${entry.value.name}
                        </span>
                        <span>Rs.${entry.value.price}</span>
                    </p>
                </c:forEach>
                <c:if test="${empty cartItems}">
                    <p>Your cart is empty.</p>
                </c:if>
                <h6 id="cartTotal" class="mt-3">Total: Rs.${cartTotal}</h6>
            </div>
        </div>
    </div>
</div>

<%@ include file="template/footer.jsp"%>
