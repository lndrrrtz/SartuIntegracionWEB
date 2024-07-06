<%@ include file="../templates/taglibs.jsp" %>

<sec:authentication var="usuarioAutenticado" property="principal" />

<h1>Sartu da</h1>

ID: ${usuarioAutenticado.id} <br />
NOMBRE: ${usuarioAutenticado.nombre}