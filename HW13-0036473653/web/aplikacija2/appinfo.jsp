<%@ page import="java.lang.String,java.lang.Long"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String bgColor = (String) request.getSession().getAttribute(
			"pickedBgCol");
	if (bgColor == null)
		bgColor = "WHITE";
	Long time = System.currentTimeMillis()
			- ((Long) session.getServletContext().getAttribute("start"));

	Long years = time / (3600000 * 24 * 365);
	Long months = time / (3600000 * 24 * 30);
	Long days = time / (3600000 * 24);
	Long hours = (time / 3600000) % 24;
	Long minutes = (time / 60000) % 60;
	Long seconds = (time / 1000) % 60;
	Long mils = time % 1000;

	String vrijeme = (years > 0 ? " Years: " + years : "")
			+ (months > 0 ? "	Months:	" + months : "")
			+ (days > 0 ? "	Days:	" + days : "")
			+ (hours > 0 ? "	Hourshours:	" + hours : "")
			+ (minutes > 0 ? "	Minutes:	" + minutes : "")
			+ (seconds > 0 ? "	Seconds:	" + seconds : "")
			+ (mils > 0 ? "	Miliseconds:	" + mils : "");
%>
<html>
<head>
<title>Application running time</title>
</head>

<body bgcolor="<%=bgColor%>">
	<h1>Application is running for: </h1>
	<p><%=vrijeme%></p>
</body>
</html>
