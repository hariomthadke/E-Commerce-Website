<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" import="java.util.*"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="frm"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Product</title>
<%@include file="/Components/common_css_js.jsp"%>
</head>
<body>
	<!--navbar -->
	<%@include file="/Components/navbar.jsp"%>

	<!-- update product -->
	<div class="container mt-3">
		<div class="row ">
			<div class="col">
				<div class="card">
					<div class="card-header text-center">
						<h3>Edit Product</h3>
					</div>

					<frm:form action="updateProduct" method="post"
						modelAttribute="product" enctype="multipart/form-data">
						<div class="card-body">
							<input type="hidden" name="operation" value="updateProduct">
							<div class="row">
								<div>
									<input type="hidden" name="id" value="${prod.id}">
								</div>
								<div class="col-md-6 mb-2">
									<label class="form-label"><b>Product Name</b></label>

									<frm:input type="text" path="name" class="form-control" />
								</div>
								<div class="col-md-6 mb-2">
									<label class="form-label"><b>Unit Price</b></label>
									<frm:input class="form-control" type="number" path="price"
										required="required" />

								</div>
							</div>
							<div class="mb-2">
								<label class="form-label"><b>Product Description</b></label>
								<frm:textarea class="form-control" path="description" rows="3"></frm:textarea>
							</div>
							<div class="row">
								<div class="col-md-6 mb-2">
									<label class="form-label"><b>Product Quantity</b></label>
									<frm:input type="number" path="quantity" class="form-control" />
								</div>
								<div class="col-md-6 mb-2">
									<label class="form-label"><b>Discount Percentage</b></label>
									<frm:input type="number" path="discount" onblur="validate()"
										class="form-control" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 mb-2">
									<label class="form-label"><b>Product Image</b></label><input
										class="form-control" type="file" name="filePath">
								</div>
								<div class="col-md-6 mb-2">
									<label class="form-label"><b>Select Category Type</b></label> <select
										name="categoryType" class="form-control">
										<option value="${prod.categoryType}">--Select
											Category--</option>

										<c:forEach var="cat" items="${categoryList}">
											<option value="${cat.id}">${cat.name}</option>
										</c:forEach>


									</select>
									<frm:input type="hidden" path="categoryType" />
								</div>
							</div>
							<div class="mb-3">
								<label class="form-label"><b>Uploaded Image:&nbsp;</b></label>${prod.image}
								&emsp;<img src="uploads/${prod.image}"
									style="width: 80px; height: 80px; width: auto;"> <input
									type="hidden" name="image" value="${prod.image}">
							</div>
						</div>
						<div class="card-footer text-center">
							<button type="submit" class="btn btn-lg btn-primary me-3">Update</button>
						</div>
					</frm:form>
				</div>
			</div>
		</div>
	</div>
	<!-- end -->

	<script type="text/javascript">
		function validate() {
			var dis = document.updateProductForm.discount.value;
			if (dis > 100 || dis < 0) {
				alert("Discount need tobe between 0-100 !");
				return false;
			}
		}
	</script>
</body>
</html>