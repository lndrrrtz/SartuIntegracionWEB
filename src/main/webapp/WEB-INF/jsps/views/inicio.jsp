<%@ include file="../templates/taglibs.jsp" %>

<sec:authentication var="usuarioAutenticado" property="principal" />

<h1>Sartu da</h1>

ID: ${usuarioAutenticado.id} <br />
NOMBRE: ${usuarioAutenticado.nombre}

<br />
<br />

<spring:url var="urlLogout" value="/logout"/>
<a href="${urlLogout}">Logout</a>
<!-- <a href="http://localhost:9081/SartuOauthWEB/oauth/logout?redirectUri=">Logout</a> -->