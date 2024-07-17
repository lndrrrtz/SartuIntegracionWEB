<%@ include file="../../templates/taglibs.jsp" %>

<sec:authentication var="usuarioAutenticado" property="principal" />

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<link rel="stylesheet" href="//cdn.datatables.net/2.0.8/css/dataTables.dataTables.min.css">
<script src="//cdn.datatables.net/2.0.8/js/dataTables.min.js"></script>

<nav aria-label="breadcrumb">
	<ol class="breadcrumb breadcrumb-sm">
		<li class="breadcrumb-item">
			<spring:url var="urlHome" value="/" />
			<a href="${urlHome}">Home</a>
		</li>
		<li class="breadcrumb-item active" aria-current="page"><spring:message code="accesos"/></li>
	</ol>
</nav>

<div class="container-fluid">
	<div class="row">
		
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
		
	</div>
	<div class="row">
		<div class="col-12 p-0 bg-light vh-50 py-2">
			<div class="container-fluid">
				<div class="row mb-2">	
					<h2><spring:message code="accesos"/></h2>
				</div>
				<div class="row">
					<div class="col-md-2">
						<div class="card">
							<div class="card-header">
								Búsqueda
							</div>
							<div class="card-body">
								<spring:url var="urlBuscarAccesos" value="/accesos/buscar" />
								<form:form action="${urlBuscarAccesos}" id="accesoForm" method="POST" modelAttribute="acceso" autocomplete="off">
									<div class="form-group">
										<label for="id"><spring:message code="id"/></label>
										<form:input path="id" class="form-control" id="id" />
									</div>
									
									<div class="form-group">
										<label for="flujo"><spring:message code="flujo.autorizacion"/></label>
										<form:select path="flujo" class="form-control" id="flujo">
											<form:option value=""></form:option>
											<form:option value="authorization_code">Authorization_code</form:option>
											<form:option value="client_credentials">client_credentials</form:option>
										</form:select>
									</div>
									
									<div class="form-group">
										<label for="clientId"><spring:message code="client.id"/></label>
										<form:input path="clientId" class="form-control" id="clientId" />
									</div>
									
									<div class="form-group">
										<label for="idUsuario"><spring:message code="usuario"/></label>
										<form:input path="idUsuario" class="form-control" id="idUsuario" />
									</div>
									
									<div class="form-group">
										<label for="resultado"><spring:message code="resultado"/></label>
										<form:input path="resultado" class="form-control" id="resultado" />
									</div>
									
<!-- 									<div class="form-group"> -->
<%-- 										<label for="fecha"><spring:message code="fecha"/></label> --%>
<%-- 										<form:input path="fecha" class="form-control" id="fecha" /> --%>
<!-- 									</div> -->

									<div class="form-group">
										<label for="scope"><spring:message code="scope"/></label>
										<form:input path="scope" class="form-control" id="scope" />
									</div>
									
									<div class="col text-center">
										<form:button class="btn btn-primary"><spring:message code="buscar"/></form:button>
									</div>
									
								</form:form>
							</div>
						</div>
					</div>
					
					<div class="col-md-10">
						<table id="tablaUsuarios" class="table table-striped table-bordered" width="100%">
							<thead>
								<tr>
									<th scope="col" class="w-10"><spring:message code="id"/></th>
									<th scope="col" class="w-15"><spring:message code="flujo.autorizacion"/></th>
									<th scope="col" class="w-15"><spring:message code="client.id"/></th>
									<th scope="col" class="w-15"><spring:message code="usuario"/></th>
									<th scope="col" class="w-20"><spring:message code="resultado"/></th>
									<th scope="col" class="w-15"><spring:message code="fecha"/></th>
									<th scope="col" class="w-10"><spring:message code="scope"/></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="acceso" items="${accesos}">
									<tr>
										<td><c:out value="${acceso.id}"/></td>
										<td><c:out value="${acceso.flujo}"/></td>
										<td><c:out value="${acceso.clientId}"/></td>
										<td><c:out value="${acceso.idUsuario}"/></td>
										<td><c:out value="${acceso.resultado}"/></td>
										<td>
											<fmt:formatDate value="${acceso.fecha}" pattern="${formatoFechaDateTime}"/>
										</td>
										<td><c:out value="${acceso.scope}"/></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" class="init">
	
	$(document).ready(function() {
// 		new DataTable('#tablaUsuarios');
		$("#tablaUsuarios").DataTable({
			responsive: true,
			autoWidth: true,
			order: [[0, 'desc']],
			language: {
				url: "<spring:url value="/recursos/lib/${springRequestContext.locale.language}/${springRequestContext.locale.language}_ES.json"/>"
			}
		});
	});
</script>