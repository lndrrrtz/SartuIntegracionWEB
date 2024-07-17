<%@ include file="../../templates/taglibs.jsp" %>

<%@page import="net.edu.sartuweb.core.enums.AccionRestriccion"%>
<%@page import="net.edu.sartuweb.core.enums.TipoRestriccion"%>

<c:set var="tipo_ip" value='<%=TipoRestriccion.IP %>'/>
<c:set var="accion_equals" value='<%=AccionRestriccion.EQUALS %>'/>
<c:set var="accion_contains" value='<%=AccionRestriccion.CONTAINS %>'/>

<sec:authentication var="restriccionAutenticado" property="principal" />
<c:set var="isNuevoRestriccion" value="${fn:contains(requestScope['javax.servlet.forward.request_uri'], 'new')}" />

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
			<spring:url var="urlRestricciones" value="/restricciones" />
			<a href="${urlRestricciones}"><spring:message code="restricciones"/></a>
		</li>
		<li class="breadcrumb-item active" aria-current="page"><spring:message code="restriccion"/></li>
	</ol>
</nav>

<div class="container">
	
	<div class="row mt-4 mb-2">	
		<div class="col-12">
			<h2><spring:message code="restriccion"/></h2>
		</div>
	</div>

	<c:choose>
		<c:when test="${isNuevoRestriccion}">
			<spring:url var="urlFormularioRestriccion" value="/restricciones/new" />
		</c:when>
		<c:otherwise>
			<spring:url var="urlFormularioRestriccion" value="/restricciones/${restriccion.id}/edit" />
		</c:otherwise>
	</c:choose>
	
	<form:form action="${urlFormularioRestriccion}" id="restriccionForm" method="POST" modelAttribute="restriccion" autocomplete="off">
		
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
		
		<c:if test="${!isNuevoRestriccion}">
			<div class="form-group">
				<label for="id"><spring:message code="id"/></label>
				<form:hidden path="id"/>
				<input type="text" class="form-control" id="id" readonly="readonly" value="${restriccion.id}" />
				<div class="invalid-feedback">
					<form:errors path="id"/>
				</div>
			</div>
		</c:if>
		<div class="form-group">
			<label for="tipo"><spring:message code="tipo"/></label>
			<form:select path="tipo" class="form-control" cssErrorClass="form-control is-invalid" id="tipo">
				<form:option value="${tipo_ip}">${tipo_ip}</form:option>
			</form:select>
			
			<div class="invalid-feedback">
				<form:errors path="tipo"/>
			</div>
		</div>
		<div class="form-group">
			<label for="accion"><spring:message code="accion"/></label>
			<form:select path="accion" class="form-control" cssErrorClass="form-control is-invalid" id="accion">
				<form:option value="${accion_equals}">${accion_equals}</form:option>
				<form:option value="${accion_contains}">${accion_contains}</form:option>
			</form:select>
			<div class="invalid-feedback">
				<form:errors path="accion"/>
			</div>
		</div>
		<div class="form-group">
			<label for="descripcion"><spring:message code="descripcion"/></label>
			<form:input path="descripcion" class="form-control" cssErrorClass="form-control is-invalid" id="descripcion" />
			<div class="invalid-feedback">
				<form:errors path="descripcion"/>
			</div>
		</div>
		<div class="form-group">
			<label for="datos"><spring:message code="datos"/></label>
			<form:input path="datos" class="form-control" cssErrorClass="form-control is-invalid" id="datos" />
			<div class="invalid-feedback">
				<form:errors path="datos"/>
			</div>
		</div>
		
		<div class="col text-center">
			<spring:url var="urlRestricciones" value="/restricciones" />
			<a href="${urlRestricciones}" class="btn btn-outline-dark mr-2"><spring:message code="volver" /></a>
			<form:button class="btn btn-primary"><spring:message code="guardar"/></form:button>
		</div>
		
	</form:form>
	
</div>