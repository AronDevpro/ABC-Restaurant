<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Arosha
  Date: 8/13/2024
  Time: 7:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!doctype html>
<html>
<%@ include file = "template/header.jsp"%>
<body>
<%@ include file = "template/topNav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <div class="col-12 col-md-3 order-1 order-md-0">
            <div class="container" style="background-color:black; border-radius: 5px">
            <p class="p-2" style="color:white">CATEGORY</p>
            </div>
            <div class="container pt-3" style="border:1px solid #e3e0e0; border-radius: 5px;">
                <form method="GET" action="" id="filterForm">
                    <input type="hidden" name="filterCategory" id="filterCategoryInput" value="${param.filterCategory || ''}">
                    <div onclick="selectCategory('')" style="cursor: pointer;">
                        <p class="${param.filterCategory == '' ? 'text-primary' : ''}">All</p>
                    </div>
                    <div onclick="selectCategory('food & beverage')" style="cursor: pointer;">
                        <p class="${param.filterCategory == 'food & beverage' ? 'text-primary' : ''}">Food & Beverage</p>
                    </div>
                    <div onclick="selectCategory('rice & kottu')" style="cursor: pointer;">
                        <p class="${param.filterCategory == 'rice & kottu' ? 'text-primary' : ''}">Rice & Kottu</p>
                    </div>
                    <div onclick="selectCategory('bakery')" style="cursor: pointer;">
                        <p class="${param.filterCategory == 'bakery' ? 'text-primary' : ''}">Bakery Items</p>
                    </div>
                    <div onclick="selectCategory('dessert')" style="cursor: pointer;">
                        <p class="${param.filterCategory == 'dessert' ? 'text-primary' : ''}">Dessert</p>
                    </div>
                    <div onclick="selectCategory('sushi')" style="cursor: pointer;">
                        <p class="${param.filterCategory == 'sushi' ? 'text-primary' : ''}">Sushi Items</p>
                    </div>
                </form>
            </div>
        </div>
        <div class="col-12 col-md-9 order-0">
            <div class="mb-3">
                <form method="GET" action="" id="searchForm">
                    <div class="input-group">
                        <input type="text" class="form-control" name="search" placeholder="Search by Product name or category" style="border: none; box-shadow: none" value="${param.search}" oninput="submitSearchForm()">
                    </div>
                </form>
            </div>
            <div class="row">
                <c:forEach var="product" items="${productList}">
                <div class="col-12 col-md-6 col-lg-4 mb-3 text-center p-3" >
                    <div class="container pb-3 px-0" style="border: 1px solid #e3e0e0; border-radius: 5px">
                        <img src="${pageContext.request.contextPath}/assets/${product.imagePath.replace('\\', '/')}" alt="${product.name}" style="width: 100%; height: 250px; object-fit: cover; display: block;">
                        <h4 class="mt-3 mb-1">${product.name}</h4>
                        <p class="mb-3">Rs. ${product.price}</p>
                        <div class="row">
                            <div class="col align-content-center">
                                <i class="fa-solid fa-minus me-3" onclick="decrementQuantity('${product.id}')"></i>
                                <span id="${product.id}">0</span>
                                <i class="fa-solid fa-plus ms-3" onclick="incrementQuantity('${product.id}')"></i>
                            </div>
                            <div class="col">
                                <button class="btn btn-primary" onclick="addToCart('${product.id}','${product.name}', '${product.price}' )">Add Cart</button>
                            </div>
                        </div>

                    </div>
                </div>
                </c:forEach>
            </div>

            <div class="justify-content-center d-flex my-3">
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



<%@ include file = "template/footer.jsp"%>
