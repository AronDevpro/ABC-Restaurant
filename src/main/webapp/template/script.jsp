<%--
  Created by IntelliJ IDEA.
  User: Arosha
  Date: 9/1/2024
  Time: 4:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        crossOrigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
    <%@include file="../assets/js/cart.js"%>
    <%@include file="../assets/js/checkout.js"%>
    <%@include file="../assets/library/wow/wow.min.js"%>
    <%@include file="../assets/library/owl/owl.carousel.js"%>

    // Initiate the wowjs
    new WOW().init();

    //banner function
    $(document).ready(function () {
        $("#owl-banner").owlCarousel({
            autoplay: true,
            autoPlay: 1000,

            items: 1,
            itemsDesktop: [1199, 1],
            itemsDesktopSmall: [979, 1]
        });
    });

    //chain function
    $(document).ready(function () {
        $("#owl-chain").owlCarousel({
            autoplay: true,
            autoPlay: 1000,

            responsiveClass:true,
            responsive:{
                0:{
                    items:1,
                    nav:false
                },
                600:{
                    items:2,
                    nav:false
                },
                1000:{
                    items:4,
                    nav:false,
                    loop:false
                }
            }
        });
    });

    function submitSearchForm() {
        var searchInput = document.querySelector('input[name="search"]').value;
        if (searchInput.length >= 3) {
            document.getElementById("searchForm").submit();
        }
    }

    function selectCategory(category) {
        document.getElementById('filterCategoryInput').value = category;
        document.getElementById('filterForm').submit();
    }

    function submitSelectForm() {
        document.getElementById('filterForm').submit();
    }
</script>
