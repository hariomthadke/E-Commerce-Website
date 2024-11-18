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

.login-with-google-btn {
	transition: background-color .3s, box-shadow .3s;
	padding: 12px 16px 12px 42px;
	border: none;
	border-radius: 3px;
	box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 1px 1px rgba(0, 0, 0, .25);
	color: #757575;
	font-size: 14px;
	font-weight: 500;
	font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
		Oxygen, Ubuntu, Cantarell, "Fira Sans", "Droid Sans", "Helvetica Neue",
		sans-serif;
	background-image:
		url(data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTgiIGhlaWdodD0iMTgiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PGcgZmlsbD0ibm9uZSIgZmlsbC1ydWxlPSJldmVub2RkIj48cGF0aCBkPSJNMTcuNiA5LjJsLS4xLTEuOEg5djMuNGg0LjhDMTMuNiAxMiAxMyAxMyAxMiAxMy42djIuMmgzYTguOCA4LjggMCAwIDAgMi42LTYuNnoiIGZpbGw9IiM0Mjg1RjQiIGZpbGwtcnVsZT0ibm9uemVybyIvPjxwYXRoIGQ9Ik05IDE4YzIuNCAwIDQuNS0uOCA2LTIuMmwtMy0yLjJhNS40IDUuNCAwIDAgMS04LTIuOUgxVjEzYTkgOSAwIDAgMCA4IDV6IiBmaWxsPSIjMzRBODUzIiBmaWxsLXJ1bGU9Im5vbnplcm8iLz48cGF0aCBkPSJNNCAxMC43YTUuNCA1LjQgMCAwIDEgMC0zLjRWNUgxYTkgOSAwIDAgMCAwIDhsMy0yLjN6IiBmaWxsPSIjRkJCQzA1IiBmaWxsLXJ1bGU9Im5vbnplcm8iLz48cGF0aCBkPSJNOSAzLjZjMS4zIDAgMi41LjQgMy40IDEuM0wxNSAyLjNBOSA5IDAgMCAwIDEgNWwzIDIuNGE1LjQgNS40IDAgMCAxIDUtMy43eiIgZmlsbD0iI0VBNDMzNSIgZmlsbC1ydWxlPSJub256ZXJvIi8+PHBhdGggZD0iTTAgMGgxOHYxOEgweiIvPjwvZz48L3N2Zz4=);
	background-color: white;
	background-repeat: no-repeat;
	background-position: 12px 11px;
	&:
	hover
	{
	box-shadow
	:
	0
	-1px
	0
	rgba(
	0
	,
	0
	,
	0
	,
	.04
	)
	,
	0
	2px
	4px
	rgba(
	0
	,
	0
	,
	0
	,
	.25
	);
}

&
:active {
	background-color: #eeeeee;
}

&
:focus {
	outline: none;
	box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25), 0
		0 0 3px #c8dafc;
}

&
:disabled {
	filter: grayscale(100%);
	background-color: #ebebeb;
	box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 1px 1px rgba(0, 0, 0, .25);
	cursor: not-allowed;
}
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
								<a href="/forgotPassword" style="text-decoration: none">Forgot
									Password?</a>
							</h6>
							<h6>
								Don't have an account?<a href="register"
									style="text-decoration: none"> Sign Up</a>
							</h6>
							<a href="/oauth2/authorization/google"
								style="text-decoration: none;">
								<button type="button" class="login-with-google-btn">
									Sign in with Google</button>
							</a>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
</body>
</html>