<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Index Page</title>
<link href="https://unpkg.com/tailwindcss@^1.0/dist/tailwind.min.css"
	rel="stylesheet">
</head>
<body class="bg-gray-800 text-white">
	<nav>
		<c:if test="${isloggedin == true }">
			<a href="./logout">Logout</a>
		</c:if>
		<c:if test="${isloggedin == false}">
			<nav class="text-4xl flex justify-around">
				<a class="" href="./login">Login</a> <a class="" href="./register">Register</a>
			</nav>
		</c:if>

	</nav>
	<c:if test="${isloggedin == false }">
		<div class="m-auto text-center">
			<h1 class="text-2xl m-auto text-center text-red-700">You are not logged in. </h1>
			<c:if test="${failed == true}">
				<p class="text-red-300">${msg}</p>
			</c:if>
			<div class="text-6xl">BeanCoin</div>
			<br> <a class="mt-48 text-6xl" href="./blockchain">Explore
				our version of the blockchain</a> <br>
	</c:if>
	</div>
	<img class="m-auto rounded-full" alt="coffee bean close up"
		src="./resources/bean.png">


	<c:if test="${isloggedin == true }">
		<h1>Welcome to the blockchain ${user.getUsername()}</h1>

		<a class="text-4xl text-green-700" href="./blockchain">See our version of the blockchain</a>
		<br>
		<a class="text-4xl text-green-700" href="./blockchain/mine">Mine Block</a>
		<br>
		<a class="text-4xl text-green-700" href="./wallet/">Your wallet</a>
		<br>
		<a class="text-4xl text-green-700" href="./wallet/transact">Transact on the blockchain</a>
		<br>
		<!-- 		<br>
		<a href="./logout">Logout</a> -->

	</c:if>
</body>
</html>