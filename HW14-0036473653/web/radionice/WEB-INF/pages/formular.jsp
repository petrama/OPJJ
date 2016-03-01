<%@ page import="hr.fer.zemris.web.radionice.Radionica" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Radionica</title>

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
		<c:choose>
			<c:when test="${zapisi.id.isEmpty()}">
					Nova Radionica
				</c:when>
			<c:otherwise>
			Uređivanje radionice
			</c:otherwise>
		</c:choose>
	</h1>
	<form action="save" method="post">

		<input type="hidden" name="id" value="${zapisi.id}">

		<table align="left">
			<tr>
				<td align="right"><em>Naziv</em></td>
				<td><input type="text" name="naziv"
					value='<c:out value="${zapisi.naziv}"/>' size="50"> <c:if
						test="${zapisi.imaPogresku('naziv')}">
						<div class="greska">
							<c:out value="${zapisi.dohvatiPogresku('naziv')}" />
						</div>
					</c:if></td>

			</tr>



			<tr>
				<td align="right"><em>Datum</em></td>
				<td><input type="text" name="datum"
					value='<c:out value="${zapisi.datum}"/>' size="20"> <c:if
						test="${zapisi.imaPogresku('datum')}">
						<div class="greska">
							<c:out value="${zapisi.dohvatiPogresku('datum')}" />
						</div>
					</c:if></td>
			</tr>

			<tr>
				<td align="right"><em>Oprema</em></td>
				<td><select multiple="multiple" name="oprema" size="7" >
						<c:forEach var="o" items="${oprema}">
					
							<option value="${o.id}"
							<c:if test="${zapisi.oprema.contains(o.id)}">
							selected="selected"
							
							</c:if>
							
							
							>${o.vrijednost}</option>
						</c:forEach>
						</select></td>
			</tr>
			<tr>
			
			

				
				<td align="right"><em>Trajanje</em></td>
				<td><select name="trajanje" size="1">
						<c:forEach var="t" items="${trajanja}">
							<option value="${t.id}" >${t.vrijednost}
						</c:forEach>

				</select></td>
			</tr>


			<tr>
				<td align="right"><em>Publika</em></td>
				<td><c:forEach var="p" items="${publika}">
						<input type="checkbox" name="publika" value="${p.id}" 
						
						
						<c:if test="${zapisi.publika.contains(p.id)}">
							checked="checked"
							
							</c:if>
						
						>${p.vrijednost}<br>
					</c:forEach>
					<c:if
						test="${zapisi.imaPogresku('publika')}">
						<div class="greska">
							<c:out value="${zapisi.dohvatiPogresku('publika')}" />
						</div>
					</c:if>
					</td>
					
			</tr>

			<tr>
				<td align="right"><em>Maksimalno polaznika</em></td>
				<td><input type="text" name="maksPolaznika"
					value='<c:out value="${zapisi.maksPolaznika}"/>' size="5">
					<br> <c:if test="${zapisi.imaPogresku('maksPolaznika')}">
						<div class="greska">
							<c:out value="${zapisi.dohvatiPogresku('maksPolaznika')}" />
						</div>
					</c:if></td>
			</tr>

			<tr>
				<td align="right"><em>EMail</em></td>
				<td><input type="text" name="email"
					value='<c:out value="${zapisi.email}"/>' size="50"> <br>
					<c:if test="${zapisi.imaPogresku('email')}">
						<div class="greska">
							<c:out value="${zapisi.dohvatiPogresku('email')}" />
						</div>
					</c:if></td>
			</tr>

			<tr>
				<td align="right"><em>Dopuna</em></td>
				<td><textarea name="dopuna" rows="5" cols="39">${zapisi.dopuna}</textarea></td>

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