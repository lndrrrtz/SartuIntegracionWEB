<%@ include file="../templates/taglibs.jsp" %>

<sec:authentication var="usuarioAutenticado" property="principal" />

<nav aria-label="breadcrumb">
	<ol class="breadcrumb breadcrumb-sm">
		<li class="breadcrumb-item active" aria-current="page">Home</li>
	</ol>
</nav>

<div class="container-fluid">

	<div class="row">
		
		<c:if test="${failureMessage != null}">
			<div class="alert alert-danger">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<i class="far fa-times-circle align-middle fa-lg mr-2"></i>
				<spring:message code="${failureMessage}"/>
			</div>
		</c:if>
	
	</div>
	
	<div class="row">
		
		<spring:url var="urlUsuarios" value="/usuarios" />
		<a href="${urlUsuarios}">
			<div class="card text-white bg-primary m-3 float-left" style="max-width: 18rem;">
				<div class="card-header"><spring:message code="edicion"/> | <spring:message code="consulta"/></div>
				<div class="card-body">
					<h5 class="card-title"><spring:message code="usuarios"/></h5>
					<p class="card-text"><spring:message code="card.info.usuarios"/></p>
				</div>
			</div>
		</a>
		
		<spring:url var="urlUsuarios" value="/clientes" />
		<a href="${urlUsuarios}">
			<div class="card text-white bg-success m-3 float-left" style="max-width: 18rem;">
				<div class="card-header"><spring:message code="edicion"/> | <spring:message code="consulta"/></div>
				<div class="card-body">
					<h5 class="card-title"><spring:message code="clientes"/></h5>
					<p class="card-text"><spring:message code="card.info.clientes"/></p>
				</div>
			</div>
		</a>
		
		<spring:url var="urlUsuarios" value="/restricciones" />
		<a href="${urlUsuarios}">
			<div class="card text-white bg-danger m-3 float-left" style="max-width: 18rem;">
				<div class="card-header"><spring:message code="edicion"/> | <spring:message code="consulta"/></div>
				<div class="card-body">
					<h5 class="card-title"><spring:message code="restricciones"/></h5>
					<p class="card-text"><spring:message code="card.info.restricciones"/></p>
				</div>
			</div>
		</a>
		
		<spring:url var="urlUsuarios" value="/accesos" />
		<a href="${urlUsuarios}">
			<div class="card text-white bg-warning m-3 float-left" style="max-width: 19rem;">
				<div class="card-header"><spring:message code="consulta"/></div>
				<div class="card-body">
					<h5 class="card-title"><spring:message code="accesos"/></h5>
					<p class="card-text"><spring:message code="card.info.accesos"/></p>
				</div>
			</div>
		</a>
	</div>
</div>