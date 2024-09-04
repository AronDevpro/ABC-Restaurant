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
    <%@include file="../assets/js/my-order.js"%>
    <%@include file="../assets/js/my-queries.js"%>
    <%@include file="../assets/js/my-reservation.js"%>
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

            items: 4,
            itemsDesktop: [1199, 4],
            itemsDesktopSmall: [979, 2]
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

    document.addEventListener('DOMContentLoaded', function () {

        //   update model
        const updateModel = document.getElementById('updateModel');
        const updateProductModelInstance = bootstrap.Modal.getOrCreateInstance(updateModel);

        document.querySelectorAll('.view-facility-btn').forEach(function (button) {
            button.addEventListener('click', function () {
                const id = this.getAttribute('data-id');

                fetch('<%= request.getContextPath() %>/admin/products/view?id=' + id)
                    .then(response => response.json())
                    .then(data => {
                        updateModel.querySelector('input[name="id"]').value = data.id;
                        updateModel.querySelector('input[name="name"]').value = data.name;
                        updateModel.querySelector('input[name="description"]').value = data.description;
                        updateModel.querySelector('input[name="category"]').value = data.category;
                        updateModel.querySelector('input[name="price"]').value = data.price;
                        updateModel.querySelector('select[name="status"]').value = data.status;

                        // Set current image source if available
                        const imageUrl = data.imagePath ? '<%= request.getContextPath() %>/assets' + data.imagePath.replace(/\\/g, '/') : '';
                        const currentImage = updateModel.querySelector('#currentImage');
                        if (imageUrl) {
                            currentImage.src = imageUrl;
                            currentImage.style.display = 'block';
                        } else {
                            currentImage.style.display = 'none';
                        }

                        updateProductModelInstance.show();
                    })
                    .catch(error => console.error('Error fetching product details:', error));
            });
        });
    });

        // update footer settings
        fetch('<%= request.getContextPath() %>/settings')
        .then(response => response.json())
        .then(data => {

        // Update logo image
        const footerLogo = document.getElementById('footerLogo');
        if (data.logoPath) {
        const logoUrl = '<%= request.getContextPath() %>/assets' + data.logoPath.replace(/\\/g, '/');
        footerLogo.src = logoUrl;
        footerLogo.style.display = 'block'; // Show image
    } else {
        footerLogo.style.display = 'none'; // Hide image if path is empty
    }

        // Update address
        const footerAddress = document.getElementById('footerAddress');
        if (data.siteStreetAddress || data.siteZip || data.siteCity) {
        const addressParts = [data.siteStreetAddress, data.siteZip, data.siteCity].filter(part => part).join(', ');
        footerAddress.textContent = addressParts || 'No address available';
    } else {
        footerAddress.textContent = 'No address available';
    }
        // Update email
        const footerEmail = document.getElementById('footerEmail');
        footerEmail.textContent = data.siteEmail ? data.siteEmail : '';

        // Update desc
        const footerDESC = document.getElementById('footerDesc');
        footerDESC.textContent = data.description ? data.description : '';
    })
        .catch(error => console.error('Error fetching settings:', error));
</script>
