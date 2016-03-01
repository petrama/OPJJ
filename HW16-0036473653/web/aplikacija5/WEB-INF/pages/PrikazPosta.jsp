<%@page import="java.lang.String"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<%Long id=(Long) request.getSession().getAttribute("current.user.id");
String ime=(String) request.getSession().getAttribute("current.user.fn");
String prezime=(String) request.getSession().getAttribute("current.user.ln");
String nick=(String) request.getSession().getAttribute("current.user.nick");

String nickPrikaza=(String) request.getAttribute("nick");
Boolean dozvola=(id!=null && nick.equals(nickPrikaza));

%>

<html>
<head>
<title>Zapisi bloga</title>
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



<h1>Post korisnika ${korisnik.nick}</h1>
<h2>${entry.title}</h2>
	
<h3>${entry.text}</h3>
	
	<br>
	<br>
	
	<c:choose>
		
		<c:when test="<%=dozvola==true%>">
		<a href="${entry.id}/edit">Uredi</a>
		</c:when>
	</c:choose>
	
			
					

<br>	
	
 <c:forEach var="comment" items="${entry.comments}">
	
			<p>${comment.message}<p>
			<h5>${comment.usersEMail}</h5>
				<br>
				</c:forEach>
	<br>
	<a href=/aplikacija5/servleti/main>Naslovnica</a>
	
	<h4>Dodajte novi komentar</h4>
	<form action="/aplikacija5/servleti/saveComment" method="post">

		
		<input type="hidden" name="entry" value="${entry.id}">
		<input type="hidden" name="nick" value="${nick}">

		<table align="left">
			


			<tr>
				<td align="right"><em>Tekst</em></td>
				<td><textarea name="message" rows="5" cols="39">${form.message}</textarea><br>
				<c:if test="${form.imaPogresku('message')}">
						<div class="greska">
							<c:out value="${form.dohvatiPogresku('message')}" />
						</div>
					</c:if></td>

			</tr>
			
				<tr>
				<td align="right"><em>EMail</em></td>
				<td><input type="text" name="email"
					value='<c:out value="${form.usersEMail}"/>' size="50"> <br>
					<c:if test="${form.imaPogresku('email')}">
						<div class="greska">
							<c:out value="${form.dohvatiPogresku('email')}" />
						</div>
					</c:if></td>
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
