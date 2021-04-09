<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Index Page</title>
</head>
<body>
	<h1>Transact on the blockchain</h1>
	<a href="../..">Home</a>
	<br>
	<a href="../../blockchain/">See our version of the blockchain</a>
	<br>


	<form action="../transaction">
		Address to send money to <input type="text" name="address"><br>
		<br> Amount to send<input type="text" name="amount"><br>
		<br> <input type="submit" name="submit">
	</form>

</body>
</html>