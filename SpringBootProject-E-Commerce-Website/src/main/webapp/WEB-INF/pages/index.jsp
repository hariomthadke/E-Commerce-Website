<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" import="java.util.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<%@include file="/Components/common_css_js.jsp"%>
<style type="text/css">
.cus-card {
	border-radius: 50%;
	border-color: transparent;
	max-height: 200px;
	max-width: 200px;
	max-height: 200px;
}

.real-price {
	font-size: 20px !important;
	font-weight: 600;
}

.product-price {
	font-size: 17px !important;
	text-decoration: line-through;
}

.product-discount {
	font-size: 15px !important;
	color: #027a3e;
}
</style>
</head>
<body>
	<!--navbar -->
	<%@include file="/Components/navbar.jsp"%>

	<!-- Category list -->
	<div class="container-fluid px-3 py-3"
		style="background-color: #e3f7fc;">
		<div class="row">
			<div class="card-group">
				<c:forEach var="cat" items="${categoryList}">
					<div class="col text-center">
						<a href="products?category=${cat.id}"
							style="text-decoration: none; color: inherit;">
							<div class="container text-center">
								<img src="uploads/${cat.filePath}" class="mt-3"
									style="max-width: 100%; max-height: 100px; width: auto; height: auto;">
							</div>
							<h6 class="mt-2" style="text-decoration: none;">${cat.name}</h6>
						</a>

					</div>
				</c:forEach>
			</div>
		</div>
	</div>

	<!-- end of list -->

	<!-- Carousel -->
	<div id="carouselAutoplaying"
		class="carousel slide carousel-dark mt-3 mb-3" data-bs-ride="carousel">
		<div class="carousel-inner">
			<div class="carousel-item active">
				<img src="Images/scroll_img2.png" class="d-block w-100" alt="...">
			</div>
			<div class="carousel-item">
				<img src="Images/scroll_img1.png" class="d-block w-100" alt="...">
			</div>
			<div class="carousel-item">
				<img src="Images/scroll_img3.png" class="d-block w-100" alt="...">
			</div>
		</div>
		<button class="carousel-control-prev" type="button"
			data-bs-target="#carouselAutoplaying" data-bs-slide="prev">
			<span class="carousel-control-prev-icon" aria-hidden="true"
				style="color: black;"></span> <span class="visually-hidden">Previous</span>
		</button>
		<button class="carousel-control-next" type="button"
			data-bs-target="#carouselAutoplaying" data-bs-slide="next">
			<span class="carousel-control-next-icon" aria-hidden="true"></span> <span
				class="visually-hidden">Next</span>
		</button>
	</div>
	<!-- end of carousel -->

	<!-- latest product listed -->
	<div class="container-fluid py-3 px-3" style="background: #f2f2f2;">
		<div class="row row-cols-1 row-cols-md-4 g-3">
			<div class="col">
				<div class="container text-center px-5 py-5">
					<h1>Latest Products</h1>
					<img src="Images\product.png" class="card-img-top"
						style="max-width: 100%; max-height: 200px; width: auto;">
				</div>
			</div>

			<c:forEach var="prod" items="${productList}">
				<div class="col">
					<a href="viewProduct?pid=${prod.id}" style="text-decoration: none;">
						<div class="card h-100">
							<div class="container text-center">
								<img src="uploads/${prod.image}" class="card-img-top m-2"
									style="max-width: 100%; max-height: 200px; width: auto;">
							</div>
							<div class="card-body">
								<h5 class="card-title text-center">${prod.name}</h5>

								<div class="container text-center">
									<span class="real-price">&#8377;${prod.priceAfterDiscount}</span>
									&ensp;<span class="product-price">&#8377;${prod.price} </span>&ensp;<span
										class="product-discount">${prod.discount}&#37; off</span>
								</div>
							</div>
						</div>
					</a>
				</div>

			</c:forEach>
		</div>
	</div>
	<!-- end of list -->

	<!-- product with heavy deals -->
	<div class="container-fluid py-3 px-3" style="background: #f0fffe;">
		<h3>Hot Deals</h3>
		<div class="row row-cols-1 row-cols-md-4 g-3">

			<c:forEach var="prod" items="${topDealsProdcuts}">
				<div class="col">
					<a href="viewProduct?pid=${prod.id}" style="text-decoration: none;">

						<div class="card h-100">
							<div class="container text-center">
								<img src="uploads/${prod.image}" class="card-img-top m-2"
									style="max-width: 100%; max-height: 200px; width: auto;">
							</div>
							<div class="card-body">
								<h5 class="card-title text-center">${prod.name}</h5>

								<div class="container text-center">
									<span class="real-price">&#8377;${prod.priceAfterDiscount}</span>
									&ensp;<span class="product-price">&#8377;${prod.price} </span>&ensp;<span
										class="product-discount">${prod.discount}&#37; off</span>
								</div>
							</div>
						</div>
					</a>
				</div>

			</c:forEach>
		</div>
	</div>
	<!-- end -->

</body>
</html>