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
<title>My application</title>
</head>
<body bgcolor="<%=bgColor%>">
	<a href="color.jsp" target="_blank">Background color chooser</a>
	<br>
	<a href="squares?a=100&b=120" target="_blank">Squares form 100 to 120</a>
	<br>
	<a href="stories/funny.jsp" target="_blank">Funny story</a>
	<br>
	<a href="report.jsp" target="_blank">OS ussage</a>
	<br>
	<a href="powers?a=1&b=100&n=3" target="_blank">Generate xls file!</a>
	<br>
	<a href="appinfo.jsp" target="_blank">How long is app running?</a>
	<br>
	<a href="glasanje" target="_blank">Vote for your favourite band</a>
</body>
</html>
