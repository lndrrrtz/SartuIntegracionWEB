<%@ include file="../../templates/taglibs.jsp" %>

<%@page import="net.edu.sartuweb.core.enums.TipoUsuario"%>
<%@page import="net.edu.sartuweb.core.enums.Accion"%>

<c:set var="tipo_normal" value='<%=TipoUsuario.NORMAL%>'/>
<c:set var="tipo_administrador" value='<%=TipoUsuario.ADMINISTRADOR%>'/>
<c:set var="accion_nuevo" value='<%=Accion.NEW%>'/>

<sec:authentication var="usuarioAutenticado" property="principal" />
<c:set var="isNuevoCliente" value="${fn:contains(requestScope['javax.servlet.forward.request_uri'], 'new')}" />

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
			<spring:url var="urlClientes" value="/clientes" />
			<a href="${urlClientes}"><spring:message code="clientes"/></a>
		</li>
		<li class="breadcrumb-item active" aria-current="page"><spring:message code="cliente"/></li>
	</ol>
</nav>

<div class="container">
	
	<div class="row mt-4 mb-2">	
		<div class="col-12">
			<h2><spring:message code="cliente"/></h2>
		</div>
	</div>

	<c:choose>
		<c:when test="${isNuevoCliente}">
			<spring:url var="urlFormularioCliente" value="/clientes/new" />
		</c:when>
		<c:otherwise>
			<spring:url var="urlFormularioCliente" value="/clientes/${cliente.clientId}/edit" />
		</c:otherwise>
	</c:choose>
	
	<form:form action="${urlFormularioCliente}" id="clienteForm" method="POST" modelAttribute="cliente" autocomplete="off">
		
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
			<label for="id"><spring:message code="client.id"/></label>
			<c:choose>
				<c:when test="${isNuevoCliente}">
					<form:input path="clientId" class="form-control" cssErrorClass="form-control is-invalid" id="id" />
				</c:when>
				<c:otherwise>
					<form:hidden path="clientId"/>
					<input type="text" class="form-control" id="id" readonly="readonly" value="${cliente.clientId}" />
				</c:otherwise>
			</c:choose>
			
			<div class="invalid-feedback">
				<form:errors path="clientId"/>
			</div>
		</div>
		<div class="form-group">
			<label for="clientSecret"><spring:message code="clientSecret"/></label>
			<form:input path="clientSecret" class="form-control" cssErrorClass="form-control is-invalid" id="clientSecret" />
			<div class="invalid-feedback">
				<form:errors path="clientSecret"/>
			</div>
		</div>
		<div class="form-group">
			<label for="nombre"><spring:message code="descripcion"/></label>
			<form:input path="descripcion" class="form-control" cssErrorClass="form-control is-invalid" id="descripcion" />
			<div class="invalid-feedback">
				<form:errors path="descripcion"/>
			</div>
		</div>
		<div class="form-group">
			<label for="resourceIds"><spring:message code="audiencias"/></label>
			<form:input path="resourceIds" class="form-control" cssErrorClass="form-control is-invalid" id="resourceIds" />
			<div class="invalid-feedback">
				<form:errors path="resourceIds"/>
			</div>
		</div>
		<div class="form-group">
			<label for="scope"><spring:message code="scopes"/></label>
			<form:input path="scope" class="form-control" cssErrorClass="form-control is-invalid" id="scope" />
			<div class="invalid-feedback">
				<form:errors path="scope"/>
			</div>
		</div>
		<div class="form-group">
			<label for="authorizedGrantTypes"><spring:message code="flujos.autorizacion"/></label>
			<form:input path="authorizedGrantTypes" class="form-control" cssErrorClass="form-control is-invalid" id="authorizedGrantTypes" />
			<div class="invalid-feedback">
				<form:errors path="resourceIds"/>
			</div>
		</div>
		<div class="form-group">
			<label for="authorities"><spring:message code="roles"/></label>
			<form:input path="authorities" class="form-control" cssErrorClass="form-control is-invalid" id="authorities" />
			<div class="invalid-feedback">
				<form:errors path="authorities"/>
			</div>
		</div>
		<div class="form-group">
			<label for="registeredRedirectUris"><spring:message code="audiencias"/></label>
			<form:input path="registeredRedirectUris" class="form-control" cssErrorClass="form-control is-invalid" id="registeredRedirectUris" />
			<div class="invalid-feedback">
				<form:errors path="registeredRedirectUris"/>
			</div>
		</div>
		<div class="form-group">
			<label for="accessTokenValiditySeconds"><spring:message code="tiempo.acces.token"/></label>
			<form:input path="accessTokenValiditySeconds" class="form-control" cssErrorClass="form-control is-invalid" id="accessTokenValiditySeconds" />
			<div class="invalid-feedback">
				<form:errors path="accessTokenValiditySeconds"/>
			</div>
		</div>
		
		<div class="form-group">
			<label for="refreshTokenValiditySeconds"><spring:message code="tiempo.refresh.token"/></label>
			<form:input path="refreshTokenValiditySeconds" class="form-control" cssErrorClass="form-control is-invalid" id="refreshTokenValiditySeconds" />
			<div class="invalid-feedback">
				<form:errors path="refreshTokenValiditySeconds"/>
			</div>
		</div>
		
		<div class="form-group">
			<label for="autoApproveScopes"><spring:message code="autoaprobacion.scope"/></label>
			<form:select path="autoApproveScopes" class="form-control" cssErrorClass="form-control is-invalid" id="autoApproveScopes">
				<form:option value="true">True</form:option>
				<form:option value="false">False</form:option>
			</form:select>
			<div class="invalid-feedback">
				<form:errors path="autoApproveScopes"/>
			</div>
		</div>
		
		<div class="form-group">
			<label for="additionalInformation"><spring:message code="informacion.adicional"/></label>
			<form:input path="additionalInformation" class="form-control" cssErrorClass="form-control is-invalid" id="additionalInformation" />
			<div class="invalid-feedback">
				<form:errors path="additionalInformation"/>
			</div>
		</div>
		
		<div class="col text-center">
			<spring:url var="urlUsuarios" value="/clientes" />
			<a href="${urlUsuarios}" class="btn btn-outline-dark mr-2"><spring:message code="volver" /></a>
			<form:button class="btn btn-primary"><spring:message code="guardar"/></form:button>
		</div>
		
	</form:form>
	
</div>