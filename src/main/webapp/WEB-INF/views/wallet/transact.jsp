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
	<a href="/blocks">Home</a>
	<br>
	<a href="/blocks/blockchain/">See our version of the blockchain</a>
	<br>


	<form action="../transaction">
		Address to send money to <input type="text" name="address"><br>
		<br> Amount to send<input type="text" name="amount"><br>
		<br> <input type="submit" name="submit">
	</form>

	<p>For Dev purposes, send a single transaction to a specific address by 
		POSTing {"address":"my address", "amount":"integer amount")} to this endpoint</p>
		
		<p>To create n bulk test transactions, send POST to  </p>
		
	<p>http://localhost:8080/blocks/wallet/transactt [note extra t]</p>


	<p>{"number"":[number of test transactions to make], "fromaddress":[your from address]}</p>
	<p>Amounts and recipients will always be random</p>
	<p>And both of these fields are optional in which case it defaults to random</p>


	<p>e.g. {number:7}</p>

</body>
</html>