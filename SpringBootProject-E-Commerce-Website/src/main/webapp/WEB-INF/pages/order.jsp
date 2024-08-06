<%@ page isELIgnored="false" import="java.util.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid px-3 py-3">

	<c:choose>
		<c:when test="${orderList==null || orderList.size()==0}">
			<div class="container mt-5 mb-5 text-center">
				<img src="Images/empty-cart.png" style="max-width: 200px;"
					class="img-fluid">
				<h4 class="mt-3">Zero Order found</h4>
				Looks like you haven't placed any order!
			</div>
		</c:when>

		<c:otherwise>

			<h4>My Order</h4>
			<hr>
			<div class="container">
				<table class="table table-hover">
					<tr class="text-center table-secondary">
						<th>Product</th>
						<th>Order ID</th>
						<th>Name</th>
						<th>Quantity</th>
						<th>Total Price</th>
						<th>Date and Time</th>
						<th>Payment Type</th>
						<th>Status</th>
					</tr>

					<c:forEach var="order" items="${orderList}">
						<tr class="text-center">
							<td><img src="uploads/${order.productDetails.image}"
								style="width: 40px; height: 40px; width: auto;"></td>
							<td class="text-center">${order.id}</td>
							<td class="text-center">${order.productDetails.name}</td>
							<td>${order.quantity}</td>
							<td>${order.quantity*order.price}
							<td>${order.orderDate}</td>
							<td>${order.paymentType}</td>
							<td class="fw-semibold" style="color: green;">${order.status}</td>
						</tr>

					</c:forEach>

				</table>
			</div>

		</c:otherwise>
	</c:choose>
</div>
