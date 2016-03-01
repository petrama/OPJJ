<%@ page import="java.lang.String" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%Long id=(Long) request.getSession().getAttribute("current.user.id");
String poruka = (String) request.getAttribute("poruka");
String ime=(String) request.getSession().getAttribute("current.user.fn");
String prezime=(String) request.getSession().getAttribute("current.user.ln");
%>
<html>
<head>
<title>Post</title>

<style type="text/css">
.greska {
	font-family: fantasy;
	font-weight: italics;
	font-size: 0.8em;
	color: #FF0000;
}
</style>
</head>

<body>
<c:choose>
		<c:when test="<%=id==null%>">
			<h1>Anonimni korisnik</h1>
			
		</c:when>
		<c:otherwise>
		<h1>Ej korisniče <%=ime%> <%=prezime %> </h1>
		<a href="/aplikacija5/servleti/logout">Logout</a>
		</c:otherwise>
</c:choose>
	<h2>
		<c:choose>
			<c:when test="${zapisi.id.isEmpty()}">
					Dodavanje novog posta
				</c:when>
			<c:otherwise>
			Uređivanje postojećeg posta
			</c:otherwise>
		</c:choose>
	</h2>
	<form action="/aplikacija5/servleti/saveEntry" method="post">

		<input type="hidden" name="id" value="${zapisi.id}">
		<input type="hidden" name="nick" value="${nick}">

		<table align="left">
			<tr>
				<td align="right"><em>Naslov</em></td>
				<td><input type="text" name="title"
					value='<c:out value="${zapisi.title}"/>' size="50"> <c:if
						test="${zapisi.imaPogresku('title')}">
						<div class="greska">
							<c:out value="${zapisi.dohvatiPogresku('title')}" />
						</div>
					</c:if></td>

			</tr>



			

			<tr>
				<td align="right"><em>Tekst</em></td>
				<td><textarea name="text" rows="5" cols="39">${zapisi.text}</textarea> <c:if
						test="${zapisi.imaPogresku('title')}">
						<div class="greska">
							<c:out value="${zapisi.dohvatiPogresku('title')}" /></c:if></td></td>

			</tr>

			<tr>
				<td></td>
				<td><input type="submit" name="metoda" value="Pohrani"></td>
			</tr>

			<tr>
				<td></td>
				<td><input type="submit" name="metoda" value="Odustani"></td>
			</tr>
		</table>
	</form>
	
	


</body>
</html>