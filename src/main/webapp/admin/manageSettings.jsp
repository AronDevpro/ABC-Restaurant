<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../template/header.jsp" %>
<%@ include file="../template/sideBar.jsp" %>

<section>
  <div class="container-fluid justify-content-center d-flex py-3">

      <form action="<%= request.getContextPath() %>/admin/settings/update" method="POST" enctype="multipart/form-data">
        <div class="form-floating mb-3">
          <input type="text" class="form-control" id="siteName" placeholder="Enter Site Name" name="siteName">
          <label for="siteName">Site Name</label>
        </div>

        <div class="form-floating mb-3">
          <input type="text" class="form-control" id="description" placeholder="Enter Description" name="description">
          <label for="description">Description</label>
        </div>

        <div class="mb-3">
          <label for="image" class="form-label">Logo</label>
          <input type="file" class="form-control" id="image" name="image" accept="image/*">
        </div>

        <div class="mb-3">
          <label for="currentImage" class="form-label">Current Logo</label>
          <br>
          <img id="currentImage" src="" alt="Current Image" style="max-width: 100%; height: auto;">
        </div>

        <h3>Email Server</h3>

        <div class="form-floating mb-3">
          <input type="text" class="form-control" id="email" placeholder="Enter Email" name="email">
          <label for="email">Email</label>
        </div>

        <div class="form-floating mb-3">
          <input type="password" class="form-control" id="password" placeholder="Enter Password" name="password">
          <label for="password">Password</label>
        </div>

        <button type="submit" class="btn btn-primary col-12">Save changes</button>

      </form>

  </div>
</section>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script>
  document.addEventListener('DOMContentLoaded', function() {

    // Update model
    const updateModel = document.getElementById('updateModel');
    const updateModelInstance = bootstrap.Modal.getOrCreateInstance(updateModel);

    document.querySelectorAll('.view-user-btn').forEach(function (button) {
      button.addEventListener('click', function () {
        const userId = this.getAttribute('data-user-id');

        fetch('<%= request.getContextPath() %>/admin/users/view?id=' + userId)
                .then(response => response.json())
                .then(data => {
                  updateModel.querySelector('input[name="id"]').value = data.id;
                  updateModel.querySelector('input[name="firstName"]').value = data.firstName;
                  updateModel.querySelector('input[name="lastName"]').value = data.lastName;
                  updateModel.querySelector('input[name="email"]').value = data.email;
                  updateModel.querySelector('input[name="phoneNumber"]').value = data.phoneNumber || '';
                  updateModel.querySelector('input[name="address"]').value = data.address || '';

                  updateModelInstance.show();
                })
                .catch(error => console.error('Error fetching user details:', error));
      });
    });

  });
</script>

<%@ include file="../template/sidebarFooter.jsp" %>
