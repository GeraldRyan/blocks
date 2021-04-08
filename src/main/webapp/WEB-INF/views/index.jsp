<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Index Page</title>
</head>
<body>
	<nav>
		<c:if test="${isloggedin == true }">
			<a href="./logout">Logout</a>
		</c:if>
		<c:if test="${isloggedin == false}">
			<a href="./login">Login</a>

			<a href="./register">Register</a>
		</c:if>

	</nav>
	<c:if test="${isloggedin == false }">
		<h1>You are not logged in.</h1>
		<c:if test="${failed == true}">
			<p>${msg}</p>
		</c:if>
		<!-- 		<a href="./login">Login</a>
		<br> -->
<!-- 		<a href="./register">Register</a>
		<br> -->
		<br>
		<a href="./blockchain">Explore our version of the blockchain</a>
		<br>
	</c:if>


	<c:if test="${isloggedin == true }">
		<h1>Welcome to the blockchain ${user.getUsername()}</h1>

		<a href="./blockchain">See our version of the blockchain</a>
		<br>
		<a href="./blockchain/mine">Mine Block</a>
		<br>
		<a href="./wallet/">Your wallet</a>
		<br>
		<a href="./wallet/transact">Transact on the blockchain</a>
		<br>
<!-- 		<br>
		<a href="./logout">Logout</a> -->

	</c:if>
</body>
</html>