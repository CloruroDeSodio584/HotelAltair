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

<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" />

<link href="/resources/css/CSS_Propio.css" rel="stylesheet" />

<link rel="stylesheet"
	href="<c:url value="/resources/css/fonts/OLD/font-awesome.css" />">
<script defer
	src="https://use.fontawesome.com/releases/v5.0.6/js/all.js"></script>

</head>

<body class="image-container set-full-height">

<div style="background-repeat: no-repeat;background-image: url('http://demos.creative-tim.com/material-bootstrap-wizard/assets/img/wizard-book.jpg'); background-attachment: fixed;">
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
					href="">Inicio</a></li>
					
					<c:if test="${trabajadorLogin != null}">
					<li class="nav-item active"><a class="nav-link"
					href="contabilidad">Contabilidad</a></li>
					
					
					<li class="nav-item active"><a style="color: silver;" class="nav-link"
					href="datosClientes">Clientes</a></li>
					
					<li class="nav-item active"><a style="color: silver;" class="nav-link"
					href="datosTrabajadores">Trabajadores</a></li>
					</c:if>
					
			</ul>
			
			
			
			<a href="registrar" role="button"
			class="btn btn-link btn-sm derecha">Registrar</a> 
		
			<c:if test="${trabajadorLogin != null}">
					<a href="cerrarSesionTrabajador" role="button"
			class="btn btn-link btn-sm derecha">cerrar Sesion</a>  </c:if>
			<!-- Formulario Entrar -->
			
			<c:if test="${trabajadorLogin == null}"> 
			
			<f:form action="entrar" class="form-inline" commandName="cli" method="POST">
				<div class="form-group">
					<div class="input-group input-group-sm mb-2 mr-sm-2 mb-sm-0">
						<div class="input-group-addon">

							<i class="far fa-envelope" aria-hidden="true"></i>
						</div>
						<f:input path="correo" type="email" id="correo" name="correo" required="required"
							class="form-control" placeholder="Correo" />
					</div>

			


					<div class="input-group input-group-sm mb-2 mr-sm-2 mb-sm-0">
						<div class="input-group-addon">
							<i class="fa fa-key" aria-hidden="true"></i>
						</div>
						<f:input path="password" type="password" id="password" name="password"
							required="required" class="form-control" placeholder="Password" />
					</div>
					<input type="submit" class="btn btn-outline-success btn-sm mr-1"
						value="Entrar">
				</div>
			</f:form>
			</c:if>
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
					<img class="d-block w-100" src="<c:url value="/resources/images/elite-hotel-2.jpg" />" alt="First slide">
					<div class="carousel-caption d-none d-md-block">
						<h5>Comedor Principal</h5>
						<p>Disfruta de la gastronomía de nuestros mejores chef siempre acompañada de nuestro show de Jazz</p>
					</div>
				</div>

				<div class="carousel-item">
					<img class="d-block w-100" src="<c:url value="/resources/images/hotel-elite-8.jpg" />"
						alt="Second slide">
					<div class="carousel-caption d-none d-md-block">
						<h5>Patio de Zeus</h5>
						<p>Un lugar perfecto para relajarse con estética griega</p>
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

<!-- fin carousel -->

<!-- Tabla De Habitaciones -->
	<div class="container" >
		<c:if test="${!empty listarH}"> 
		<div class="row" style="padding-left: 40px">
			<c:forEach items="${listarH}" var="h">
					
					
			
		<c:if test="${h.tematica.equals('Habitacion Batman')}">
			<div class="col-sm-6 col-md-4 col-lg-3 mt-4">
                <div class="card card-inverse card-info">
                    <img class="card-img-top" src="<c:url value="/resources/images/habitacio-batman.jpg" />">
                    <div class="card-block">
                        <figure class="profile profile-inline">
                          
                        </figure>
                        <h4 class="card-title">Habitacion temática Batman</h4>
                        <div class="card-text">
                           Cada una de ellas está equipada con aire acondicionado, televisión, antena parabólica, minibar, caja fuerte individual, conexión a Internet, hilo musical, etc...
                        </div>
                    </div>
                    <div class="card-footer">
                        <button value="${h.idHabitacion}"  onclick="location.href='reservaTrabajador?idHabitacion=${h.getIdHabitacion()}'" class="btn btn-info btn-sm">Reservar</button>
                    </div>
                </div>
            </div>
			
			</c:if>
					
				
				
					
				<c:if test="${h.tematica.equals('Superior Piratas del Caribe')}">	
			
					<div class="col-sm-6 col-md-4 col-lg-3 mt-4">
                <div class="card card-inverse card-info">
                    <img class="card-img-top" src="<c:url value="/resources/images/pirata-habitacion.jpg" />">
                    <div class="card-block">
                        <figure class="profile profile-inline">
                          
                        </figure>
                        <h4 class="card-title">Habitacion Superior Piratas del Caribe</h4>
                        <div class="card-text">
                           Las habitaciones superiores que son muy amplias cuentan además con un saloncito y con baño con bañera hidromasaje, calentador de toallas, etc..                 
                        </div>
                    </div>
                    <div class="card-footer">
                        <button value="${h.idHabitacion}"  onclick="location.href='reservaTrabajador?idHabitacion=${h.getIdHabitacion()}'" class="btn btn-info btn-sm">Reservar</button>
                    </div>
                </div>
            </div>
					
				</c:if>
			
			
				<c:if test="${h.tematica.equals('Suite Star Wars')}">
			
			<div class="col-sm-6 col-md-4 col-lg-3 mt-4">
                <div class="card card-inverse card-info">
                    <img class="card-img-top" src="<c:url value="/resources/images/habitaciones-star-wars.jpg" />">
                    <div class="card-block">
                        <figure class="profile profile-inline">
                          
                        </figure>
                        <h4 class="card-title">Suite star wars</h4>
                        <div class="card-text">
 Las Suites Cuentan con una mayor inmersión en la temática ademas de incluir con espectáculos relacionados                                  </div>
                    </div>
                    <div class="card-footer">
                        <button value="${h.idHabitacion}" name="idhabitacion" onclick="location.href='reservaTrabajador?idHabitacion=${h.getIdHabitacion()}'" class="btn btn-info btn-sm">Reservar</button>
                    </div>
                </div>
            </div>
	
			</c:if>				
				
			
				</c:forEach> 
	</div>
		</c:if> 
	
	</div>
	
	<!-- Footer -->
	
	<footer style="background-color: #1C1C1C" class="page-footer font-small blue pt-4 mt-4">
  <!-- Copyright -->
  <div class="footer-copyright text-center py-3" style="color: yellow;">© 2018 Copyright:
    <a href="" > hotelAltair Company</a>
  </div>

</footer>

	



                
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
	</div>
</body>
</html>
