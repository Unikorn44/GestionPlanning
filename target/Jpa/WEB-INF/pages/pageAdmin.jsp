<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
    
<!DOCTYPE html>
<html
	xmlns="http://www.w3.org/1999/xhml"
	xmlns:th="http://www.thymeleaf.org"
>

<head>
	<meta charset="ISO-8859-1">
	<title>ADMIN</title>
</head>

<body>
	<input type="button" value="Nouveau Compte" name="NouveauCompte"/>
	<input type="button" value="Détruire Compte" name="DetruireCompte"/>
	
	<div class="listUsers">
		<c:forEach var="user" items="${userListRecup}">
			<div class="cadre">
				<div class="LumièreCompteActif">
					<p>Compte Actif</p>
				</div>
				<div class="présentationUser">
					<span th:text="Nom: + ${user.first_name}"></span><br/>
					<span th:text="Prénom: + ${user.last_name}"></span>			
				</div>
				<div class="profilePicture">
					<img class="picture" src="smile.jpg" alt="Profile picture">
				</div>
				<div class="serieBoutonsAdmin">
					<input type="button" value="Admin" name="positionAdmin"/><br/>
					<input type="button" value="Autorisation export planning" name="AutorExportPlanning"/><br/>
					<input type="button" value="Autorisation accès planning par collaborateur" name="AutorAccesPlanningParExt"/><br/>
					<input type="button" value="Autorisation accès planning collaborateur" name="AutorAccesPlanningCollab"/><br/>
					<input type="button" value="Autorisation modification planning par collaborateur" name="AutorModifPlanningParCollab"/>
				</div>
			</div>
		</c:forEach>	
	</div>
	
</body>
</html>