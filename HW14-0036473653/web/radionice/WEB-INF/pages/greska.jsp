<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Pogreška</title>
<style type="text/css">
.greska {
	font-family: calibri;
	font-weight: italics;
	font-size: 0.8em;
	color: #FFAA11;
}
</style>
</head>

<body>
	<h1>Dogodila se pogreška</h1>

	
		<div class="greska">
			<h2><c:out value="${poruka}" /></h2>
		</div>
	
	<p>
		<a href="listaj">Povratak na početnu stranicu</a>
	</p>
</body>
</html>