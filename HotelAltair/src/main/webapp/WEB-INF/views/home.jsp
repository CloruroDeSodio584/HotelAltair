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

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Bootstrap core CSS -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
<!-- Material Design Bootstrap -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.5.0/css/mdb.min.css" rel="stylesheet">

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
					href="">Inicio</a></li>
			</ul>
			
			<a href="registrar" role="button"
			class="btn btn-link btn-sm derecha">registrar</a> 
			<!-- Formulario Entrar -->
			
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

<!--  
<div class="row" style="padding-left: 40px">
		 <div class="col-sm-6 col-md-4 col-lg-3 mt-4">
                <div class="card card-inverse card-info">
                    <img class="card-img-top" src="<c:url value="/resources/images/habitaciones-star-wars.jpg" />">
                    <div class="card-block">
                        <figure class="profile profile-inline">
                          
                        </figure>
                        <h4 class="card-title">Habitacion temática star wars</h4>
                        <div class="card-text">
                            Sientete como un auténtico Jedi con nuestra habitacion dedicada al universo Star Wars
                        </div>
                    </div>
                    <div class="card-footer">
                        <button class="btn btn-info btn-sm">Reservar</button>
                    </div>
                </div>
            </div>
            
             <div class="col-sm-6 col-md-4 col-lg-3 mt-4">
                <div class="card card-inverse card-info">
                    <img class="card-img-top" src="<c:url value="/resources/images/habitacio-batman.jpg" />">
                    <div class="card-block">
                        <figure class="profile profile-inline">
                          
                        </figure>
                        <h4 class="card-title">Habitacion temática Batman</h4>
                        <div class="card-text">
                            Cae la noche sobre la ciudad de Gotham y que mejor manera de descansar con la habitacion dedicada al Caballero Oscuro
                        </div>
                    </div>
                    <div class="card-footer">
                        <button class="btn btn-info btn-sm">Reservar</button>
                    </div>
                </div>
            </div>
            
            <div class="col-sm-6 col-md-4 col-lg-3 mt-4">
                <div class="card card-inverse card-info">
                    <img class="card-img-top" src="<c:url value="/resources/images/pirata-habitacion.jpg" />">
                    <div class="card-block">
                        <figure class="profile profile-inline">
                          
                        </figure>
                        <h4 class="card-title">Habitacion temática Piratas del Caribe</h4>
                        <div class="card-text">
                           Que mejor manera de sentirse un pirata Con la habitacion dedicada al Pirata mas Buscado
                        </div>
                    </div>
                    <div class="card-footer">
                        <button class="btn btn-info btn-sm">Reservar</button>
                    </div>
                </div>
            </div>
            
            </div>
            -->
            
            
            
            
           
           <!-- 	<table class="table table-md-6 table-striped table-inverse">
			<thead>
				<tr>
					<th class="">Titulo Juego</th>
					<th class="">Num Jugadores</th>
					<th class="">Descripcion</th>
					<th class="">Pegi</th>

				</tr>
			</thead>
			<tbody>
			
				<c:forEach items="${listarH}" var="h">
					<tr>
						<td>${h.tematica }</td>
						<td>${h.precio }</td>		
					</tr>
				</c:forEach> 
			</tbody>
		</table> --> 


<!-- Tabla De Habitaciones -->
	<div class="container">
		<c:if test="${!empty listarH}"> 
		<div class="row" style="padding-left: 40px">
			<c:forEach items="${listarH}" var="h">
					
					<!-- ?idHabitacion=h.getIdHabitacion() -->
					
			<c:if test="${h.tematica.equals('Star Wars') }">
			
			<div class="col-sm-6 col-md-4 col-lg-3 mt-4">
                <div class="card card-inverse card-info">
                    <img class="card-img-top" src="<c:url value="/resources/images/habitaciones-star-wars.jpg" />">
                    <div class="card-block">
                        <figure class="profile profile-inline">
                          
                        </figure>
                        <h4 class="card-title">Habitacion temática star wars</h4>
                        <div class="card-text">
                            Sientete como un auténtico Jedi con nuestra habitacion dedicada al universo Star Wars
                        </div>
                    </div>
                    <div class="card-footer">
                        <button value="${h.idHabitacion}" name="idhabitacion" onclick="location.href='reserva?idHabitacion=${h.getIdHabitacion()}'" class="btn btn-info btn-sm">Reservar por ${h.precio } $</button>
                    </div>
                </div>
            </div>
	
			</c:if>
		<c:if test="${h.tematica.equals('Batman') }">
			<div class="col-sm-6 col-md-4 col-lg-3 mt-4">
                <div class="card card-inverse card-info">
                    <img class="card-img-top" src="<c:url value="/resources/images/habitacio-batman.jpg" />">
                    <div class="card-block">
                        <figure class="profile profile-inline">
                          
                        </figure>
                        <h4 class="card-title">Habitacion temática Batman</h4>
                        <div class="card-text">
                           Cae la noche y que mejor manera de descansar con la habitacion dedicada al Caballero Oscuro
                        </div>
                    </div>
                    <div class="card-footer">
                        <button onclick="location.href='reserva'" class="btn btn-info btn-sm">Reservar por ${h.precio } $</button>
                    </div>
                </div>
            </div>
			
			</c:if>
					
				<c:if test="${h.tematica.equals('Piratas del Caribe') }">	
			
					<div class="col-sm-6 col-md-4 col-lg-3 mt-4">
                <div class="card card-inverse card-info">
                    <img class="card-img-top" src="<c:url value="/resources/images/pirata-habitacion.jpg" />">
                    <div class="card-block">
                        <figure class="profile profile-inline">
                          
                        </figure>
                        <h4 class="card-title">Habitacion temática Piratas del Caribe</h4>
                        <div class="card-text">
                           Que mejor manera de sentirse un pirata Con la habitacion dedicada al Pirata mas Buscado                 
                        </div>
                    </div>
                    <div class="card-footer">
                        <button onclick="location.href='reserva'" class="btn btn-info btn-sm">Reservar por ${h.precio } $</button>
                    </div>
                </div>
            </div>
					
				</c:if>
			
			
				
				</c:forEach> 
	</div>
	
		</c:if> 
	</div>


	<!-- FIN Tabla De Juegos -->
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
