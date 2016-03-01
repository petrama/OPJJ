<%@ page import="java.lang.String"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String bgColor = (String) request.getSession().getAttribute(
			"pickedBgCol");
	if (bgColor == null)
		bgColor = "WHITE";
%>
<html>
<head>
<title>Squares</title>
</head>
<body bgcolor="<%=bgColor%>">
	<h1>Squares</h1>
	<table border="1" cellspacing="0">
		<c:forEach var="r" items="${parovi}">
			<tr>
			
				<td>${r.broj}</td>
				<td>${r.vrijednost}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>