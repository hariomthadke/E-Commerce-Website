<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View User's</title>
<%@include file="/Components/common_css_js.jsp"%>
</head>
<body>
	<!--navbar -->
	<%@include file="/Components/navbar.jsp"%>

	<div class="container-fluid px-5 py-3">
		<table class="table table-hover">
			<tr class="text-center table-primary" style="font-size: 18px;">
				<th>User Name</th>
				<th>Email</th>
				<th>Phone No.</th>
				<th>Gender</th>
				<th>Address</th>
				<th>Register Date</th>
				<th>Action</th>
			</tr>
			<c:forEach var="user" items="${usersList}">
				<tr class="text-center">
					<td>${user.name}</td>
					<td>${user.email}</td>
					<td>${user.mobileNo}</td>
					<td>${user.gender}</td>
					<td>${user.address}</td>
					<td>${user.registrationDate }</td>
					<td><a href="deleteUser?id=${user.id}" role="button"
						class="btn btn-danger">Remove</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>