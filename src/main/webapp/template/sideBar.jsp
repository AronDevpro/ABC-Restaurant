        <%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: Arosha
  Date: 8/13/2024
  Time: 8:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <%
            // Check if a session exists and if there is a "user" attribute in it
            session = request.getSession(false); // false means don't create a new session if one doesn't exist
            String firstName = "Guest"; // Default to "Guest" if the user is not logged in
            String lastName = "Guest"; // Default to "Guest" if the user is not logged in
            if (session != null && session.getAttribute("user") != null) {
                User user = (User) session.getAttribute("user");
                firstName = user.getFirstName();
                lastName = user.getLastName();
            }
        %>
<body class="nav-fixed">
<nav class="topnav navbar navbar-expand navbar-dark bg-dark">
    <!-- Navbar Brand-->
    <a class="navbar-brand ps-3" href="#">ABC Admin</a>
    <!-- Sidebar Toggle-->
    <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
    <!-- Navbar-->
    <ul class="navbar-nav ms-auto me-3 me-lg-4">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" href="#">Settings</a></li>
                <li><hr class="dropdown-divider" /></li>
                <li><a class="dropdown-item" href="#">Logout</a></li>
            </ul>
        </li>
    </ul>
</nav>
<div id="layoutSidenav">
    <div id="layoutSidenav_nav">
        <nav class="sidenav accordion sidenav-dark" id="sidenavAccordion">
            <div class="sidenav-menu">
                <div class="nav">
                    <div class="sidenav-menu-heading">Main Section</div>
                    <a class="nav-link" href="#">
                        <div class="nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                        Dashboard
                    </a>
                    <a class="nav-link" href="${pageContext.request.contextPath}/user">
                        <div class="nav-link-icon"><i class="fa-regular fa-user"></i></div>
                        Users
                    </a>
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/restaurant/">
                        <div class="nav-link-icon"><i class="fa-regular fa-user"></i></div>
                        Restaurants
                    </a>
                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseGallery" aria-expanded="false" aria-controls="collapseGallery">
                        <div class="nav-link-icon"><i class="fa-regular fa-image"></i></div>
                        Gallery
                        <div class="sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                    </a>
                    <div class="collapse" id="collapseGallery" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                        <nav class="sidenav-menu-nested nav">
                            <a class="nav-link" href="${pageContext.request.contextPath}/gallery">Gallery</a>
                            <a class="nav-link" href="#">New Gallery</a>
                        </nav>
                    </div>
                    <a class="nav-link collapsed " href="${pageContext.request.contextPath}/gallery" data-bs-toggle="collapse" data-bs-target="#collapseServices" aria-expanded="false" aria-controls="collapseServices">
                        <div class="nav-link-icon"><i class="fas fa-columns"></i></div>
                        Services
                        <div class="sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                    </a>
                    <div class="collapse" id="collapseServices" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                        <nav class="sidenav-menu-nested nav">
                            <a class="nav-link" href="#">Services</a>
                            <a class="nav-link" href="#">New Service</a>
                        </nav>
                    </div>
                    <a class="nav-link " href="#">
                        <div class="nav-link-icon"><i class="fa-solid fa-gear"></i></div>
                        Settings
                    </a>
                    <a class="nav-link " href="#">
                        <div class="nav-link-icon"><i class="fa-regular fa-envelope"></i></div>
                        Mailbox
                    </a>
                    <div class="sidenav-menu-heading">Blog Section</div>
                    <a class="nav-link collapsed " href="#" data-bs-toggle="collapse" data-bs-target="#collapseBlog" aria-expanded="false" aria-controls="collapseBlog">
                        <div class="nav-link-icon"><i class="fas fa-columns"></i></div>
                        Posts
                        <div class="sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                    </a>
                    <div class="collapse" id="collapseBlog" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                        <nav class="sidenav-menu-nested">
                            <a class="nav-link" href="#">Posts</a>
                            <a class="nav-link" href="#">New Post</a>
                        </nav>
                    </div>
                </div>
            </div>
            <div class="sidenav-footer">
                <div class="small">Logged in as:  <%= firstName %> <%= lastName %></div>
            </div>
        </nav>
    </div>
    <div id="layoutSidenav_content">
        <main>
            <!-- Adding contact part after here -->
