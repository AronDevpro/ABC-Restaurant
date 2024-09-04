<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../template/header.jsp" %>
<%@ include file="../template/sideBar.jsp" %>


<section>
  <div class="container-fluid">
    <nav aria-label="breadcrumb" class="m-3">
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
        <li class="breadcrumb-item active" aria-current="page">Settings</li>
      </ol>
    </nav>
  <div class="justify-content-center d-flex py-3">

      <form action="<%= request.getContextPath() %>/admin/settings/update" method="POST" enctype="multipart/form-data">
        <c:if test="${not empty successMessage}">
          <div class="alert alert-success">${successMessage}</div>
        </c:if>
        <div class="row">
          <div class="col">
            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="siteName" placeholder="Enter Site Name" name="siteName">
              <label for="siteName">Site Name</label>
            </div>
          </div>
          <div class="col">
            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="description" placeholder="Enter Description" name="description">
              <label for="description">Description</label>
            </div>
          </div>
        </div>
        <div class="form-floating mb-3">
          <input type="text" class="form-control" id="siteEmail" placeholder="Enter Email" name="siteEmail">
          <label for="siteEmail">Site Email</label>
        </div>
        <div class="row">
          <div class="col">
            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="siteStreetAddress" placeholder="Enter Street Address" name="siteStreetAddress">
              <label for="siteStreetAddress">Site Street Address</label>
            </div>
          </div>
          <div class="col">
            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="siteZip" placeholder="Enter Zip" name="siteZip">
              <label for="siteZip">Zip</label>
            </div>
          </div>
          <div class="col">
            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="siteCity" placeholder="Enter Zip" name="siteCity">
              <label for="siteCity">City</label>
            </div>
          </div>
        </div>

        <div class="mb-3">
          <label for="image" class="form-label">Logo</label>
          <input type="file" class="form-control" id="image" name="image" accept="image/*">
        </div>

        <div class="mb-3">
          <label for="currentImage" class="form-label">Current Logo</label>
          <br>
          <img id="currentImage" src="" alt="Current Image" style="max-width: 200px; height: auto;">
        </div>

        <h3>Email Server</h3>

        <div class="form-floating mb-3">
          <input type="text" class="form-control" id="serverEmail" placeholder="Enter Server Email" name="serverEmail">
          <label for="serverEmail">Email</label>
        </div>

        <div class="form-floating mb-3">
          <input type="password" class="form-control" id="serverPassword" placeholder="Enter Server Password" name="serverPassword">
          <label for="serverPassword">Password</label>
        </div>

        <button type="submit" class="btn btn-primary col-12">Update changes</button>

      </form>

  </div>
  </div>
</section>

<%@ include file="../template/sidebarFooter.jsp" %>
