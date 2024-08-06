<%@ page isELIgnored="false" import="java.util.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container px-3 py-3">

	<c:choose>
		<c:when test="${wishListProducts.size()==0 }">
			<div class="container mt-5 mb-5 text-center">
				<img src="Images/wishlist.png" style="max-width: 200px;"
					class="img-fluid">
				<h4 class="mt-3">Empty Wishlist</h4>
				You have no items in your wishlist. Start adding!
			</div>
		</c:when>

		<c:otherwise>
			<h4>My Wishlist (${wishListProducts.size()})</h4>
			<hr>
			<div class="container">
				<table class="table table-hover">
					<thead>
						<tr class="table-primary text-center" style="font-size: 18px;">
							<th>Item</th>
							<th>Item Name</th>
							<th>Price</th>
							<th>Remove</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="prod" items="${wishListProducts}">
							<tr class="text-center">
								<td><a href="viewProduct?pid=${prod.id}"
									style="text-decoration: none;"> <img
										src="uploads/${prod.image}"
										style="width: 50px; height: 50px; width: auto;">
								</a></td>
								<td class="text-center"><a
									href="viewProduct?pid=${prod.id}"
									style="text-decoration: none; color: inherit;">
										${prod.name}</a></td>
								<td>&#8377;${prod.priceAfterDiscount}</td>
								<td><a
									href="wishlist?uid=${activeUser.id}&pid=${prod.id}&op=remove"
									class="btn btn-secondary" role="button">Remove</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:otherwise>
	</c:choose>
</div>
