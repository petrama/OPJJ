<%@ page import="hr.fer.zemris.web.radionice.Radionica" session="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String poruka = (String) request.getAttribute("poruka");
%>
<html>
<style type="text/css">
.greska {
	font-family: calibri;
	font-weight: italics;
	font-size: 0.8em;
	color: #FF00DD;
}
</style>

<body>
	<h1>Login</h1>
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
				<td><input type="text" name="username" size="20"></td>
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
</body>
</html>