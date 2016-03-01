<%@page import="java.lang.String"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<%Long id=(Long) request.getSession().getAttribute("current.user.id");
String poruka = (String) request.getAttribute("poruka");
String ime=(String) request.getSession().getAttribute("current.user.fn");
String prezime=(String) request.getSession().getAttribute("current.user.ln");
%>

<html>
<head>
<title>Blogovi</title>
</head>

<style type="text/css">
.greska {
	font-family: calibri;
	font-weight: italics;
	font-size: 0.8em;
	color: #FF00DD;
}
</style>

<body>

	
<c:choose>
		<c:when test="<%=id==null%>">
			<h1>Anonimni korisnik</h1>
			
		</c:when>
		<c:otherwise>
		<h1>Ej korisniče <%=ime%> <%=prezime %> </h1>
		<a href="logout">Logout</a>
		</c:otherwise>
</c:choose>
	
	<c:choose>
		<c:when test="<%=id==null%>">
			<h2>Ako ste postojeći korinsik ulogirajte se</h2>
			<c:choose>
		<c:when test="<%=poruka != null%>">
			<div class="greska">
				<h2><%=poruka%></h2>
			</div>
		</c:when>
	</c:choose>

	<form action=login method=post>
		<table>
			<tr>
				<td align="right"><em>Korisničko ime</em></td>
				<td><input type="text" name="nick" size="20"></td>
			</tr>

			<tr>
				<td align="right"><em>Lozinka</em></td>
				<td><input type="password" name="password" size="20"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" name="metoda" value="login"></td>
			</tr>

		</table>



	</form>
	<h2>Ako nemate račun slobodno se registrirajte</h2>
	
	<a href="register">Registracija</a>
			
		</c:when>
		
</c:choose>

<h3>Registrirani korisnici</h3>
	<c:forEach var="r" items="${registrirani}">
	<li><a href="author/${r.nick}">${r.nick}</a></li>
			
					
				</c:forEach>
	

	
	
</body>
</html>
