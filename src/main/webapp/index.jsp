<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<%@ include file="template/header.jsp" %>
<body>
<%@ include file="template/topNav.jsp" %>
<div class="container-fluid px-0 py-0">
    <div id="owl-banner" class="owl-carousel owl-loaded owl-drag wow fadeInUp" data-wow-delay=".3s">
        <div class="item">
            <div class="image-container wow fadeInUp banner banner-1" data-wow-delay=".5s">
                <div class="container px-4 px-lg-5 h-75 wow fadeInUp" data-wow-delay=".3s">
                    <div class="row gx-4 gx-lg-5 h-100 align-items-center justify-content-center text-center custom-color">
                        <div class="col-lg-8 align-self-end">
                            <h1 class="title title-second text-light"><span class="title-first">WELCOME TO</span> ABC RESTAURANT</h1>
                            <hr class="divider"/>
                        </div>
                        <div class="col-lg-8 align-self-baseline wow fadeInUp" data-wow-delay=".6s">
                            <h5>Fresh Flavors, Unforgettable Moments</h5>
                            <a href="/menu/" class="btn mt-5 text-light" style="border: 5px solid white; font-weight: bold">ORDER NOW</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="item">
            <div class="image-container wow fadeInUp banner banner-2" data-wow-delay=".5s">
                <div class="container px-4 px-lg-5 h-75 wow fadeInUp" data-wow-delay=".3s">
                    <div class="row gx-4 gx-lg-5 h-100 align-items-center justify-content-center text-center custom-color">
                        <div class="col-lg-8 align-self-end">
                            <h1 class="title title-second text-light"><span class="title-first">WELCOME TO</span> ABC RESTAURANT</h1>
                            <hr class="divider"/>
                        </div>
                        <div class="col-lg-8 align-self-baseline wow fadeInUp" data-wow-delay=".6s">
                            <h5>Fresh Flavors, Unforgettable Moments</h5>
                            <a href="/reservation" class="btn text-light mt-5" style="border: 5px solid white; font-weight: bold">BOOK NOW</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<%--    about us--%>
    <div class="row">
        <div class="col-12 col-md-6 justify-content-center align-content-center text-center mt-3 mt-md-0 wow slideInLeft" data-wow-delay=".3s">
            <h2 class="title title-second"><span class="title-first">ABOUT</span> ABC RESTAURANT</h2>
        </div>
        <div class="col-12 col-md-6 about-us wow slideInRight" data-wow-delay=".3s">
            <div class="px-4 text-light" style="text-align: justify">
                <h2 class="text-light text-center mb-3">Flavor, Tradition, Innovation—Since 2009</h2>
            <p>
                Since 2009, ABC Restaurant has been serving up exceptional dining experiences, one meal at a time. Founded by Mr.Arosha, our journey began with a single location in Colombo 5. Over the past 15 years, we've grown into a beloved chain with multiple locations across South East Asia, each staying true to our commitment to quality and innovation.</p>
            <p>
                At ABC Restaurant, we combine fresh, locally sourced ingredients with culinary creativity to offer a menu that delights and surprises. Whether you're visiting us for a quick bite or a special occasion, our friendly staff and welcoming atmosphere ensure that every visit is as enjoyable as the first. Join us in celebrating over 15 years of flavor, tradition, and excellence.
            </p>
            </div>
        </div>
    </div>

<%--    restaurant chain--%>
    <h2 class="title title-second text-center my-3"><span class="title-first">FIND AN</span> ABC RESTAURANT</h2>
    <div id="owl-chain" class="owl-carousel owl-loaded owl-drag">
        <div class="owl-stage-outer">
            <div class="owl-stage">
                <c:forEach var="restaurant" items="${restaurantList}">
                <div class="owl-item">
                    <div class="align-content-end px-3 text-light" style="background:linear-gradient(to bottom, rgba(0, 0, 0, 0.3) 0%, rgba(0, 0, 0, 0.3) 100%), url('${pageContext.request.contextPath}/assets/${restaurant.image.replace('\\', '/').replace(' ', '%20')}'); width: 350px; height: 400px; background-size: cover;">

                <%--                    <img src="${pageContext.request.contextPath}/assets/${restaurant.image.replace('\\', '/').replace(' ', '%20')}"--%>
<%--                         alt="${restaurant.name}" style="width: 350px; height: 400px;">--%>
                    <h3 class="text-light">${restaurant.name}</h3>
                    <p>${restaurant.address}</p>
                    <p>${restaurant.phoneNumber}</p>
                    </div>
                </div>
                </c:forEach>
            </div>
        </div>
    </div>

<%--    gallery--%>
    <h2 class="title title-second text-center my-3"><span class="title-first">EXPLORE OUR</span> CULINARY CREATIONS</h2>
    <div class="row px-0">
        <c:forEach var="gallery" items="${galleryList}">
            <div class="col-4 px-0">
                <img src="${pageContext.request.contextPath}/assets/${gallery.imagePath.replace('\\', '/').replace(' ', '%20')}"
                     alt="${gallery.title}" style="width: 100%; height: auto; display: block; max-width: 100%;">
            </div>
        </c:forEach>
    </div>
</div>
<%@ include file="template/footer.jsp" %>