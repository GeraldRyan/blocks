<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Transaction Pool</h1>
	<h2>Get as JSON by submitting POST request to this site
		{"transactionpool":"gimme"}</h2>


	<table border="2">
		<tr>
			<td>Sender Beg Balance</td>
			<td>Sender Address</td>
			<td>Transaction ID</td>
			<td>Timestamp</td>
			<td>Sender End Balance</td>
		</tr>

		<c:forEach items="${transactionpoollist}" var="t">
			<tr>
				<td>${t.getInput().get("amount")}</td>
				<td>${t.getInput().get("address")}</td>
				<td>${t.getInput().get("timestamp")}</td>
				<td>${t.getUuid()}</td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<p>TODO: Hard to display other important data columns for two
		reasons--- one transaction might have multiple recipients. (Would just
		have to split using plain old java function. Wouldn't technically
		represent a "transaction" object as processed, but a more human
		readable rendering of the same info). Also, the lookup key values are
		also often not known in advance. They represent wallet addresses,
		which can be iterated through and gotten but otherwise are not
		constant.</p>
	<p>In other words- displaying values whose lookup keys are
		constants, and which relate to one other value</p>




	<%-- <h3>${transactionList} }</h3> --%>

</body>
</html>