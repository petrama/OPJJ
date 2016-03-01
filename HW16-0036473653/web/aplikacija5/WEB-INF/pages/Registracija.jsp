<%@page import="hr.fer.zemris.java.tecaj_14.model.forms.BlogUserRegistrationForm" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<title>Registracija novog korisnika</title>

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
	<h1>
		Registracija novog korisnika
	</h1>
	
	<form action="save" method="post">


		<table align="left">
			<tr>
				<td align="right"><em>Ime</em></td>
				<td><input type="text" name="firstName"
					value='<c:out value="${form.firstName}"/>' size="50"> <c:if
						test="${form.imaPogresku('firstName')}">
						<div class="greska">
							<c:out value="${form.dohvatiPogresku('firstName')}" />
						</div>
					</c:if></td>

			</tr>
	
		<tr>
				<td align="right"><em>Prezime</em></td>
				<td><input type="text" name="lastName"
					value='<c:out value="${form.lastName}"/>' size="50"> <c:if
						test="${form.imaPogresku('lastName')}">
						<div class="greska">
							<c:out value="${form.dohvatiPogresku('lastName')}" />
						</div>
					</c:if></td>

			</tr>

			
			<tr>
				<td align="right"><em>EMail</em></td>
				<td><input type="text" name="email"
					value='<c:out value="${form.email}"/>' size="50"> <br>
					<c:if test="${form.imaPogresku('email')}">
						<div class="greska">
							<c:out value="${form.dohvatiPogresku('email')}" />
						</div>
					</c:if></td>
			</tr>
			
			<tr>
				<td align="right"><em>Nickname</em></td>
				<td><input type="text" name="nick"
					value='<c:out value="${form.nick}"/>' size="50"> <c:if
						test="${form.imaPogresku('nick')}">
						<div class="greska">
							<c:out value="${form.dohvatiPogresku('nick')}" />
						</div>
					</c:if></td>
			</tr>

			<tr>
				<td align="right"><em>Lozinka</em></td>
				<td><input type="password" name="password" size="20"></td>
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