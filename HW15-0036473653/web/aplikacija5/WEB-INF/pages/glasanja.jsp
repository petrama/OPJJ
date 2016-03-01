<%@ page import="java.lang.String,java.lang.Integer"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>




<html>
<head>
<title>Odabir glasanja</title>
</head>
<body>
	<p>Odaberite jedno od glasanja:</p>
	<ol>
		<c:forEach var="g" items="${glasanja}">
			<li><a href="/aplikacija5/servleti/glasanje?pollID=${g.id}">${g.title}</a></li>

		</c:forEach>


	</ol>
</body>
</html>