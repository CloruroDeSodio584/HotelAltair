<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="t"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ page session="false"%>
<html>
<head>
<style>
h5 {
	color: black;
}
</style>
<title>Hotel Altair</title>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- BOOTSTRAP CSS -->
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.min.css" />">

<link href="/resources/css/CSS_Propio.css" rel="stylesheet" />

<link rel="stylesheet"
	href="<c:url value="/resources/css/fonts/OLD/font-awesome.css" />">
<script defer
	src="https://use.fontawesome.com/releases/v5.0.6/js/all.js"></script>

</head>

<body>
	
	<!-- Inicio -->
	<nav class="navbar navbar-expand-md navbar-dark bg-dark">
		<figure class="figure mt-0 mb-0">
			<a class="navbar-brand" href=""><img
				src="<c:url value="/resources/images/hotel.png" />" class="figure-img img-fluid rounded"
				alt="Logo" style="height: 40px;"></a>
		</figure>

		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarExample" arial-controls="navbarExample"
			arial-expanded="false" arial-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarExample">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link"
					href="homet">Inicio</a></li>
			</ul>
			
			<!-- Formulario Entrar -->
			
		
		
			
		</div>

	</nav>
	
<!-- Inicio Mensaje Error/Exito -->
	
			<c:if test="${!empty mensaje}">
						<div class="alert alert-warning alert-dismissable">
							<button type="button" class="close" data-dismiss="alert"
								aria-hidden="true">x</button>
							<strong>${mensaje}</strong>
							<%-- <%=error%> --%>
						</div>
						</c:if>
						
							<c:if test="${!empty errorLogin}">
						<div class="alert alert-warning alert-dismissable">
							<button type="button" class="close" data-dismiss="alert"
								aria-hidden="true">x</button>
							<strong>${errorLogin}</strong>
							<%-- <%=error%> --%>
						</div>
						</c:if>
					
<!-- Fin Mensaje Error/Exito <span class="badge badge-danger">Peña}</span> -->
						
						
	<div class="container">
	
							
		<table class="table table-md-6 table-striped table-inverse">
			<thead>
				<tr>
					<th class="">Temática habitacion</th>
					<th class="">Fecha Entrada</th>
					<th class="">Fecha Salida</th>
					<th class="">Precio </th>
					<th class="">Tipo Pago</th>

				</tr>
			</thead>
			<tbody>
					<tr>
					
					<c:url value="/confirmarReserva" var="reserva"/>
					
					<f:form class="form-horizontal" commandName="reserva" method="post" action="${reserva}">
						<td>${habitacion.tematica}<input name="idHabitacion" type="hidden" value="${habitacion.idHabitacion}"/>  </td>
						<td><input type="date" id="fechaEntrada" name="fechaEntrada" required="required" /></td>
						<td><input type="date" id="fechaSalida" name ="fechaSalida" required="required" /></td>
						<td>${habitacion.precio}<input name="precioApagar" type="hidden" required="required" value="${habitacion.precio}"/></td>
						
						<td>
							<select name="tipoPago">
  								<option value="Efectivo">Efectivo</option>
 					 			<option value="Tarjeta">Tarjeta</option>
  								<option value="Paypal">Paypal</option>
  								<option value="Cheque">Cheque</option>
							</select>
								<button type="submit" class="btn btn-success">Reservar</button>
						</td>
					
						</f:form>
					</tr>			
			</tbody>
		</table>	
		
		
	</div>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	<!-- Optional JavaScript -->
	<!-- JQquery first, then Popper.js, the Bootsrap JS -->
	<script src="<c:url value="/resources/js/jquery-3.2.1.slim.min.js" />"></script>
	<script src="<c:url value="/resources/js/popper.min.js" />"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>

	<script type="text/javascript">

	$('.carousel[data-type="multi"] .item').each(function(){
		  var next = $(this).next();
		  if (!next.length) {
		    next = $(this).siblings(':first');
		  }
		  next.children(':first-child').clone().appendTo($(this));
		  
		  for (var i=0;i<2;i++) {
		    next=next.next();
		    if (!next.length) {
		    	next = $(this).siblings(':first');
		  	}
		    
		    next.children(':first-child').clone().appendTo($(this));
		  }
		});
	
	
	
	
	
	
	  
	
	
	
	
	
	</script>
	
</body>
</html>
