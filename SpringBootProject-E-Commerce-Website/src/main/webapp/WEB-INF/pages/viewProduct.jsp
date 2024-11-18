<%@page import="com.nt.model.Users"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page isELIgnored="false" import="java.util.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Product</title>
<%@include file="/Components/common_css_js.jsp"%>
<style type="text/css">
.real-price {
	font-size: 26px !important;
	font-weight: 600;
}

.product-price {
	font-size: 18px !important;
	text-decoration: line-through;
}

.product-discount {
	font-size: 16px !important;
	color: #027a3e;
}
</style>
</head>
<body>

	<!--navbar -->
	<%@include file="/Components/navbar.jsp"%>

	<div class="container mt-5">
		<%@include file="/Components/alert_message.jsp"%>
		<div class="row border border-3">
			<div class="col-md-6">
				<div class="container-fluid text-end my-3">
					<img src="uploads/${product.image}" class="card-img-top"
						style="max-width: 100%; max-height: 500px; width: auto;">
				</div>
			</div>
			<div class="col-md-6">
				<div class="container-fluid my-5">
					<h4>${product.name}</h4>
					<span class="fs-5"><b>Description</b></span><br> <span>${product.description}</span><br>
					<span class="real-price">&#8377;${product.priceAfterDiscount}</span>&ensp;
					<span class="product-price">&#8377;${product.price}</span>&ensp; <span
						class="product-discount">${product.discount}&#37;off</span><br>
					<span class="fs-5"><b>Status : </b></span>
					<c:choose>
						<c:when test="${product.quantity>0}">
							<span id="availability" style="color: green">Available</span>
						</c:when>
						<c:otherwise>
							<span id="availability" style="color: red">Currently Out
								of Stock</span>
							<a href="/notify?pid=${product.id}" title="Notify when available"> <i
								class="fa-solid fa-bell"></i>
							</a>
						</c:otherwise>
					</c:choose>

					<br> <span class="fs-5"><b>Category : </b></span> <span>${categoryType}</span>
					<form method="post">
						<div class="container-fluid text-center mt-3">
							<c:choose>
								<c:when test="${activeUser==null}">
									<button type="button" onclick="window.open('login', '_self')"
										class="btn btn-primary text-white btn-lg">Add to Cart</button>
									&emsp;
									<button type="button" onclick="window.open('login', '_self')"
										class="btn btn-info text-white btn-lg">Buy Now</button>
								</c:when>
								<c:otherwise>
									<h6 class="text-center">Quantity</h6>
									<div class="input-group input-number-group">
										<div class="input-group-button">
											<span class="input-number-decrement">-</span>
										</div>
										<input class="input-number" name="quantity" type="number"
											value="1" min="0" max="100">
										<div class="input-group-button">
											<span class="input-number-increment">+</span>
										</div>
									</div>


									<button type="submit"
										formaction="addToCart?userId=${activeUser.id}&productId=${product.id}"
										class="btn btn-primary text-white btn-lg">Add to Cart</button>
									&emsp;
									<button type="submit"
										formaction="checkout?productId=${product.id}" id="buy-btn"
										class="btn btn-info text-white btn-lg" role="button"
										aria-disabled="true">Buy Now</button>
								</c:otherwise>
							</c:choose>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script>
		$(document).ready(function() {
			if ($('#availability').text().trim() == "Currently Out of stock") {
				$('#availability').css('color', 'red');
				$('.btn').addClass('disabled');
			}
			$('#buy-btn').click(function(){

				});
		});
	</script>
	<style>
.input-number-group {
	display: -webkit-flex;
	display: -ms-flexbox;
	display: flex;
	-webkit-justify-content: center;
	-ms-flex-pack: center;
	justify-content: center;
}

.input-number-group input[type=number]::-webkit-inner-spin-button,
	.input-number-group input[type=number]::-webkit-outer-spin-button {
	-webkit-appearance: none;
	appearance: none;
}

.input-number-group .input-group-button {
	line-height: calc(80px/ 2 - 5px);
}

.input-number-group .input-number {
	width: 80px;
	padding: 0 12px;
	vertical-align: top;
	text-align: center;
	outline: none;
	display: block;
	margin: 0;
}

.input-number-group .input-number, .input-number-group .input-number-decrement,
	.input-number-group .input-number-increment {
	border: 1px solid #cacaca;
	height: 40px;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	border-radius: 0;
}

.input-number-group .input-number-decrement, .input-number-group .input-number-increment
	{
	display: inline-block;
	width: 40px;
	background: #e6e6e6;
	color: #0a0a0a;
	text-align: center;
	font-weight: bold;
	cursor: pointer;
	font-size: 2rem;
	font-weight: 400;
}

.input-number-group .input-number-decrement {
	margin-right: 0.3rem;
}

.input-number-group .input-number-increment {
	margin-left: 0.3rem;
}
</style>
	<script>
    $('.input-number-increment').click(function() {
        var $input = $(this).parents('.input-number-group').find('.input-number');
        var val = parseInt($input.val(), 10);
        $input.val(val + 1);
    });

    $('.input-number-decrement').click(function() {
        var $input = $(this).parents('.input-number-group').find('.input-number');
        var val = parseInt($input.val(), 10);
        if (val > 1) {
            $input.val(val - 1);
        }
    });
</script>
	<%
	String order = (String) session.getAttribute("order");
	if (order != null) {
		Users activeUser = (Users) session.getAttribute("activeUser");
		String userEmail = (activeUser != null) ? activeUser.getEmail() : "";
	%>

	<script type="text/javascript">
		console.log("testing..4...");
		Swal.fire({
		  icon : 'success',
		  title: 'Order Placed, Thank you!',
		  text: 'Confirmation will be sent to <%=userEmail%> ',
		  width: 600,
		  padding: '3em',
		  showConfirmButton : false,
		  timer : 3500,
		  backdrop: `
		    rgba(0,0,123,0.4)
		  `
		});
	</script>
	<%
	}
	session.removeAttribute("order");
	%>

</body>
</html>