<%--
  Created by IntelliJ IDEA.
  User: Arosha
  Date: 8/13/2024
  Time: 5:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    session = request.getSession(false);
    boolean isLoggedIn = session != null && session.getAttribute("user") != null;
%>
<nav class="navbar navbar-expand-lg bg-body-tertiary mb-3" style="height: 80px; border-bottom: 1px solid #efeded">
    <div class="container">
        <a class="navbar-brand" href="/"><img src="<%= request.getContextPath() %>/assets/uploads/logo/logo-top.png" alt="" class="logo"></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse bg-white" id="navbarSupportedContent" style="z-index: 999">
            <ul class="navbar-nav ms-auto me-auto gap-3">
                <li class="nav-item">
                    <a class="nav-link active navMod" aria-current="page" href="/">HOME</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navMod" href="/menu/">MENU</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navMod" href="/facility">FACILITIES</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navMod" href="/offers">SPECIAL OFFERS</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navMod" href="/reservation">BOOK NOW</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navMod" href="/contact">CONTACT US</a>
                </li>
            </ul>
            <div class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    <i class="fa-solid fa-cart-shopping"></i>
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown" id="cartDropdown">
                </ul>
            </div>
            <% if (isLoggedIn) { %>
            <ul class="navbar-nav ms-0 ms-lg-5">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown1" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown1">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/customer/">Settings</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/customer/orders">My Orders</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/customer/reservations">My Reservation</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/customer/queries">My Queries</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/customer/query">Submit Query</a></li>
                        <li><hr class="dropdown-divider" /></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/customer/logout">Logout</a></li>
                    </ul>
                </li>
            </ul>
            <% } else { %>
            <a href="/login" class="btn ms-4" type="button" style="border: 5px solid black; font-weight: bold">
                LOGIN
            </a>
            <% } %>
        </div>
    </div>
</nav>
