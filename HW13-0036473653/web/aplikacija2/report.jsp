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
<title>OS usage</title>
</head>
<body bgcolor="<%=bgColor%>">
<h1>OS Usage</h1>
<p>Here are the results of OS usage in survey that we completed.<p>
<img src='reportImage'>
</body>
</html>
