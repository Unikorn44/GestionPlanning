<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>



<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<header>
	</header>
	
	<main>
	
		<c:if test="${param.liste != true}">
		<section id="formulaire">
			<form action="/Jpa/todo" method="post">
				<div>
					<label id="inp_nom">Nom : </label>
					<input type="text" id="inp_nom" name="nom" >
				</div>
				<div>
					<label id="inp_desc">Description :</label>
					<input type="text" id="inp_desc" name="description">
				</div>
				<input type="submit" value="Ajouter">
			</form>
		</section>
		</c:if>
		<c:if test="${param.liste == true}">
		<section id="liste">
		<fieldset>
			<legend>Native</legend>
			<ul>
				<c:forEach items="${todos}" var="todo">
					<li>${todo.nom} - ${todo.description}</li>
				</c:forEach>
			</ul>
		</fieldset>
		<fieldset>
			<legend>JPQL</legend>
			<ul>
				<c:forEach items="${todosJPQL}" var="todo">
					<li>${todo.nom} - ${todo.description}</li>
				</c:forEach>
			</ul>
		</fieldset>
		<fieldset>
			<legend>Prog</legend>
			<ul>
				<c:forEach items="${todosProg}" var="todo">
					<li>${todo.nom} - ${todo.description}</li>
				</c:forEach>
			</ul>
		</fieldset>
		
		<fieldset>
			<legend>Query Parameters</legend>
			<strong>${todoRes.nom} - ${todoRes.description}</strong>
			
		</fieldset>
		
			
			<a href="/Jpa/todo">Ajouter</a>
		</section>
		</c:if>
	</main>



	<footer>
	</footer>

</body>
</html>