<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
    
<!DOCTYPE html>
<html>
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
					<c:out value="Nom: ${user.first_name}"/><br/>
					<c:out value="Prénom:  ${user.last_name}"/>	
					<c:out value="User id_planning:  ${user.planning.id}"/>	<!-- ne marche pas -->
				</div>
				<div class="profilePicture">
					<img class="picture" src="smile.jpg" alt="Profile picture">
				</div>
				
				<div class="serieBoutonsAdmin">
					<!-- FORM pour traitement des options -->
					<form action="/Base/list?req=mod&id=${user.id}" method="POST">
						
					    <c:if test="${user.admin}">
					        result de user.admin : is an admin						        
					    </c:if>    
					    <c:if test="${!user.admin}">
					        <p>result de user.admin : not an admin</p>
					    </c:if>
					    <p>$$$</p>
					    <input type="checkbox" id="adminStatus" value="adminStatus" name="adminStatus" <c:if test="${user.admin}"> checked</c:if>/>						<br/>
						<p>$$$</p>
						
						<input type="button" value="Autorisation export planning" name="AutorExportPlanning"/><br/>
						    <c:if test="${user.planning.export}">
						        <p>Peut être exporté</p> 
						    </c:if>    
						    <c:if test="${!user.planning.export}">
						        <p>Ne peut PAS être exporté</p>
						    </c:if>
						    
						<input type="button" value="Autorisation accès planning par collaborateur" name="AutorAccesPlanningParExt"/><br/>
							<c:if test="${user.planning.acces}">
						        <p>Accès libre</p> 
						    </c:if>    
						    <c:if test="${!user.planning.acces}">
						        <p>Accès refusé</p>
						    </c:if>
						
						<input type="button" value="Autorisation modification planning par collaborateur" name="AutorModifPlanningParCollab"/>
							<c:if test="${user.planning.modification}">
						        <p>Modifications autorisées</p> 
						    </c:if>    
						    <c:if test="${!user.planning.modification}">
						        <p>Modifications interdites</p>
						    </c:if>
						
					</form>
					<p>$$$$$$$$$$$$$$$</p>
				</div>
			</div>
		</c:forEach>	
	</div>
	
</body>
</html>