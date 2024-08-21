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
                <h4 class="fw-bold">Facilities</h4>
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
                    <option value="Event" ${param.filterUserType == 'customer' ? 'selected' : ''}>Customers</option>
                    <option value="admin" ${param.filterUserType == 'admin' ? 'selected' : ''}>Admins</option>
                    <option value="staff" ${param.filterUserType == 'staff' ? 'selected' : ''}>Staff</option>
                  </select>
                </form>
              </div>
              <div class="col-2 col-md-2 col-lg-2 text-end">
                <button class="btn btn-primary"
                        data-bs-toggle="modal"
                        data-bs-target="#addGalleryModel">
                  Add Gallery
                </button>
              </div>
            </div>
          </div>
          <div class="card-body">
            <div class="table-responsive">
              <table class="table table-hover table-bordered">
                <thead class="table-light">
                <tr>
                  <th>#id</th>
                  <th>Title</th>
                  <th>Description</th>
                  <th>Status</th>
                  <th>Options</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="gallery" items="${galleryList}">
                  <tr>
                    <td>${gallery.id}</td>
                    <td>${gallery.title}</td>
                    <td>${gallery.description}</td>
                    <td>
                    <span class="badge ${gallery.status == 'active' ? 'text-bg-primary' : 'text-bg-danger'}">
                        ${gallery.status}
                    </span>
                    </td>
                    <td>
                      <button class="btn btn-primary view-gallery-btn"
                              data-id="${gallery.id}"
                              data-bs-toggle="modal"
                              data-bs-target="#updateModel">
                        Edit
                      </button> |
                      <a href="${pageContext.request.contextPath}/admin/galleries/delete?id=${gallery.id}" class="btn btn-danger">Delete</a>
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
</section>

<!-- Add Gallery Modal -->
<div class="modal fade" id="addGalleryModel" tabindex="-1" aria-labelledby="addGalleryModelLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form action="<%= request.getContextPath() %>/admin/galleries/create" method="POST" enctype="multipart/form-data">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="addGalleryModelLabel">Add Gallery</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="id">
          <div class="form-floating mb-3">
            <input type="text" class="form-control" id="title" placeholder="Enter Gallery title" name="title" required>
            <label for="title">Gallery Title</label>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" id="description" placeholder="Enter Description" name="description" required>
            <label for="description">Description</label>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" id="category" placeholder="Enter Category" name="category" required>
            <label for="category">Category</label>
          </div>
          <div class="mb-3">
            <label for="image" class="form-label">Upload Image</label>
            <input type="file" class="form-control" id="image" name="image" accept="image/*" required>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" id="status" placeholder="Enter Status" name="status" required>
            <label for="status">Status</label>
          </div>
        </div>
        <div class="modal-footer">
          <button type="submit" class="btn btn-primary">Save Gallery</button>
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="updateModel" tabindex="-1" aria-labelledby="updateModelLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form action="${pageContext.request.contextPath}/admin/galleries/update" method="POST" enctype="multipart/form-data">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="updateModelLabel">Gallery Details</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="id">
          <div class="form-floating mb-3">
            <input type="text" class="form-control" placeholder="Enter Gallery title" name="title" required>
            <label for="title">Gallery Name</label>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" placeholder="Enter Description" name="description" required>
            <label for="description">Description</label>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" placeholder="Enter Category" name="category" required>
            <label for="category">Category</label>
          </div>
          <div class="mb-3">
            <label for="image" class="form-label">Upload Image (optional)</label>
            <input type="file" class="form-control" name="image" accept="image/*">
          </div>
          <div class="mb-3">
            <label for="currentImage" class="form-label">Current Image</label>
            <br>
            <img id="currentImage" src="" alt="Current Image" style="max-width: 100%; height: auto;">
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" placeholder="Enter Status" name="status" required>
            <label for="status">Status</label>
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
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

    //   update model
    const updateModel = document.getElementById('updateModel');
    const updateGalleryModelInstance = bootstrap.Modal.getOrCreateInstance(updateModel);

    document.querySelectorAll('.view-gallery-btn').forEach(function (button) {
      button.addEventListener('click', function () {
        const id = this.getAttribute('data-id');

        fetch('<%= request.getContextPath() %>/admin/galleries/view?id=' + id)
                .then(response => response.json())
                .then(data => {
                  updateModel.querySelector('input[name="id"]').value = data.id;
                  updateModel.querySelector('input[name="title"]').value = data.title;
                  updateModel.querySelector('input[name="description"]').value = data.description;
                  updateModel.querySelector('input[name="category"]').value = data.category;
                  updateModel.querySelector('input[name="status"]').value = data.status;

                  // Set current image source if available
                  const imageUrl = data.imagePath ? '<%= request.getContextPath() %>/assets' + data.imagePath.replace(/\\/g, '/') : ''; // Convert backslashes to forward slashes
                  const currentImage = updateModel.querySelector('#currentImage');
                  if (imageUrl) {
                    currentImage.src = imageUrl;
                    currentImage.style.display = 'block'; // Show image
                  } else {
                    currentImage.style.display = 'none'; // Hide image if path is empty
                  }

                  updateGalleryModelInstance.show();
                })
                .catch(error => console.error('Error fetching gallery details:', error));
      });
    });
  });
</script>

<%@ include file="../template/sidebarFooter.jsp" %>
