<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ include file="taglibs.jsp"%>

<spring:url var="ruta_recursos" value="/recursos" />

<!DOCTYPE html>

<html>

	<head>
		<meta charset="ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		
		<link rel="icon" type="image/x-icon" href="${ruta_estaticos_baibot}/img/favicon.ico">
		
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
		<link rel="stylesheet" href="${ruta_recursos}/css/estilos.css">
		
		<title><bean:message code="integracion.sartu"/></title>

		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
		
		<spring:url var="ruta_recursos" value="/recursos" scope="request"/>
	</head>
	
	<body>
		
		<header>
			<tiles:insertAttribute name="header" />
		</header>
	
		<main>
			<tiles:insertAttribute name="cuerpo" />
		</main>
		
		<footer class="bg-body-tertiary text-center text-lg-start fixed-bottom">
			<div class="text-center p-2 bg-light">
				<a class="text-body" href="https://www.unir.net/" target="_blank"><img src="${ruta_recursos}/imagenes/unir.svg" width="50" alt="cargando" /></a>
			</div>
		</footer>
	</body>
	
</html>