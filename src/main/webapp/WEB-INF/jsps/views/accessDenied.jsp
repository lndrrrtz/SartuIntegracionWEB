<%@ include file="../templates/taglibs.jsp" %>

<sec:authentication var="usuarioAutenticado" property="principal" />

<div class="container-fluid">
	
	<div class="row justify-content-center mt-5 mb-3">
		<h1><spring:message code="acceso.denegado" /></h1>
	</div>
	<div class="row justify-content-center">
		<spring:url var="urlLogout" value="/logout"/>
		<a href="${urlLogout}"><spring:message code="salir" /></a>
	</div>
		
	</div>
</div>
