<%@ include file="../../templates/taglibs.jsp" %>

<%@page import="net.edu.sartuweb.core.enums.TipoUsuario"%>

<c:set var="tipo_normal" value='<%=TipoUsuario.NORMAL%>'/>
<c:set var="tipo_administrador" value='<%=TipoUsuario.ADMINISTRADOR%>'/>

<sec:authentication var="usuarioAutenticado" property="principal" />
<c:set var="isNuevoUsuario" value="${fn:contains(requestScope['javax.servlet.forward.request_uri'], 'new')}" />

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<link rel="stylesheet" href="//cdn.datatables.net/2.0.8/css/dataTables.dataTables.min.css">
<script src="//cdn.datatables.net/2.0.8/js/dataTables.min.js"></script>

<nav aria-label="breadcrumb">
	<ol class="breadcrumb breadcrumb-sm">
		<li class="breadcrumb-item">
			<spring:url var="urlHome" value="/" />
			<a href="${urlHome}">Home</a>
		</li>
		<li class="breadcrumb-item">
			<spring:url var="urlUsuarios" value="/usuarios" />
			<a href="${urlUsuarios}"><spring:message code="usuarios"/></a>
		</li>
		<li class="breadcrumb-item active" aria-current="page"><spring:message code="usuario"/></li>
	</ol>
</nav>

<div class="container">
	
	<div class="row mt-4 mb-2">	
		<div class="col-12">
			<h2><spring:message code="usuario"/></h2>
		</div>
	</div>

	<c:choose>
		<c:when test="${isNuevoUsuario}">
			<spring:url var="urlFormularioUsuario" value="/usuarios/new" />
		</c:when>
		<c:otherwise>
			<spring:url var="urlFormularioUsuario" value="/usuarios/${usuario.id}/edit" />
		</c:otherwise>
	</c:choose>
	
	<form:form action="${urlFormularioUsuario}" id="usuarioForm" method="POST" modelAttribute="usuario" autocomplete="off">
		
		<!-- Mensajes  -->
		
		<spring:bind path="*">
			<c:if test="${not empty status.errorMessages}">
				<div class="alert alert-danger">
					<i class="far fa-times-circle align-middle fa-lg mr-2"></i>
					<spring:message code="corregir.errores.continuar"/>
				</div>
			</c:if>
		</spring:bind>
		
		<c:choose>
			
			<c:when test="${successMessage != null}">
				<div class="alert alert-success">
					<i class="far fa-check-circle align-middle fa-lg mr-2"></i>
					<spring:message code="${successMessage}"/>
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
			</c:when>
			
			<c:when test="${failureMessage != null}">
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<i class="far fa-times-circle align-middle fa-lg mr-2"></i>
					<spring:message code="${failureMessage}"/>
				</div>
			</c:when>
			
		</c:choose>
		
		<div class="form-group">
			<label for="id"><spring:message code="id"/></label>
			<c:choose>
				<c:when test="${isNuevoUsuario}">
					<form:input path="id" class="form-control" cssErrorClass="form-control is-invalid" id="id" />
				</c:when>
				<c:otherwise>
					<form:hidden path="id"/>
					<input type="text" class="form-control" id="id" readonly="readonly" value="${usuario.id}" />
				</c:otherwise>
			</c:choose>
			
			
			<div class="invalid-feedback">
				<form:errors path="id"/>
			</div>
		</div>
		<div class="form-group">
			<label for="nombre"><spring:message code="nombre"/></label>
			<form:input path="nombre" class="form-control" cssErrorClass="form-control is-invalid" id="nombre" />
			<div class="invalid-feedback">
				<form:errors path="id"/>
			</div>
		</div>
		<div class="form-group">
			<label for="dni"><spring:message code="dni"/></label>
			<form:input path="dni" class="form-control" cssErrorClass="form-control is-invalid" id="dni" />
			<div class="invalid-feedback">
				<form:errors path="dni"/>
			</div>
		</div>
		<div class="form-group">
			<label for="email"><spring:message code="email"/></label>
			<form:input path="email" class="form-control" cssErrorClass="form-control is-invalid" id="email" />
			<div class="invalid-feedback">
				<form:errors path="email"/>
			</div>
		</div>
		<div class="form-group">
			<label for="tipo"><spring:message code="tipo"/></label>
			<form:select path="tipo" class="form-control" cssErrorClass="form-control is-invalid" id="tipo">
				<form:option value="${tipo_normal}"><spring:message code="tipo.usuario.${tipo_normal}"/></form:option>
				<form:option value="${tipo_administrador}"><spring:message code="tipo.usuario.${tipo_administrador}"/></form:option>
			</form:select>
			<div class="invalid-feedback">
				<form:errors path="tipo"/>
			</div>
		</div>
		<div class="form-group">
			<label for="contrasena"><spring:message code="contrasena"/></label>
			<form:input path="contrasena" class="form-control" cssErrorClass="form-control is-invalid" id="contrasena" />
			<div class="invalid-feedback">
				<form:errors path="contrasena"/>
			</div>
		</div>
		
		<div class="col text-center">
			<spring:url var="urlUsuarios" value="/usuarios" />
			<a href="${urlUsuarios}" class="btn btn-outline-dark mr-2"><spring:message code="volver" /></a>
			<form:button class="btn btn-primary"><spring:message code="guardar"/></form:button>
		</div>
		
	</form:form>
	
</div>