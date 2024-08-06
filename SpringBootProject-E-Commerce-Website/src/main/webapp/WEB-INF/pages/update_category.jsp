
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="frm"%>
<%@ page isELIgnored="false" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Category</title>
<%@include file="/Components/common_css_js.jsp"%>
</head>
<body>
	<!--navbar -->
	<%@include file="/Components/navbar.jsp"%>

	<!-- update category -->

	<div class="container mt-5">
		<div class="row row-cols-1 row-cols-md-1 offset-md-2">
			<div class="col">
				<div class="card w-75">
					<div class="card-header text-center">
						<h3>Edit Category</h3>
					</div>

					<frm:form action="updateCategory" method="post"
						modelAttribute="category" enctype="multipart/form-data">
						<div class="card-body">
							<input type="hidden" name="operation" value="updateCategory">
							<div>
								<input type="hidden" name="id" value="${cat.id}">
							</div>
							<div class="mb-3">
								<label class="form-label"><b>Category Name</b></label>
								<frm:input type="text" class="form-control" path="name" />
							</div>
							<div class="mb-3">
								<label class="form-label"><b>Category Image</b></label>
								<!-- <input class="form-control" type="file" name="category_img"> -->
								<input class="form-control" type="file" name="file">
							</div>
							<div class="mb-3">
								<label class="form-label"><b>Uploaded Image:&nbsp;</b></label>${cat.filePath}&emsp;<img
									src="uploads/${cat.filePath}"
									style="width: 80px; height: 80px; width: auto;"> <input
									type="hidden" name="image" value="${cat.filePath}">
							</div>
						</div>
						<div class="card-footer text-center">
							<button type="submit" class="btn btn-lg btn-primary me-3">Update</button>
						</div>
						<!-- </form> -->
					</frm:form>
				</div>
			</div>
		</div>
	</div>
	<!-- end-->
</body>
</html>