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
		<li class="breadcrumb-item active" aria-current="page"><spring:message code="usuarios"/></li>
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
					<div class="col-6">
						<h2><spring:message code="usuarios"/></h2>
					</div>
					<div class="col-6">
						<spring:url var="urlNuevo" value="/usuarios/new" />
						<a href="${urlNuevo}" class="btn btn-success float-sm-right"><spring:message code="crear.usuario" /></a>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<table id="tablaUsuarios" class="table table-striped table-bordered" width="100%">
							<thead>
								<tr>
									<th scope="col" class="w-15"><spring:message code="id"/></th>
									<th scope="col" class="w-35"><spring:message code="nombre"/></th>
									<th scope="col" class="w-15"><spring:message code="dni"/></th>
									<th scope="col" class="w-34"><spring:message code="email"/></th>
									<th scope="col" class="w-auto"></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="usuario" items="${usuarios}">
									<tr>
										<td>
											<spring:url var="urlUsuarioEdit" value="/usuarios/${usuario.id}/edit" />
											<a href="${urlUsuarioEdit}">
												<c:out value="${usuario.id}"/>
											</a>
										</td>
										<td><c:out value="${usuario.nombre}"/></td>
										<td><c:out value="${usuario.dni}"/></td>
										<td><c:out value="${usuario.email}"/></td>
										<td class="w-auto">
											<spring:url var="urlEliminar" value="/usuarios/${usuario.id}/delete" />
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
// 		new DataTable('#tablaUsuarios');
		$("#tablaUsuarios").DataTable({
			responsive: true,
			autoWidth: true,
			"columnDefs": [
				{ "orderable": false, "targets": [4] }
			],
			language: {
				url: "<spring:url value="/recursos/lib/${springRequestContext.locale.language}/${springRequestContext.locale.language}_ES.json"/>"
			}
		});
	});
</script>