<%@ include file="taglibs.jsp"%>

<sec:authentication var="usuarioAutenticado" property="principal" />

<nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-primary" id="navbar1">
	<div class="container-fluid">
		<spring:url var="urlHome" value="/"/>
		<a class="navbar-brand mr-1 mb-1 mt-0" href="${urlHome}">
			<img src="${ruta_recursos}/imagenes/sartu-blanco.svg" height="20" alt='<spring:message code="gestion.sartu"/>' />
			<span class="badge badge-light align-middle ml-1 mr-3"><spring:message code="gestion"/></span>
		</a>
		<div class="navbar-collapse collapse">
		
		<c:set var="uri" value="${requestScope['javax.servlet.forward.request_uri']}" />
		
		<c:choose>
			<c:when test="${fn:contains(uri, 'usuarios')}">
				<c:set var="usuarioActive" value="active" />
			</c:when>
			<c:when test="${fn:contains(uri, 'clientes')}">
				<c:set var="clientesActive" value="active" />
			</c:when>
			<c:when test="${fn:contains(uri, 'restricciones')}">
				<c:set var="restriccionesActive" value="active" />
			</c:when>
			<c:when test="${fn:contains(uri, 'accesos')}">
				<c:set var="accesosActive" value="active" />
			</c:when>
		</c:choose>
		
		<ul class="navbar-nav mr-auto">
			<li class="nav-item ${usuarioActive}">
				<spring:url var="urlUsuarios" value="/usuarios" />
				<a class="nav-link" href="${urlUsuarios}"><spring:message code="usuarios"/></a>
			</li>
			<li class="nav-item ${clientesActive}">
				<spring:url var="urlClientes" value="/clientes" />
				<a class="nav-link" href="${urlClientes}"><spring:message code="clientes"/></a>
			</li>
			<li class="nav-item ${restriccionesActive}">
				<spring:url var="urlRestricciones" value="/restricciones" />
				<a class="nav-link" href="#"><spring:message code="restricciones"/></a>
			</li>
			<li class="nav-item ${accesosActive}">
				<spring:url var="urlAccesos" value="/accesos" />
				<a class="nav-link" href="#"><spring:message code="accesos"/></a>
			</li>
		</ul>
		
		<ul class="navbar-nav ml-auto">
			<li class="nav-item text-nowrap">
				<a class="nav-link" href="#"><i class="fas fa-user"></i> <c:out value="${usuarioAutenticado.nombre}" /></a> 
			</li>
			
			<li class="nav-item">
				<span class="nav-link">|</span>
			</li>
			
			<!-- seleccion idioma -->
			<li class="nav-item dropdown">
				<a class="nav-link dropdown-toggle" href="#" id="layoutDd" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				<spring:message code="idioma" />
				</a>
				<div class="dropdown-menu" aria-labelledby="layoutDd">
					<spring:url var="url_locale_es" value="?locale=es"/>
					<a class="dropdown-item px-2" href="${url_locale_es}"><spring:message code="castellano"/></a>
					<spring:url var="url_locale_eu" value="?locale=eu"/>
					<a class="dropdown-item px-2" href="${url_locale_eu}"><spring:message code="euskara"/></a>
				</div>
			</li>
			
			<li class="nav-item">
				<span class="nav-link">|</span>
			</li>
			
			<li class="nav-item">
				<spring:url var="urlLogout" value="/logout"/>
				<a class="nav-link" href="${urlLogout}"><i class="fas fa-sign-in-alt"></i> </i><spring:message code="salir" /></a>
			</li>
			</ul>
			
		</div>
	</div>
</nav> 