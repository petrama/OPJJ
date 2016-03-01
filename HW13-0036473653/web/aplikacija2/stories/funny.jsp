<%@ page import="java.lang.String,java.util.Random"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String bgColor = (String) request.getSession().getAttribute(
			"pickedBgCol");
	if (bgColor == null)
		bgColor = "WHITE";
	Random random = new Random();
	String fontCol = "rgb(" + random.nextInt(255) + ","
			+ random.nextInt(200) + "," + random.nextInt(150) + ")";
%>
<html>
<head>
<title>Funny story</title>
</head>
<body bgcolor="<%=bgColor%>">
	<h1>
		<font color="<%=fontCol%>">Very funny story</font>
	</h1>

	<font color="<%=fontCol%>">
		<p>
			Funny story
			</p1>
			This supposed to be funny.
	</font>
</body>
</html>