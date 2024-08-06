<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<%@include file="/Components/common_css_js.jsp"%>
<style>
label {
	font-weight: bold;
}
</style>
</head>
<body>

	<!--navbar -->
	<%@include file="/Components/navbar.jsp"%>

	<div class="container-fluid">
		<div class="row mt-5">
			<div class="col-md-4 offset-md-4">
				<div class="card">
					<div class="card-body px-5">

						<div class="container text-center">
							<img src="Images/login.png" style="max-width: 100px;"
								class="img-fluid">
						</div>
						<h3 class="text-center">Sign-In</h3>
						<%@include file="/Components/alert_message.jsp"%>

						<!--login-form-->

						<form id="login-form" action="<c:url value='/login' />"
							method="post">

							<div class="mb-3">
								<label class="form-label">Email</label> <input type="email"
									name="username" placeholder="Email address"
									class="form-control" required>
							</div>
							<div class="mb-3">
								<label class="form-label">Password</label> <input
									type="password" name="password"
									placeholder="Enter your password" class="form-control" required>
							</div>
							<div id="login-btn" class="container text-center">
								<button type="submit" class="btn btn-outline-primary me-3">Login</button>
							</div>
						</form>
						<div class="mt-3 text-center">
							<h6>
								<a href="forgotPassword" style="text-decoration: none">Forgot
									Password?</a>
							</h6>
							<h6>
								Don't have an account?<a href="register"
									style="text-decoration: none"> Sign Up</a>
							</h6>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
</body>
</html>