<%@page import="com.nt.model.Users"%>
<%@ page isELIgnored="false" import="java.util.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Shopping cart</title>
<%@include file="/Components/common_css_js.jsp"%>
<style type="text/css">
.qty {
	display: inline-block;
	padding: 3px 6px;
	width: 46px;
	height: 32px;
	border-radius: 2px;
	background-color: #fff;
	border: 1px solid #c2c2c2;
	margin: 0 5px;
}
</style>
</head>
<body>
	<!--navbar -->
	<%@include file="/Components/navbar.jsp"%>

	<c:choose>
		<c:when test="${cartList==null }">
			<%@include file="/Components/alert_message.jsp"%>
			<div class="container text-center py-5 px-5">
				<img src="Images/empty-cart.png" style="max-width: 250px;"
					class="img-fluid">
				<h4 class="mt-5">Your cart is empty!</h4>
				<p>Add items to it now.</p>
				<a href="products" class="btn btn-primary btn-lg" role="button"
					aria-disabled="true">Shop Now</a>
			</div>
		</c:when>

		<c:otherwise>
			<div class="container mt-5">
				<%@include file="/Components/alert_message.jsp"%>
				<div class="card px-3 py-3">
					<table class="table table-hover">
						<thead>
							<tr class="table-primary text-center" style="font-size: 18px;">
								<th>Item</th>
								<th>Item Name</th>
								<th>Price</th>
								<th>Quantity</th>
								<th>Total price</th>
								<th>Remove</th>
							</tr>
						</thead>
						<tbody>

							<c:forEach var="cart" items="${cartList}">
								<tr class="text-center">

									<td>
										<div>
											<a href="viewProduct?pid=${cart.productDetails.id}"
												style="text-decoration: none;"> <img
												src="uploads/${cart.productDetails.image}"
												style="width: 50px; height: 50px; width: auto;" />
											</a>
										</div>
									</td>

									<td class="text-center">
										<div>
											<a href="viewProduct?pid=${cart.productDetails.id}"
												style="text-decoration: none; color: inherit;">
												${cart.productDetails.name} </a>
										</div>
									</td>

									<td>&#8377;${cart.productDetails.priceAfterDiscount}</td>
									<td>
										<div class="text-center">${cart.quantity}</div> &#8377;
										${cart.quantity * cart.productDetails.priceAfterDiscount}
									</td>
									<td><a href="removeCart?cartId=${cart.id}"
										class="btn btn-secondary" role="button">Remove</a></td>
								</tr>
							</c:forEach>
							<tr>
								<td class="text-end" colspan="8"><h4 class='pe-5'>
										Total Amount : &#8377; ${totalPrice}</h4></td>
							</tr>
						</tbody>
					</table>
					<div class="text-end">
						<form method="post">
							<button type="submit" formaction="checkout" id="checkout-btn"
								class="btn btn-outline-primary" role="button"
								aria-disabled="true">Checkout</button>
						</form>
					</div>

				</div>
			</div>

		</c:otherwise>
	</c:choose>
	<script>
		$(document).ready(function(){
			$('#checkout-btn').click(function(){

			});
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
		  title: 'All Orders are Placed, Thank you!',
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