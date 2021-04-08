<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ page import="com.gerald.ryan.blocks.entity.Blockchain"%>
<%@ page import="com.gerald.ryan.blocks.entity.Block"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h1>Welcome to our Node</h1>
	<h2>The dwarves are hard at work mining your Block</h2>
	<h3>
		<%=request.getAttribute("foo")%>
		<%
		Blockchain bc = (Blockchain) request.getAttribute("blockchain");
		Block last_block = bc.getLastBlock();
		%>
		<%=bc.toJSONtheChain()%>
	</h3>

	<h4>Most recent block</h4>
	<%=bc.getLastBlock().toJSONtheBlock()%>

	<h3>${afb}</h3>
</body>
</html>