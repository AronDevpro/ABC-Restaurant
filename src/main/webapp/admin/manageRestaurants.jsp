<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../template/header.jsp" %>
<%@ include file="../template/sideBar.jsp" %>
<section>
  <div class="container-fluid">
    <div class="row">
      <div class="col-12">
        <div class="card m-3">
          <div class="card-header">
            <div class="row align-items-center">
              <div class="col-12 col-lg-3">
                <h4 class="fw-bold">Restaurants</h4>
              </div>
              <div class="col-9 col-md-10 col-lg-7 ">
                <form method="GET" action="" id="searchForm">
                  <div class="input-group">
                    <input class="form-control" type="text" name="search" placeholder="Search"
                           aria-label="Search" value="${param.search}" oninput="submitSearchForm()"/>
                  </div>
                </form>
              </div>
              <div class="col-3 col-md-2 col-lg-2 text-end">
                <button class="btn btn-primary"
                        data-bs-toggle="modal"
                        data-bs-target="#addUserModel">
                  Add Restaurant
                </button>
              </div>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-hover table-bordered">
                  <thead class="table-light">
                  <tr>
                    <th>#id</th>
                    <th>Name</th>
                    <th>Address</th>
                    <th>Phone Number</th>
                    <th>Status</th>
                    <th>Options</th>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="restaurant" items="${restaurantList}">
                    <tr>
                      <td>${restaurant.id}</td>
                      <td>${restaurant.name}</td>
                      <td>${restaurant.address}</td>
                      <td>${restaurant.phoneNumber}</td>
                      <td>
                    <span class="badge ${restaurant.status == 'active' ? 'text-bg-primary' : 'text-bg-danger'}">
                        ${restaurant.status}
                    </span>
                      </td>
                      <td>
                        <button class="btn btn-primary view-user-btn"
                                data-id="${restaurant.id}"
                                data-bs-toggle="modal"
                                data-bs-target="#updateModel">
                          Edit
                        </button>  |
                        <a href="/admin/restaurant/delete?id=${restaurant.id}" class="btn btn-danger">Delete</a>
                      </td>
                    </tr>
                  </c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
            <div class="card-footer">
              <nav aria-label="Page navigation">
                <ul class="pagination">
                  <c:if test="${currentPage > 1}">
                    <li class="page-item">
                      <a class="page-link" href="?page=${currentPage - 1}&size=${size}">Previous</a>
                    </li>
                  </c:if>
                  <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                      <a class="page-link" href="?page=${i}&size=${size}">${i}</a>
                    </li>
                  </c:forEach>
                  <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                      <a class="page-link" href="?page=${currentPage + 1}&size=${size}">Next</a>
                    </li>
                  </c:if>
                </ul>
              </nav>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
<!-- Modal -->
<div class="modal fade" id="updateModel" tabindex="-1" aria-labelledby="updateModelLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form action="${pageContext.request.contextPath}/admin/restaurant/update" method="POST">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">Restaurant Details</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="id" value="">
          <div class="form-floating mb-3">
            <input type="text" class="form-control" placeholder="Enter Chain Name" name="name">
            <label for="name">Chain Name</label>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" placeholder="Enter Description" name="description">
            <label for="description">Description</label>
          </div>
          <div class="form-floating mb-3">
            <input type="time" class="form-control" placeholder="Enter Open Time" name="openTime">
            <label for="openTime">Open Time</label>
          </div>
          <div class="form-floating mb-3">
            <input type="time" class="form-control" placeholder="Enter Close Time" name="closeTime">
            <label for="closeTime">Close Time</label>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" placeholder="Enter Address" name="address">
            <label for="address">Address</label>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" placeholder="Enter Phone Number" name="phoneNumber">
            <label for="phoneNumber">Phone Number</label>
          </div>
        </div>
        <div class="modal-footer">
          <button type="submit" class="btn btn-primary">Update changes</button>
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="addUserModel" tabindex="-1" aria-labelledby="addUserModelLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form action="<%= request.getContextPath()%>/admin/restaurant/create" method="POST">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="addUserModelLabel">Create User</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="form-floating mb-3">
            <input type="text" class="form-control" id="name" placeholder="Enter Chain Name" name="name">
            <label for="name">Chain Name</label>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" id="description" placeholder="Enter Description" name="description">
            <label for="description">Description</label>
          </div>
          <div class="form-floating mb-3">
            <input type="time" class="form-control" id="openTime" placeholder="Enter Open Time" name="openTime">
            <label for="openTime">Email</label>
          </div>
          <div class="form-floating mb-3">
            <input type="time" class="form-control" id="closeTime" placeholder="Enter Close Time" name="closeTime">
            <label for="closeTime">Password</label>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" id="address" placeholder="Enter Address" name="address">
            <label for="address">Address</label>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" id="phoneNumber" placeholder="Enter Phone Number" name="phoneNumber">
            <label for="phoneNumber">Phone Number</label>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="submit" class="btn btn-primary">Save changes</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script>
  function submitSearchForm() {
    var searchInput = document.querySelector('input[name="search"]').value;
    if (searchInput.length >= 3) {
      document.getElementById("searchForm").submit();
    }
  }

  document.addEventListener('DOMContentLoaded', function() {

    //   update model
    const updateModel = document.getElementById('updateModel');
    const updateModelInstance = bootstrap.Modal.getOrCreateInstance(updateModel);

    document.querySelectorAll('.view-user-btn').forEach(function (button) {
      button.addEventListener('click', function () {
        const id = this.getAttribute('data-id');

        fetch('<%= request.getContextPath() %>/admin/restaurant/view?id=' + id)
                .then(response => response.json())
                .then(data => {
                  updateModel.querySelector('input[name="id"]').value = data.id;
                  updateModel.querySelector('input[name="name"]').value = data.name;
                  updateModel.querySelector('input[name="description"]').value = data.description;
                  updateModel.querySelector('input[name="openTime"]').value = data.openTime;
                  updateModel.querySelector('input[name="closeTime"]').value = data.closeTime;
                  updateModel.querySelector('input[name="address"]').value = data.address || '';
                  updateModel.querySelector('input[name="phoneNumber"]').value = data.phoneNumber || '';

                  updateModelInstance.show();
                })
                .catch(error => console.error('Error fetching restaurant details:', error));
      });
    });

  });


</script>
<%@ include file="../template/sidebarFooter.jsp" %>
