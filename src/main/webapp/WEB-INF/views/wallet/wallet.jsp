<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.gerald.ryan.blocks.entity.Blockchain"%>


<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title></title>
<link rel="stylesheet" href="./blockchain.css" type="text/css"></link>
<link href="https://unpkg.com/tailwindcss@^1.0/dist/tailwind.min.css"
	rel="stylesheet">
<style type="text/css">
body {
	background-color: #3D3D3D;
	color: white;
}

nav {
	display: flex;
	justify-content: space-around;
	background-color: darkgreen;
	color: white;
	font-weight: bold;
}

.card {
	display: flex;
	flex-direction: column;
}

.card p {
	font-size: 1.5rem;
}

h1 {
	font-size: 4rem;
}

h3 {
	font-size: 2rem;
}
</style>
<script type="text/javascript">
	function cpytxt() {
		var copyText = document.getElementById("address");
		copyText.select();
		copyText.setSelectionRange(0, 99999);
		alert("Text copied");
		document.execCommand("copy");
	}
</script>
</head>
<body>
	<nav>
		<a href="/blocks">Home</a>
	</nav>
	<h1>Your Wallet</h1>

	<div class="card">
		<div class="inline">
			<p class="inline">
				Address: <span id="address">${wallet.getAddress() }</span>
			</p>
			<button onclick="cpytxt()"
				class=" inline shadow bg-gray-500
				ml-4 px-4 text-whitehover:bg-gray-400">copy
				to clipboard</button>
		</div>
		<p>Balance: ${wallet.getBalance() }</p>
		<p>public key: ${wallet.getPublickey().toString() }</p>
	</div>
	<h3>
		<a class="text-green-700" href="./transact/">Make a transaction</a>
	</h3>
	<br>
	<br>
	<br>
	<br>
	<br>

	<br>
	<p>Or send a post request to (/wallet/transact") in the following
		JSON format</p>
	<p>{ "address": "foo", "amount": 500 }</p>


	<!-- 	<div class="flex bg-green-200 p-4">
		<div class="mr-4">
			<div
				class="h-10 w-10 text-white bg-green-600 rounded-full flex justify-center items-center">
				<i class="material-icons">done</i>
			</div>
		</div>
		<div class="flex justify-between w-full">
			<div class="text-green-600">
				<p class="mb-2 font-bold">Succes alert</p>
				<p class="text-xs">description text</p>
			</div>
			<div class="text-sm text-gray-500">
				<span>x</span>
			</div>
		</div>
	</div>
 -->
</body>


</html>