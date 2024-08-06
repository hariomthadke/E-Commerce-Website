
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" import="java.util.*"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="frm"%>
<style>
label {
	font-weight: bold;
}
</style>
<div class="container px-3 py-3">
	<h3>Personal Information</h3>

	<frm:form action="updateUserDetails" method="post"
		modelAttribute="user" enctype="multipart/form-data">
		<input type="hidden" name="operation" value="updateUser">
		<div class="row">
			<div>
				<frm:input type="hidden" path="id" />
			</div>
			<div class="col-md-6 mt-2">
				<label class="form-label">Your name</label>
				<frm:input type="text" path="name" class="form-control"
					placeholder="First and last name" />
			</div>
			<div class="col-md-6 mt-2">
				<label class="form-label">Email</label>
				<frm:input type="email" path="email" placeholder="Email address"
					class="form-control" readonly="readonly" id="emailField" />
			</div>
			<script>
				document.getElementById('emailField').readOnly = true;
			</script>
		</div>
		<div class="row">
			<div class="col-md-6 mt-2">
				<label class="form-label">Mobile number</label>
				<frm:input type="number" path="mobileNo" placeholder="Mobile number"
					class="form-control" />
			</div>
			<div class="col-md-6 mt-5">
				<label class="form-label pe-3">Gender</label>

				<c:choose>
					<c:when test="${user.gender.equalsIgnoreCase('male')}">
						<input class="form-check-input" type="radio" name="gender"
							value="Male" checked>
						<span class="form-check-label pe-3 ps-1"> Male </span>
						<input class="form-check-input" type="radio" name="gender"
							value="Female">
						<span class="form-check-label ps-1"> Female </span>
					</c:when>

					<c:otherwise>
						<input class="form-check-input" type="radio" name="gender"
							value="Male">
						<span class="form-check-label pe-3 ps-1"> Male </span>
						<input class="form-check-input" type="radio" name="gender"
							value="Female" checked>
						<span class="form-check-label ps-1"> Female </span>

					</c:otherwise>
				</c:choose>

			</div>
		</div>
		<div class="mt-2">
			<label class="form-label">Address</label>
			<frm:input type="text" path="address"
				placeholder="Enter Address(Area and Street))" class="form-control" />
		</div>
		<div class="row">
			<div class="col-md-6 mt-2">
				<label class="form-label">City</label>
				<frm:input class="form-control" type="text" path="city"
					placeholder="City/District/Town" />
			</div>
			<div class="col-md-6 mt-2">
				<label class="form-label">Pincode</label>
				<frm:input class="form-control" type="number" path="pincode"
					placeholder="Pincode" maxlength="6" />
			</div>
		</div>
		<div class="row mt-2">
			<label class="form-label">State</label>
			<div class="input-group mb-3">
				<select name="state" id="state-list" class="form-select">
					<option value="${user.state}">${user.state}</option>
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
		<div id="submit-btn" class="container text-center mt-3">
			<button type="submit" class="btn btn-outline-primary me-3">Update</button>
			<button type="reset" class="btn btn-outline-primary">Reset</button>
		</div>
	</frm:form>
</div>

















