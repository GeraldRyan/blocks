<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register he re</title>
</head>
<body>
	<form:form action="./publish" method="post" modelAttribute="message">
		<div>
			<label>Message</label>
			<form:input path="message" />
			<form:errors path="message" />
		</div>
		<div>
			<label>Channel</label>
			<form:input path="channel" />
			<form:errors path="channel" />
		</div>
		<div>
			<input type="submit" value="publish" />
		</div>
	</form:form>

	<p>${display}</p>


<%-- 	<form action="./publish" method="post">
		<div>
			<label>Channel to subscribe to</label> <input name="channel"/>

		</div>
		<div>
			<input type="submit" value="subscribe" />
		</div>
	</form> --%>

</body>
</html>