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

<div style="background-repeat: no-repeat;background-image: url('http://demos.creative-tim.com/material-bootstrap-wizard/assets/img/wizard-book.jpg'); background-attachment: fixed;">

	<!-- Inicio -->
	<nav class="navbar navbar-expand-md navbar-dark bg-dark">
		<figure class="figure mt-0 mb-0">
			<a class="navbar-brand" href=""><img
				src="<c:url value="/resources/images/hotel.png" />"
				class="figure-img img-fluid rounded" alt="Logo"
				style="height: 40px;"></a>
		</figure>

		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarExample" arial-controls="navbarExample"
			arial-expanded="false" arial-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<c:if test="${empty trabajadorLogin }">
		<div class="collapse navbar-collapse" id="navbarExample">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link" href="homet">Inicio</a></li>


				<li class="nav-item active"><a class="nav-link"
					href="misReservas">Mis reservas</a></li>
					
					<li class="nav-item active"><a style="color: silver;" class="nav-link"
					href="modPerfil">Modificar Perfil</a></li>


			</ul>

		</div>
		<a href="cerrarSesion" role="button"
			class="btn btn-link btn-sm derecha">cerrar Sesion</a>
		</c:if>
		
		<c:if test="${!empty trabajadorLogin }">
		
		<div class="collapse navbar-collapse" id="navbarExample">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link" href=homeTrabajador>Inicio</a></li>


				<li class="nav-item active"><a class="nav-link"
					href="contabilidad">Contabilidad</a></li>
					
					<li class="nav-item active"><a style="color: silver;" class="nav-link"
					href="datosClientes">Clientes</a></li>
					
					<li class="nav-item active"><a style="color: silver;" class="nav-link"
					href="datosTrabajadores">Trabajadores</a></li>
			</ul>

		</div>
		<a href="cerrarSesionTrabajador" role="button"
			class="btn btn-link btn-sm derecha">cerrar Sesion</a>
		
		
		</c:if>
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

	<!-- Fin Mensaje Error/Exito -->



	<!-- inicio carousel -->

	<div class="mt-5" data-toggle="collapse">

		<div id="carouselExampleIndicators" class="carousel slide"
			data-ride="carousel">

			<ol class="carousel-indicators">
				<li data-target="#carouselExampleIndicators" data-slide-to="0"
					class="active"></li>
				<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
				<!-- <li data-target="#carouselExampleIndicators" data-slide-to="2"></li> -->
			</ol>
			<div class="carousel-inner classBack">
				<div class="carousel-item active">
					<img class="d-block w-100"
						src="<c:url value="/resources/images/pecine.jpg" />"
						alt="First slide">
					<div class="carousel-caption d-none d-md-block">
						<h5>Sotano Cueva</h5>
						<p>Un largo camino que conecta nuestras instalaciones</p>
					</div>
				</div>

				<div class="carousel-item">
					<img class="d-block w-100"
						src="<c:url value="/resources/images/anden.jpg" />"
						alt="Second slide">
					<div class="carousel-caption d-none d-md-block">
						<h5>Proxima apertura</h5>
						<p>Proximamente nueva apertura en China</p>
					</div>
				</div>
			</div>
			<a class="carousel-control-prev" href="#carouselExampleIndicators"
				role="button" data-slide="prev"> <span
				class="carousel-control-prev-icon" aria-hidden="true"></span> <span
				class="sr-only">Previous</span>
			</a> <a class="carousel-control-next" href="#carouselExampleIndicators"
				role="button" data-slide="next"> <span
				class="carousel-control-next-icon" aria-hidden="true"></span> <span
				class="sr-only">Next</span>
			</a>
		</div>

	</div>


	<!-- Fin Carrousel -->


	<!-- Tabla de Habitaciones -->

	<div style="padding-top: 15px" class="container">
		<c:if test="${!empty listarR}">
			<table class="table table-md-6 table-striped table-inverse">
				<thead>
					<tr>
						<th class="">Tematica Habitacion</th>
						<th class="">Fecha Entrada</th>
						<th class="">Fecha Salida</th>
						<th class="">Precio</th>
						<th class="">Tipo Pago</th>
						<c:if test="${!empty trabajadorLogin }">
						<th class="">Trabajador Reserva</th>
						<th class="">uuid</th>
						</c:if>

					</tr>
				</thead>
				<tbody>

					<c:forEach items="${listarR}" var="r">
						<tr>
							<td>${r.getHabitacion().getTematica() }</td>
							<td>${r.fechaEntrada }</td>
							<td>${r.fechaSalida }</td>
							<td>${r.precioAPagar } Euros</td>
							<td>${r.tipoPago }</td>
							<c:if test="${!empty trabajadorLogin }">
							<td>${r.getTrabajador().getNombre() } ${r.getTrabajador().getApellidos() }</td>
							<td>${r.uuid}</td>
							</c:if>


							<td>

								<button type="button" class="btn btn-primary"
									data-toggle="modal" data-target="#cancelarReserva${r.idReserva }">
									Cancelar Reserva</button> 
									
									
									<button type="button" class="btn btn-dark">Solicitar factura</button>
									
									
									<!-- Modal -->
								<div class="modal fade" id="cancelarReserva${r.idReserva }" tabindex="-1"
									role="dialog" aria-labelledby="exampleModalLabel"
									aria-hidden="true">
									<div class="modal-dialog" role="document">
										<div class="modal-content">
											<div class="modal-header">
												<h5  style="color: black;" class="modal-title" id="exampleModalLabel">Cancelar
												</h5>
												<button type="button" class="close" data-dismiss="modal"
													aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
											</div>
											<div style="color: black;" class="modal-body">�Esta seguro de cancelar la reserva ${r.getHabitacion().getTematica()}?</div>
											<div class="modal-footer">
												<button style="color: black;" type="button" class="btn btn-secondary"
													data-dismiss="modal">No</button>
												<button style="color: black;" type="button" class="btn btn-primary" onclick="location.href='cancelarReserva?idReserva=${r.uuid}&idCliente=${r.getCliente().getUuid()}'">Si
													</button>
											</div>
										</div>
									</div>
								</div>

							</td>


						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
	
		<!-- Footer -->
	
	<footer style="background-color: #1C1C1C" class="page-footer font-small blue pt-4 mt-4">
  <!-- Copyright -->
  <div class="footer-copyright text-center py-3" style="color: yellow;">� 2018 Copyright:
    <a href="" > hotelAltair Company</a>
  </div>

</footer>


	<!-- Fin tabla de Habitaciones -->
















	<!-- Optional JavaScript -->
	<!-- JQquery first, then Popper.js, the Bootsrap JS -->
	<script src="<c:url value="/resources/js/jquery-3.2.1.slim.min.js" />"></script>
	<script src="<c:url value="/resources/js/popper.min.js" />"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>

	<script type="text/javascript">
		$('.carousel[data-type="multi"] .item').each(function() {
			var next = $(this).next();
			if (!next.length) {
				next = $(this).siblings(':first');
			}
			next.children(':first-child').clone().appendTo($(this));

			for (var i = 0; i < 2; i++) {
				next = next.next();
				if (!next.length) {
					next = $(this).siblings(':first');
				}

				next.children(':first-child').clone().appendTo($(this));
			}
		});
	</script>
	
	</div>

</body>
</html>
