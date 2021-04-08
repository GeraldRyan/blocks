<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register here</title>
</head>
<body>
	<form:form method="post" modelAttribute="user" >
		<table align="center">
			<tr>
				<td><label>Choose a username</label></td>
				<td><form:input path="username" /></td>
				<td><form:errors path="username" /></td>
			</tr>
			<tr>
				<td><label>Choose a strong password</label></td>
				<td><form:input path="password" /></td>
				<td><form:errors path="password" /></td>
			</tr>
			<tr>
				<td><label>Enter your Email</label></td>
				<td><form:input path="email" /></td>
				<td><form:errors path="email" /></td>
			</tr>

			<tr>
				<td><label>Enter a hint or question</label></td>
				<td><form:input path="hint" /></td>
				<td><form:errors path="hint" /></td>
			</tr>
			<tr>
				<td><label>Enter the answer</label></td>
				<td><form:input path="answer" /></td>
				<td><form:errors path="answer" /></td>
			</tr>
			<tr>
				<td><form:button id="register" name="register">Register</form:button>
				</td>
			</tr>
			<tr>
				<td><a href="../">Home</a></td>
			</tr>

		</table>
	</form:form>
</body>
</html>