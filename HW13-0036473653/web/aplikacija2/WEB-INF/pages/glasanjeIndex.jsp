<%@ page
	import="java.lang.String,java.lang.Integer"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String bgColor = (String) request.getSession().getAttribute(
			"pickedBgCol");
	if (bgColor == null)
		bgColor = "WHITE";
	Integer n=(Integer)request.getAttribute("brojBendova");

	session.setAttribute("n", n);
%>



<html>
<head>
<title>Voting</title>
</head>
<body bgcolor="<%=bgColor%>">
<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
<ol>
<c:forEach var="bend" items="${bendovi}">
<li><a href="glasanje-glasaj?id=${bend.id}&n=<%=n.toString()%>">${bend.bend}</li>

</c:forEach>


</ol>
</body>
</html>