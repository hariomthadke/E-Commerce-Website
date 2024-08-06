<%@ page isELIgnored="false" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
.navbar {
	font-weight: 500;
}

.nav-link {
	color: rgb(255 255 255/ 100%) !important;
}

.dropdown-menu {
	background-color: #ffffff !important;
	border-color: #949494;
}

.dropdown-menu li a:hover {
	background-color: #808080 !important;
	color: white;
}
</style>
<nav class="navbar navbar-expand-lg custom-color" data-bs-theme="dark">

	<!-- admin nav bar -->
	<c:choose>
		<c:when
			test="${activeUser != null && activeUserRole.equals( 'admin')}">
			<div class="container">
				<a class="navbar-brand" href="admin"><i
					class="fa-sharp fa-solid fa-house" style="color: #ffffff;"></i>&ensp;EazyDeals</a>
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarSupportedContent">

					<div class="container text-end">
						<ul class="navbar-nav justify-content-end">
							<li class="nav-item"><button type="button"
									class="btn nav-link" data-bs-toggle="modal"
									data-bs-target="#add-category">
									<i class="fa-solid fa-plus fa-xs"></i>Add Category
								</button></li>
							<li class="nav-item"><button type="button"
									class="btn nav-link" data-bs-toggle="modal"
									data-bs-target="#add-product">
									<i class="fa-solid fa-plus fa-xs"></i>Add Product
								</button></li>

							<li class="nav-item"><a class="nav-link" aria-current="page"
								href="admin">${activeUser.name}</a></li>

							<li class="nav-item"><a class="nav-link" aria-current="page"
								href="logout?user=admin"><i
									class="fa-solid fa-user-slash fa-sm" style="color: #fafafa;"></i>&nbsp;Logout</a></li>
						</ul>
					</div>
				</div>
			</div>
		</c:when>

		<c:otherwise>
			<div class="container">
				<a class="navbar-brand" href="/"><i
					class="fa-sharp fa-solid fa-house" style="color: #ffffff;"></i>&ensp;EazyDeals</a>
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav me-auto mb-2 mb-lg-0">
						<li class="nav-item"><a class="nav-link" href="products"
							role="button" aria-expanded="false"> Products </a>
						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle" href="#" role="button"
							data-bs-toggle="dropdown" aria-expanded="false"> Category </a>
							<ul class="dropdown-menu">
								<li><a class="dropdown-item" href="products?category=0">All
										Products</a></li>

								<c:forEach var="cat" items="${categoryList}">
									<li><a class="dropdown-item"
										href="products?category=${cat.id}">${cat.name}</a></li>
								</c:forEach>

							</ul></li>
					</ul>
					<form class="d-flex pe-5" role="search" action="products.jsp"
						method="get">
						<input name="search" class="form-control me-2" size="50"
							type="search" placeholder="Search for products"
							aria-label="Search" style="background-color: white !important;">
						<button class="btn btn-outline-light" type="submit">Search</button>
					</form>


					<c:choose>
						<c:when
							test="${activeUser != null && activeUserRole.equals( 'user')}">
							<ul class="navbar-nav ml-auto">
								<li class="nav-item active pe-3"><a
									class="nav-link position-relative" aria-current="page"
									href="showCart?uid=${activeUser.id}"><i
										class="fa-solid fa-cart-shopping" style="color: #ffffff;"></i>
										&nbsp;Cart<span
										class="position-absolute top-1 start-0 translate-middle badge rounded-pill bg-danger">${totalCartCount}</span></a></li>
								<li class="nav-item active pe-3"><a class="nav-link"
									aria-current="page" href="userProfile">${activeUser.name}</a></li>
								<li class="nav-item pe-3"><a class="nav-link"
									aria-current="page" href="logout?user=user"><i
										class="fa-solid fa-user-slash" style="color: #fafafa;"></i>&nbsp;Logout</a></li>
							</ul>
						</c:when>

						<c:otherwise>
							<ul class="navbar-nav ml-auto">
								<li class="nav-item active pe-2"><a class="nav-link"
									aria-current="page" href="register"> <i
										class="fa-solid fa-user-plus" style="color: #ffffff;"></i>&nbsp;Register
								</a></li>
								<li class="nav-item pe-2"><a class="nav-link"
									aria-current="page" href="login"><i
										class="fa-solid fa-user-lock" style="color: #fafafa;"></i>&nbsp;Login</a></li>
							</ul>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

		</c:otherwise>
	</c:choose>
	<!-- end  -->
</nav>

