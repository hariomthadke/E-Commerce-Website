<%@ page isELIgnored="false" import="java.util.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Products</title>
<%@include file="/Components/common_css_js.jsp"%>
<style>
.real-price {
	font-size: 22px !important;
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

.wishlist-icon {
	cursor: pointer;
	position: absolute;
	right: 10px;
	top: 10px;
	width: 36px;
	height: 36px;
	border-radius: 50%;
	border: 1px solid #f0f0f0;
	box-shadow: 0 1px 4px 0 rgba(0, 0, 0, .1);
	padding-right: 40px;
	background: #fff;
}
</style>
</head>
<body style="background-color: #f0f0f0;">
	<!--navbar -->
	<%@include file="/Components/navbar.jsp"%>

	<!--show products-->
	<div class="container-fluid my-3 px-5">

		<div class="row row-cols-1 row-cols-md-4 g-3">

			<c:forEach var="prod" items="${productList}" varStatus="status">

				<div class="col">

					<div class="card h-100 px-2 py-2">

						<div class="container text-center">
							<div>
								<a href="viewProduct?pid=${prod.id}"
									style="text-decoration: none;"> <img
									src="uploads/${prod.image}" class="card-img-top m-2"
									style="max-width: 100%; max-height: 200px; width: auto;">
								</a>
							</div>
							<div class="wishlist-icon">


								<c:choose>
									<c:when test="${activeUser!=null}">
										<c:choose>
											<c:when test="${fn:contains(wishList, prod.id)}">
												<button
													onclick="window.open('wishlist?uid=${activeUser.id}&pid=${prod.id}&op=remove', '_self')"
													class="btn btn-link" type="submit">
													<i class="fa-sharp fa-solid fa-heart"
														style="color: #ff0303; margin: -6px; transition: transform 0.3s, color 0.3s; transform: scale(1.2) translateY(-5px);"></i>
												</button>
											</c:when>

											<c:otherwise>
												<button type="submit"
													onclick="window.open('wishlist?uid=${activeUser.id}&pid=${prod.id}&op=add', '_self')"
													class="btn btn-link">
													<i class="fa-sharp fa-solid fa-heart"
														style="color: #909191; margin: -6px; transition: transform 0.3s, color 0.3s; transform: scale(1.2) translateY(-5px);"></i>
												</button>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>

										<button onclick="window.open('login', '_self')"
											class="btn btn-link" type="submit">
											<i class="fa-sharp fa-solid fa-heart"
												style="color: #909191; margin: -6px; transition: transform 0.3s, color 0.3s; transform: scale(1.2) translateY(-5px);"></i>
										</button>
									</c:otherwise>
								</c:choose>

							</div>
							<a href="viewProduct?pid=${prod.id}"
								style="text-decoration: none; color: inherit;">
								<div>
									<h5 class="card-title text-center">${prod.name}</h5>
									<div class="container text-center">
										<span class="real-price">&#8377;${prod.priceAfterDiscount}</span>&ensp;
										<span class="product-price">&#8377;${prod.price}</span>&ensp;
										<span class="product-discount">${prod.discount}&#37;off</span>
									</div>
								</div>
							</a>

						</div>
					</div>

				</div>

			</c:forEach>
		</div>
	</div>
</body>
</html>

