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
<title>Choose color!</title>
</head>
<body bgcolor="<%=bgColor%>">
	<a href="setcolor?a=WHITE" target="_self">WHITE</a>
	<a href="setcolor?a=RED" target="_self">RED</a>
	<a href="setcolor?a=GREEN" target="_self">GREEN</a>
	<a href="setcolor?a=CYAN" target="_self"> CYAN</a>
</body>
</html>
