<%@ page
	import="java.lang.String,java.lang.Integer"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<title>"${glasanje.title}"</title>
</head>
<body>
<p>${glasanje.message}</p>
<ol>
<c:forEach var="opcija" items="${opcije}">
<li><a href="glasanje-glasaj?id=${opcija.id}&pollID=${opcija.pollId}">${opcija.optionTitle}</a></li>

</c:forEach>


</ol>
</body>
</html>