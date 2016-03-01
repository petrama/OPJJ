<%@page import="hr.fer.zemris.web.radionice.korisnici.User"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<%User korisnik=(User) request.getSession().getAttribute("current.user"); %>

<html>
<head>
<title>Radionice</title>
</head>


<body>

	
<c:choose>
		<c:when test="<%=korisnik==null%>">
			<h1>Anonimni korisnik</h1>
			<a href="/radionice/login">Login</a>
		</c:when>
		<c:otherwise>
		<h1><%=korisnik.getIme()%>   <%=korisnik.getPrezime() %></h1>
		<a href="logout">Logout</a>
		</c:otherwise>
</c:choose>


	<h2>Lista postojećih radionica</h2>
	<c:choose>
		<c:when test="${radionice.isEmpty()}">
			<p>trenutno nema radionica</p>
		</c:when>
		<c:otherwise>
			<ol>

				<c:forEach var="r" items="${radionice}">
					<li>
					<c:out value="${r.naziv}" /> 
					<c:out value="${r.datum}" /> 
				
				<c:choose>
				<c:when test="<%=korisnik!=null%>">
				<a href="edit?id=<c:out value="${r.id}"/>">Uredi</a>
				</c:when>
				</c:choose>
					</li>
				</c:forEach>
			</ol>



		</c:otherwise>

	</c:choose>
	<p>    
	
	<c:choose>
			<c:when test="<%=korisnik!=null%>">
		<a href="new">Novi</a>
		</c:when>
		</c:choose>
</body>
</html>
