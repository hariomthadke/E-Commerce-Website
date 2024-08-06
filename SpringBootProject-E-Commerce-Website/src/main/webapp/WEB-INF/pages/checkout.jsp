<%@ page isELIgnored="false" import="java.util.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Checkout</title>
<%@include file="/Components/common_css_js.jsp"%>
</head>
<body>
	<!--navbar -->
	<%@include file="/Components/navbar.jsp"%>

	<div class="container mt-5" style="font-size: 17px;">
		<div class="row">

			<!-- left column -->
			<div class="col-md-8">
				<div class="card">
					<div class="container px-3 py-3">
						<div class="card">
							<div class="container-fluid text-white"
								style="background-color: #389aeb;">
								<h4>Delivery Address</h4>
							</div>
						</div>
						<div class="mt-3 mb-3">
							<h5>${user.name}&nbsp; ${user.mobileNo}</h5>
							${user.address} , ${user.city}<br> ${user.city}:
							${user.pincode} <br>
							<div class="text-end">
								<button type="button" class="btn btn-outline-primary"
									data-bs-toggle="modal" data-bs-target="#exampleModal">
									Change Address</button>
							</div>
						</div>
						<hr>
						<div class="card">
							<div class="container-fluid text-white"
								style="background-color: #389aeb;">
								<h4>Payment Options</h4>
							</div>
						</div>
						<form action="placeOrder?op=${from}" method="post">
							<div class="form-check mt-2">
								<input class="form-check-input" type="radio" name="paymentMode"
									value="Card Payment" required><label
									class="form-check-label">Credit /Debit /ATM card</label><br>
								<div class="mb-3">

									<input class="form-control mt-3" type="number"
										placeholder="Enter card number" name="cardno">
									<div class="row gx-5">
										<div class="col mt-3">
											<input class="form-control" type="number"
												placeholder="Enter CVV" name="cvv">
										</div>
										<div class="col mt-3">
											<input class="form-control" type="text"
												placeholder="Valid through i.e '07/23'">
										</div>
									</div>
									<input class="form-control mt-3" type="text"
										placeholder="Enter card holder name" name="name">
								</div>
								<input class="form-check-input" type="radio" name="paymentMode"
									value="Cash on Delivery"><label
									class="form-check-label">Cash on Delivery</label>
							</div>
							<div class="text-end">
								<button type="submit"
									class="btn btn-lg btn-outline-primary mt-3">Place
									Order</button>
							</div>
						</form>
					</div>
				</div>
			</div>
			<!-- end of column -->

			<!-- right column -->
			<div class="col-md-4">
				<div class="card">
					<div class="container px-3 py-3">
						<h4>Price Details</h4>
						<hr>
						<c:choose>
							<c:when test="${from.equals('cart')}">
								<table class="table table-borderless">
									<tr>
										<td>Total Item</td>
										<td>${totalItems}</td>
									</tr>
									<tr>
										<td>Total Quantity</td>
										<td>${totalProducts}</td>
									<tr>
										<td>Total Price</td>
										<td>&#8377; ${totalPrice}</td>
									</tr>
									<tr>
										<td>Delivery Charges</td>
										<td>&#8377; 40</td>
									</tr>
									<tr>
										<td>Packaging Charges</td>
										<td>&#8377; 29</td>
									</tr>
									<tr>
										<td><h5>Amount Payable :</h5></td>
										<td><h5>&#8377; ${totalPrice +69}</h5></td>
									</tr>
								</table>
							</c:when>
							<c:otherwise>

								<table class="table table-borderless">
									<tr>
										<td>Total Item</td>
										<td>1</td>
									</tr>
									<tr>
										<td>Quantity</td>
										<td>${quantity}</td>
									<tr>
										<td>Total Price</td>
										<td>&#8377; ${productPrice}</td>
									</tr>
									<tr>
										<td>Delivery Charges</td>
										<td>&#8377; 40</td>
									</tr>
									<tr>
										<td>Packaging Charges</td>
										<td>&#8377; 29</td>
									</tr>
									<tr>
										<td><h5>Amount Payable :</h5></td>
										<td><h5>&#8377; ${productPrice*quantity +69}</h5></td>
									</tr>
								</table>

							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			<!-- end of column -->
		</div>
	</div>


	<!--Change Address Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">Change
						Address</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<form action="UpdateUserServlet" method="post">
					<input type="hidden" name="operation" value="changeAddress">
					<div class="modal-body mx-3">
						<div class="mt-2">
							<label class="form-label fw-bold">Address</label>
							<textarea name="user_address" rows="3"
								placeholder="Enter Address(Area and Street))"
								class="form-control" required></textarea>
						</div>
						<div class="mt-2">
							<label class="form-label fw-bold">City</label> <input
								class="form-control" type="text" name="city"
								placeholder="City/District/Town" required>
						</div>
						<div class="mt-2">
							<label class="form-label fw-bold">Pincode</label> <input
								class="form-control" type="number" name="pincode"
								placeholder="Pincode" maxlength="6" required>
						</div>
						<div class="mt-2">
							<label class="form-label fw-bold">State</label> <select
								name="state" class="form-select">
								<option selected>--Select State--</option>
								<option value="Andaman &amp; Nicobar Islands">Andaman
									&amp; Nicobar Islands</option>
								<option value="Andhra Pradesh">Andhra Pradesh</option>
								<option value="Arunachal Pradesh">Arunachal Pradesh</option>
								<option value="Assam">Assam</option>
								<option value="Bihar">Bihar</option>
								<option value="Chandigarh">Chandigarh</option>
								<option value="Chhattisgarh">Chhattisgarh</option>
								<option value="Dadra &amp; Nagar Haveli &amp; Daman &amp; Diu">Dadra
									&amp; Nagar Haveli &amp; Daman &amp; Diu</option>
								<option value="Delhi">Delhi</option>
								<option value="Goa">Goa</option>
								<option value="Gujarat">Gujarat</option>
								<option value="Haryana">Haryana</option>
								<option value="Himachal Pradesh">Himachal Pradesh</option>
								<option value="Jammu &amp; Kashmir">Jammu &amp; Kashmir</option>
								<option value="Jharkhand">Jharkhand</option>
								<option value="Karnataka">Karnataka</option>
								<option value="Kerala">Kerala</option>
								<option value="Ladakh">Ladakh</option>
								<option value="Lakshadweep">Lakshadweep</option>
								<option value="Madhya Pradesh">Madhya Pradesh</option>
								<option value="Maharashtra">Maharashtra</option>
								<option value="Manipur">Manipur</option>
								<option value="Meghalaya">Meghalaya</option>
								<option value="Mizoram">Mizoram</option>
								<option value="Nagaland">Nagaland</option>
								<option value="Odisha">Odisha</option>
								<option value="Puducherry">Puducherry</option>
								<option value="Punjab">Punjab</option>
								<option value="Rajasthan">Rajasthan</option>
								<option value="Sikkim">Sikkim</option>
								<option value="Tamil Nadu">Tamil Nadu</option>
								<option value="Telangana">Telangana</option>
								<option value="Tripura">Tripura</option>
								<option value="Uttarakhand">Uttarakhand</option>
								<option value="Uttar Pradesh">Uttar Pradesh</option>
								<option value="West Bengal">West Bengal</option>
							</select>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Save</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- end modal -->

</body>
</html>