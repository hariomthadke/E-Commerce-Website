<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Categories</title>
<%@include file="/Components/common_css_js.jsp"%>
</head>
<body>
	<!--navbar -->
	<%@include file="/Components/navbar.jsp"%>

	<!-- Category -->
	<div class="container mt-3">
		<%@include file="/Components/alert_message.jsp"%>
		<table class="table table-hover">
			<tr class="table-primary text-center" style="font-size: 20px;">
				<th>Image</th>
				<th>Category Name</th>
				<th>Action</th>
			</tr>
			<c:forEach var="cat" items="${categoryList}">
				<tr class="text-center">
					<td><img src="uploads/${cat.filePath}"
						style="width: 60px; height: 60px; width: auto;"></td>
					<td>${cat.name}</td>
					<td><a href="updateCategory?id=${cat.id}" role="button"
						class="btn btn-secondary">Update</a>&emsp;<a
						href="deleteCategory?id=${cat.id}" class="btn btn-danger"
						role="button">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>
