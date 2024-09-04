<%@ page import="java.time.LocalDate" %><%--
  Created by IntelliJ IDEA.
  User: Arosha
  Date: 8/15/2024
  Time: 6:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<footer class="text-light text-center">
  <div class="container-fluid footer py-5 wow fadeIn" data-wow-delay=".3s">
    <div class="container py-5">
      <div class="row g-4 footer-inner">
        <div class="col-lg-4 col-md-4">
          <div class="footer-item">
            <img id="footerLogo" src="" alt="Logo" style="width: 200px; display: none;">
            <h5 id="footerDesc" class="text-light mt-3 text-start" style="font-size: 15px"></h5>
          </div>
        </div>
        <div class="col-lg-4 col-md-4">
          <div class="footer-item">
            <h4 class="text-white fw-bold mb-4">Useful Link</h4>
            <div class="d-flex flex-column align-items-center">
              <a class="btn btn-link ps-0 text-decoration-none" href="/">Home</a>
              <a class="btn btn-link ps-0 text-decoration-none" href="/menu/">Menu</a>
              <a class="btn btn-link ps-0 text-decoration-none" href="/reservation">Book</a>
              <a class="btn btn-link ps-0 text-decoration-none" href="/register">Register</a>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-md-4">
          <div class="footer-item">
            <h4 class="text-white fw-bold mb-4">Contact Us</h4>
            <p><i class="fa fa-map-marker-alt me-3"></i><span id="footerAddress"></span></p>
            <p><i class="fas fa-envelope me-3"></i><span id="footerEmail"></span></p>
            <p><i class="fa fa-phone-alt me-3"></i>+94 71 111 1112</p>
          </div>
          <div class="text-center text-md-start mb-3 mb-md-0 d-flex justify-content-center">
            <a class="btn btn-outline-light rounded-circle me-3" href=""><i class="fab fa-twitter"></i></a>
            <a class="btn btn-outline-light rounded-circle me-3" href=""><i class="fab fa-facebook-f"></i></a>
            <a class="btn btn-outline-light rounded-circle me-3" href=""><i class="fab fa-youtube"></i></a>
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <p class="text-center">All Right Reserved by ABC Restaurant &copy; <%= LocalDate.now().getYear() %> </p>
    </div>
  </div>
</footer>
<%@ include file="script.jsp" %>

</body>
</html>
