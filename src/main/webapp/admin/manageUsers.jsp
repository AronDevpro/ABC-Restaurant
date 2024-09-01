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
                <h4 class="fw-bold">Users</h4>
              </div>
              <div class="col-6 col-md-7 col-lg-5 ">
                <form method="GET" action="" id="searchForm">
                  <div class="input-group">
                    <input class="form-control" type="text" name="search" placeholder="Search"
                           aria-label="Search" value="${param.search}" oninput="submitSearchForm()"/>
                  </div>
                </form>
              </div>
              <div class="col-4 col-md-3 col-lg-2">
                <form method="GET" action="" id="filterForm">
                  <select name="filterUserType" class="form-select" onchange="submitSelectForm()">
                    <option value="">All</option>
                    <option value="customer" ${param.filterUserType == 'customer' ? 'selected' : ''}>Customers</option>
                    <option value="admin" ${param.filterUserType == 'admin' ? 'selected' : ''}>Admins</option>
                    <option value="staff" ${param.filterUserType == 'staff' ? 'selected' : ''}>Staff</option>
                  </select>
                </form>
              </div>
              <div class="col-2 col-md-2 col-lg-2 text-end">
                <button class="btn btn-primary"
                        data-bs-toggle="modal"
                        data-bs-target="#addUserModel">
                  Add user
                </button>
              </div>
            </div>
          <div class="card-body">
            <div class="table-responsive">
              <table class="table table-hover table-bordered">
                <thead class="table-light">
                <tr>
                  <th>#id</th>
                  <th>Email</th>
                  <th>Name</th>
                  <th>Phone Number</th>
                  <th>User Type</th>
                  <th>Status</th>
                  <th>Options</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${userList}">
                  <tr>
                    <td>${user.id}</td>
                    <td>${user.email}</td>
                    <td>${user.firstName} ${user.lastName}</td>
                    <td>${user.phoneNumber}</td>
                    <td>${user.accountType}</td>
                    <td>
                    <span class="badge ${user.status == 'active' ? 'text-bg-primary' : 'text-bg-danger'}">
                        ${user.status}
                    </span>
                    </td>
                    <td>
                      <button class="btn btn-primary view-user-btn"
                               data-user-id="${user.id}"
                               data-bs-toggle="modal"
                               data-bs-target="#updateModel">
                      Edit
                      </button>  |
                      <a href="/admin/users/delete?id=${user.id}" class="btn btn-danger">Delete</a>
                    </td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
          <div class="card-footer">
            <nav aria-label="Page navigation">
              <ul class="pagination justify-content-end">
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
      <form action="${pageContext.request.contextPath}/admin/users/update" method="POST">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">User Details</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
          <input type="hidden" name="id">
          <div class="form-floating mb-3">
            <input type="text" class="form-control" name="firstName">
            <label for="firstName">First Name</label>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" name="lastName">
            <label for="lastName">Last Name</label>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" name="email">
            <label for="email">Email</label>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" name="phoneNumber">
            <label for="phoneNumber">Phone Number</label>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" name="address">
            <label for="address">Address</label>
          </div>
          <div class="form-floating mb-3" id="updateNicField" style="display: none">
            <input type="text" class="form-control" name="nic">
            <label for="nic">NIC</label>
          </div>
          <div class="form-floating mb-3" id="updatePositionField" style="display: none">
            <input type="text" class="form-control" name="position">
            <label for="position">Position</label>
          </div>
          <div class="form-floating mb-3">
            <input readonly type="text" class="form-control" name="accountType" id="updateAccountType">
            <label for="accountType">Account Type</label>
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
      <form action="<%= request.getContextPath()%>/admin/users/create" method="POST">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="addUserModelLabel">Create User</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div class="form-floating mb-3">
          <input type="text" class="form-control" id="firstName" placeholder="Enter First Name" name="firstName">
          <label for="firstName">First Name</label>
        </div>
        <div class="form-floating mb-3">
          <input type="text" class="form-control" id="lastName" placeholder="Enter Last Name" name="lastName">
          <label for="lastName">Last Name</label>
        </div>
        <div class="form-floating mb-3">
          <input type="email" class="form-control" id="email" placeholder="Enter Email" name="email">
          <label for="email">Email</label>
        </div>
        <div class="form-floating mb-3">
          <input type="password" class="form-control" id="password" placeholder="Enter Password" name="password">
          <label for="password">Password</label>
        </div>
        <div class="form-floating mb-3">
          <input type="text" class="form-control" id="address" placeholder="Enter Address" name="address">
          <label for="address">Address</label>
        </div>
        <div class="form-floating mb-3">
          <input type="text" class="form-control" id="phoneNumber" placeholder="Enter Phone Number" name="phoneNumber">
          <label for="phoneNumber">Phone Number</label>
        </div>
        <div class="form-floating mb-3" style="display: none" id="nicField">
          <input type="text" class="form-control" id="nic" placeholder="Enter NIC Number" name="nic">
          <label for="nic">NIC NO</label>
        </div>
        <div class="form-floating mb-3" style="display: none" id="positionField">
          <input type="text" class="form-control" id="position" placeholder="Enter Position" name="position">
          <label for="position">Position</label>
        </div>
        <div class="form-floating">
          <select class="form-select" id="accountType" aria-label="Floating label select example" name="accountType">
            <option selected>Select The Account Type</option>
            <option value="Admin">Admin</option>
            <option value="Staff">Staff</option>
            <option value="Customer">Customer</option>
          </select>
          <label for="accountType">Account Type</label>
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

  function submitSelectForm() {
    document.getElementById('filterForm').submit();
  }

  document.addEventListener('DOMContentLoaded', function() {
    //display addition inputs based on input value on update modal
    function toggleAdditionalUpdateFields() {
      const accountType = document.getElementById('updateAccountType').value;
      const positionField = document.getElementById('updatePositionField');
      const nicField = document.getElementById('updateNicField');

      if (accountType === 'Admin') {
        nicField.style.display = 'block';
      } else if(accountType === 'Staff') {
        nicField.style.display = 'block';
        positionField.style.display = 'block';
      } else {
        positionField.style.display = 'none';
        nicField.style.display = 'none';
      }
    }
    document.getElementById('updateAccountType').addEventListener('change', toggleAdditionalUpdateFields);

    //display addition inputs based on select add model
    function toggleAdditionalFields() {
      const accountType = document.getElementById('accountType').value;
      const positionField = document.getElementById('positionField');
      const nicField = document.getElementById('nicField');

      if (accountType === 'Admin') {
        nicField.style.display = 'block';
      } else if(accountType === 'Staff') {
        nicField.style.display = 'block';
        positionField.style.display = 'block';
      } else {
        positionField.style.display = 'none';
        nicField.style.display = 'none';
      }
    }
    document.getElementById('accountType').addEventListener('change', toggleAdditionalFields);

    toggleAdditionalFields();

  //   update model
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
                  if(data.accountType === 'Staff'){
                    updateModel.querySelector('input[name="nic"]').value = data.nic || '';
                    updateModel.querySelector('input[name="position"]').value = data.position || '';
                  } else if(data.accountType === 'Admin'){
                    updateModel.querySelector('input[name="nic"]').value = data.nic || '';
                  }
                  updateModel.querySelector('input[name="accountType"]').value = data.accountType;


                  updateModelInstance.show();
                  toggleAdditionalUpdateFields();
                })
                .catch(error => console.error('Error fetching user details:', error));
      });
    });

  });


</script>
<%@ include file="../template/sidebarFooter.jsp" %>
