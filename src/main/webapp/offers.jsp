<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<%@ include file="template/header.jsp"%>
<body>
<%@ include file="template/topNav.jsp"%>
<div class="container">
  <h1 class="title title-second"><span class="title-first">OUR</span> OFFERS</h1>
  <h3 class="sub-title">Delicious Discounts Await – Don't Miss Out!</h3>
  <div class="col-12">
    <div class="mb-3">
      <form method="GET" action="" id="searchForm">
        <div class="input-group">
          <input type="text" class="form-control" name="search" placeholder="Search by Offer Name" style="border: none; box-shadow: none" value="${search}" oninput="submitSearchForm()">
        </div>
      </form>
    </div>
    <div class="row">
      <c:forEach var="offer" items="${offerList}">
        <div class="col-12 col-md-6 col-lg-4 mb-3 p-3">
          <div class="container pb-3 px-0" style="border-radius: 5px; background-color: #ded7d742">
            <img src="${pageContext.request.contextPath}/assets/${offer.imagePath.replace('\\', '/').replace(' ', '%20')}" alt="${offer.offerName}" style="width: 100%; height: 250px; object-fit: cover; display: block; border-radius: 5px;">
            <div class="px-3">
            <h4 class="my-3 mb-1 sub-title">${offer.offerName}</h4>
            <div class="mt-3 d-flex justify-content-between">
                <p class="mb-3" style="font-size: 18px">Promo Code: <span class="fw-bold">${offer.promoCode}</span></p>
                <p class="mb-3" style="font-size: 18px">Discount: <span class="fw-bold">${offer.discountPercentage}%</span></p>
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
              <a class="page-link" href="?page=${currentPage - 1}&size=${size}&search=${search}">Previous</a>
            </li>
          </c:if>
          <c:forEach begin="1" end="${totalPages}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
              <a class="page-link" href="?page=${i}&size=${size}&search=${search}">${i}</a>
            </li>
          </c:forEach>
          <c:if test="${currentPage < totalPages}">
            <li class="page-item">
              <a class="page-link" href="?page=${currentPage + 1}&size=${size}&search=${search}">Next</a>
            </li>
          </c:if>
        </ul>
      </nav>
    </div>
  </div>
</div>
<%@ include file = "template/footer.jsp"%>
