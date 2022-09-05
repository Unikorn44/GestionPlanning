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
				

				<p>user compte </p>
				<c:out value="${user.compte_actif}"/>
				
				
				<div class="présentationUser">
					<c:out value="Nom: ${user.first_name}"/><br/>
					<c:out value="Prénom:  ${user.last_name}"/>	
				</div>
				<div class="profilePicture">
					<img class="picture" src="smile.jpg" alt="Profile picture">
				</div>
				
				<div class="serieBoutonsAdmin">
					<!-- FORM pour traitement des options -->
					<form action="/Base/list?req=mod&id=${user.id}" method="POST">
						<!-- la checkbox renvoie TRUE quand elle est COCHEE -->
					    <input type="checkbox" id="adminStatus" value="true" name="adminStatus" <c:if test="${user.admin}"> checked</c:if> />
						Status d'Admin<br/>						
						<input type="checkbox" id="autorExportPlanningStatus" value="true" name="autorExportPlanningStatus" <c:if test="${user.planning.export}"> checked</c:if> />
						Autorisation export planning<br/>
						<input type="checkbox" id="autorAccesPlanningParExt" value="true" name="autorAccesPlanningParExt" <c:if test="${user.planning.acces}"> checked</c:if> />
						Autorisation accès planning par collaborateur<br/>
						<input type="checkbox" id="autorModifPlanningParCollab" value="true" name="autorModifPlanningParCollab" <c:if test="${user.planning.modification}"> checked</c:if> />
						Autorisation modification planning par collaborateur<br/>
						<input type="button" id="validationModifStatus" value="Enregistrer les modifications" name="validModif"/>
					</form>
					<p>$$$$$$$$$$$$$$$</p>
				</div>
			</div>
		</c:forEach>	
	</div>
	
</body>
</html>