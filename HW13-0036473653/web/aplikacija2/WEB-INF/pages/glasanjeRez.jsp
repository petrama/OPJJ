<%@ page import="java.lang.String,java.lang.Integer,java.util.List,hr.fer.zemris.java.tecaj_13.servleti.GlasanjeRezultatiServlet.Result"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String bgColor = (String) request.getSession().getAttribute(
			"pickedBgCol");
	if (bgColor == null)
		bgColor = "WHITE";
	

	
	getServletContext().setAttribute("rezultati", request.getAttribute("rezultati"));
	getServletContext().setAttribute("pobjednici", request.getAttribute("pobjednici"));
%>

<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>
<body bgcolor="<%=bgColor%>">
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="r" items="${rezultati}">
			<tr>
				<td>${r.trio.bend}</td>
				<td>${r.glasovi}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<h2>Grafički prikaz rezultata</h2>
<img alt="Pie-chart" src="glasanje-grafika"/>


<h2>Rezultati u XLS formatu</h2>
<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>

<h2>Razno</h2>
<p>Primjeri pjesama pobjedničkih bendova:</p>
<ul>
<c:forEach var="r" items="${pobjednici}">
<li><a href="${r.trio.pjesma}" target="_blank">${r.trio.bend}</a></li>
</c:forEach>
</ul>

</body>
</html>