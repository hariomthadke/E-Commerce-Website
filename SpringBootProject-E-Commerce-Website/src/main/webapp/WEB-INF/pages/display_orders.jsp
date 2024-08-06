<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Order's</title>
<%@include file="/Components/common_css_js.jsp"%>
</head>
<body>
	<!--navbar -->
	<%@include file="/Components/navbar.jsp"%>

	<!-- order details -->

	<div class="container-fluid px-3 py-3">

		<c:choose>
			<c:when test="${orderList==null || orderList.size()==0 }">
				<div class="container mt-5 mb-5 text-center">
					<img src="Images/empty-cart.png" style="max-width: 200px;"
						class="img-fluid">
					<h4 class="mt-3">Zero Order found</h4>
				</div>
			</c:when>
			<c:otherwise>

				<div class="container-fluid">
					<table class="table table-hover">
						<tr class="table-primary" style="font-size: 18px;">
							<th class="text-center">Product</th>
							<th>Order ID</th>
							<th>Product Details</th>
							<th>Delivery Address</th>
							<th>Date & Time</th>
							<th>Payment Type</th>
							<th>Status</th>
							<th colspan="2" class="text-center">Action</th>
						</tr>

						<c:forEach var="order" items="${orderList}">
							<form action="updateOrderStatus?orderId=${order.id}"
								method="post">
								<tr>
									<td class="text-center"><img
										src="uploads/${order.productDetails.image}"
										style="width: 50px; height: 50px; width: auto;"></td>
									<td>${order.id}</td>
									<td>${order.productDetails.name}<br>Quantity:
										${order.quantity}<br>Total Price: &#8377;${order.quantity * order.price}
									</td>
									<td>${userAddress[order.userId]}</td>
									<td>${order.orderDate}</td>
									<td>${order.paymentType}</td>
									<td>${order.status}</td>
									<td><select id="operation" name="status"
										class="form-select">
											<option value="">Select Status</option>
											<option value="Confirmed">Confirmed</option>
											<option value="Shipped">Shipped</option>
											<option value="Out For Delivery">Out For Delivery</option>
											<option value="Delivered">Delivered</option>
									</select></td>
									<td><c:choose>
											<c:when test="${order.status.equals('Delivered')}">
												<button type="submit" class="btn btn-success disabled">Update</button>
											</c:when>
											<c:otherwise>
												<button type="submit" class="btn btn-secondary">Update</button>

											</c:otherwise>
										</c:choose></td>
								</tr>
							</form>

						</c:forEach>
					</table>

				</div>

			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>