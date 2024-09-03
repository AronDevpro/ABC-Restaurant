<%--
  Created by IntelliJ IDEA.
  User: Arosha
  Date: 8/13/2024
  Time: 5:44 PM
  To change this template use File | Settings | File Templates.
--%>
<nav class="navbar navbar-expand-lg bg-body-tertiary mb-3" style="height: 80px">
    <div class="container">
        <a class="navbar-brand" href="#">ABC Restaurant</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
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
                <button class="btn ms-4" type="button" style="border: 5px solid black">
                    BOOK NOW
                </button>
        </div>
    </div>
</nav>
