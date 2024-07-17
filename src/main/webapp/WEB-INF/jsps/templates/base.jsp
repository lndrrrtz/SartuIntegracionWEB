<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="taglibs.jsp"%>

<spring:url var="ruta_recursos" value="/recursos" />

<!DOCTYPE html>

<html>

	<head>
		<meta charset="ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />

		<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
		<link rel="stylesheet" href="//cdn.datatables.net/2.0.8/css/dataTables.bootstrap4.css">

		<spring:url var="ruta_recursos" value="/recursos" scope="request"/>
		
		<link rel="stylesheet" href="${ruta_recursos}/css/estilos.css">
		<link rel="stylesheet" href="${ruta_recursos}/css/theme.css">
		<link rel="stylesheet" href="${ruta_recursos}/css/template.css">
		
		<link rel="icon" type="image/x-icon" href="${ruta_recursos}/imagenes/favicon.ico">
		
<!-- 		<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script> -->
<!-- 		<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script> -->
		
		<title><spring:message code="integracion.sartu"/></title>

		<spring:message var="formatoFechaDateTime" code="format.date.large" scope="request"/>
		
	</head>
	
	<body>
		
		 <style>
	        /* CSS para asegurar que el footer se comporte correctamente */
	        body {
	            display: flex;
	            flex-direction: column;
	            min-height: 100vh;
	        }
	        main {
	            flex: 1;
	        }
	    </style>
			
		<sec:authentication var="usuarioAutenticado" property="principal" scope="request" />
		
		<header>
			<tiles:insertAttribute name="header" />
		</header>
	
		<main>
			<tiles:insertAttribute name="cuerpo" />
		</main>
		
		<footer class="footer bg-body-tertiary text-center text-lg-start py-3 mt-5">
			<div class="text-center p-2 bg-light">
				<a class="text-body" href="https://www.unir.net/" target="_blank"><img src="${ruta_recursos}/imagenes/unir.svg" width="50" alt="cargando" /></a>
			</div>
		</footer>
		
		<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
		<script src="//cdn.datatables.net/2.0.8/js/dataTables.bootstrap4.js"></script>
		<script src="//cdn.datatables.net/2.0.8/js/dataTables.js"></script>
		<script src="//cdnjs.cloudflare.com/ajax/libs/popper.js/1.13.0/umd/popper.min.js"></script>
		<script src="//cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
		
	</body>
	
</html>