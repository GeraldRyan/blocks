<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Welcome to the club ${user.getUsername() }</h1>

	<br>

	<h3>
		We created a <a href="./wallet/">Wallet</a> for you and initialized it
		with 1.000 beancoin. Enjoy
	</h3>
	<br>
	<br>
	<a href="./">Home</a>

</body>
</html>