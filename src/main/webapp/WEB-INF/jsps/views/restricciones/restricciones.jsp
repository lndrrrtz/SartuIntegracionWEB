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
		<li class="breadcrumb-item active" aria-current="page"><spring:message code="restricciones"/></li>
	</ol>
</nav>

<div class="container-fluid">
	<div class="row">
		
		<c:choose>
			
			<c:when test="${successMessage != null}">
				<div class="alert alert-success w-100">
					<i class="far fa-check-circle align-middle fa-lg mr-2"></i>
					<spring:message code="${successMessage}"/>
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
			</c:when>
			
			<c:when test="${failureMessage != null}">
				<div class="alert alert-danger w-100">
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
					<div class="col-6">
						<h2><spring:message code="restricciones"/></h2>
					</div>
					<div class="col-6">
						<spring:url var="urlNuevo" value="/restricciones/new" />
						<a href="${urlNuevo}" class="btn btn-success float-sm-right"><spring:message code="crear.restriccion" /></a>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<table id="tablaRestricciones" class="table table-striped table-bordered" width="100%">
							<thead>
								<tr>
									<th scope="col" class="w-5"><spring:message code="id"/></th>
									<th scope="col" class="w-15"><spring:message code="tipo"/></th>
									<th scope="col" class="w-15"><spring:message code="accion"/></th>
									<th scope="col" class="w-30"><spring:message code="descripcion"/></th>
									<th scope="col" class="w-30"><spring:message code="datos"/></th>
									<th scope="col" class="w-auto"></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="restriccion" items="${restricciones}">
									<tr>
										<td>
											<spring:url var="urlRestriccionEdit" value="/restricciones/${restriccion.id}/edit" />
											<a href="${urlRestriccionEdit}">
												<c:out value="${restriccion.id}"/>
											</a>
										</td>
										<td><c:out value="${restriccion.tipo}"/></td>
										<td><c:out value="${restriccion.accion}"/></td>
										<td><c:out value="${restriccion.descripcion}"/></td>
										<td><c:out value="${restriccion.datos}"/></td>
										<td class="w-auto">
											<spring:url var="urlEliminar" value="/restricciones/${restriccion.id}/delete" />
											<a href="${urlEliminar}" class="btn btn-danger btn-tx" title='<spring:message code="eliminar"/>'><i class="fas fa-trash fa-sm text-white"></i></a>
										</td>
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
		$("#tablaRestricciones").DataTable({
			responsive: true,
			autoWidth: true,
			"columnDefs": [
				{ "orderable": false, "targets": [5] }
			],
			language: {
				url: "<spring:url value="/recursos/lib/${springRequestContext.locale.language}/${springRequestContext.locale.language}_ES.json"/>"
			}
		});
	});
</script>