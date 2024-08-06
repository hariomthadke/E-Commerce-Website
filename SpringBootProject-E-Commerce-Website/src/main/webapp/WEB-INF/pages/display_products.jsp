<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Product's</title>
<%@include file="/Components/common_css_js.jsp"%>
</head>
<body>
	<!--navbar -->
	<%@include file="/Components/navbar.jsp"%>

	<!-- update product -->
	<div class="container mt-3">
		<%@include file="/Components/alert_message.jsp"%>
		<table class="table table-hover">
			<tr class="table-primary text-center" style="font-size: 20px;">
				<th>Image</th>
				<th>Name</th>
				<th class="text-start">Category</th>
				<th>Price</th>
				<th class="text-start">Quantity</th>
				<th class="text-start">Discount</th>
				<th>Action</th>
			</tr>
			<c:forEach var="prod" items="${productList}">
				<tr class="text-center">
					<td><img src="uploads/${prod.image}"
						style="width: 60px; height: 60px; width: auto;"></td>
					<td class="text-start">${prod.name}</td>
					<td>${prod.categoryType}</td>
					<td>&#8377;${prod.price}</td>
					<td>${prod.quantity}</td>
					<td>${prod.discount}%</td>
					<td><a href="updateProduct?id=${prod.id}" role="button"
						class="btn btn-secondary">Update</a>&emsp;<a
						href="deleteProduct?id=${prod.id}" class="btn btn-danger"
						role="button">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>

