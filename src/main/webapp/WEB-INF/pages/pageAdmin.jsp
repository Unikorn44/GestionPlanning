
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
    
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel=stylesheet type="text/css" href="${pageContext.request.contextPath}/static/css/style.css">
	<meta charset="ISO-8859-1">
	<title>ADMIN</title>
</head>

<body>
	<!-- NAVBAR -->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<img src="../../pics/logoblack.png" alt="APPLI PLANNING">
		<!-- <a class="navbar-brand" href="#">APPLI PLANNING</a> -->
		<br/>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		
		<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
			<div class="navbar-nav">
				<a class="nav-item nav-link active" href="http://localhost:4200/accueil"> Acceuil <span class="sr-only">(current)</span></a>

			</div>
		</div>
	</nav>
	<br>
		<h1 class="text-center">
			PAGE ADMIN
		</h1>
	
		
    <div class="container">
		<div class="">
			
			<div >
		     	<a href="#open-modal" class="btn btn-primary mt-3">Créer un nouvel Utilisateur</a>
		    </div>
		    
			<div id="open-modal" class="modal-window">
		       	<div>
		          	<h1>Nouvel utilisateur</h1>
		          	<a href="#modal-close" title="Close" class="modal-close"> X </a>
		            <div class="créationUser">
						<form action="pageadmin?req=new" method="POST">
							<label>Nom:</label>
								<input type="text" id="last_name" placeholder="Nom" name="last_name" required><br>
							<label>Prénom:</label>
								<input type="text" id="first_name" placeholder="Prénom" name="first_name" required><br>
							<label>Ville:</label>
								<input type="text" id="city" placeholder="Ville" name="city" placeholder="Lyon" required><br>
							<label>Date d'anniversaire:</label>
								<input type="date" id="birthday_date" name="birthday_date" required><br>
							<label>Téléphone:</label>
								<input type="tel" id="phone_number" placeholder="01-23-45-67-89" name="phone_number" pattern="[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{2}" required><br>
							<label>email:</label>
								<input type="email" id="email" placeholder="JohnDoe@BigBox.com" name="email" required><br>
							<input type="submit" value="Créer ce nouvel utilisateur" name="newUser" class="btn btn-primary"/>
						</form>
					</div>
	        	</div>
	     	</div>
		</div>

		<div class="row">
			<c:forEach var="user" items="${userListRecup}">
				<div class="card col-lg-5 w-30 ml-5 mt-3">							
                    <div class="card-header row" >	                    	
                    	<div class="col-lg-8">
	                    	<h4>
		                   		<c:out value="${user.first_name}"/>
		                   		<c:out value=" ${user.last_name}"/>	
		                   	</h4>
	                   	</div>
	                   	<div class="col-lg-4">
							<c:if test="${user.compte_actif}">	
								<div class=" btn btn-success">
									<p>Compte Actif</p>				
								</div>
							 </c:if>
							 <c:if test="${!user.compte_actif}">	
								<div class=" btn btn-warning">
									<p>Compte Inactif</p>				
								</div>			
							</c:if>			
						</div>
                    </div>
                    <div class="card-body">
                        <span class="card-text">
	                        <form action="pageadmin?req=mod" method="POST">
								<input type="text" name="userId" value="${user.id}" hidden/>
								<!-- la checkbox renvoie TRUE quand elle est COCHEE  et FALSE quand elle ne l'est pas -->
								<input type="checkbox" id="compteActif" value="true" name="compteActif" <c:if test="${user.compte_actif}"> checked</c:if> />
								Compte Actif<br/>		
							    <input type="checkbox" id="adminStatus" value="true" name="adminStatus" <c:if test="${user.admin}"> checked</c:if> />
								Status d'Admin<br/>						
								<input type="checkbox" id="autorExportPlanningStatus" value="true" name="autorExportPlanningStatus" <c:if test="${user.planning.export}"> checked</c:if> />
								Autorisation export planning<br/>
								<input type="checkbox" id="autorAccesPlanningParExt" value="true" name="autorAccesPlanningParExt" <c:if test="${user.planning.acces}"> checked</c:if> />
								Autorisation accès planning par collaborateur<br/>
								<input type="checkbox" id="autorModifPlanningParCollab" value="true" name="autorModifPlanningParCollab" <c:if test="${user.planning.modification}"> checked</c:if> />
								Autorisation modification planning par collaborateur<br/>
								<input type="submit" id="validationModifStatus" value="Enregistrer les modifications" name="validModif" class="btn btn-primary"/>
							</form>
                        </span>
                        <div class="d-flex justify-content-end">
	                        <form action="pageadmin?req=out" method="POST" >
								<input type="text" name="userId" value="${user.id}" hidden/>
								<input type="submit" value="Supprimer utilisateur" name="destroyUser" class="btn btn-danger"/>
							</form>
						</div>
                    </div>
				</div>
			</c:forEach>	
		</div>
	</div>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>	
</body>
</html>
