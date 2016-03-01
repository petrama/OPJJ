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

<c:choose>
		<c:when test="<%=id==null%>">
			<h1>Anonimni korisnik</h1>
			
		</c:when>
		<c:otherwise>
		<h1>Ej korisniče <%=ime%> <%=prezime %> </h1>
		<a href="logout">Logout</a>
		</c:otherwise>
</c:choose>
	

<h3>Zapisi bloga korisnika ${korisnik.nick}</h3>
	<c:forEach var="entry" items="${korisnik.entrys}">
	<li><a href="${korisnik.nick}/${entry.id}">${entry.title}</a>
	
	
	
	
	</li>
			
					
				</c:forEach>
	
<c:choose>
		
		<c:when test="<%=dozvola==true%>">
		<a href="${korisnik.nick}/new">Novi Zapis</a>
		</c:when>
	</c:choose>
	<br>
	<a href=/aplikacija5/servleti/main>Naslovnica</a>
	
</body>
</html>
