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
<body style="background-repeat: no-repeat;background-image: url('https://1.bp.blogspot.com/-uifnlBVtbY0/WcM3yIUQ-MI/AAAAAAAABaM/Vz3kOUCnl6U1zgz4k8zKuFFDh0nREn6gQCLcBGAs/s1600/Tomorrowland-Theme-Hotel.jpg'); background-attachment: fixed;">
	
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
					
					
					<li class="nav-item active"><a class="nav-link"
					href="contabilidad">Contabilidad</a></li>
					
					
					<li class="nav-item active"><a style="color: silver;" class="nav-link"
					href="datosClientes">Clientes</a></li>
					
					<li class="nav-item active"><a style="color: silver;" class="nav-link"
					href="datosTrabajadores">Trabajadores</a></li>
				
					
			</ul>
			
			

		
			
					<a href="cerrarSesionTrabajador" role="button"
			class="btn btn-link btn-sm derecha">cerrar Sesion</a>  
			<!-- Formulario Entrar -->
			
		
		</div>

	</nav>
	
	
	
	
	<div class="container">
	
	
		<div class="row main">
			<div class="panel-heading">
				<div class="panel-title text-center">
					<h1 class="title"></h1>
					<hr />
				</div>
			</div>
		
			<div class="main-login main-center">
				<form action="actualizarPerfilUsu" class="form-horizontal" method="POST" >

					<div class="form-group">
						<label for="name" class="cols-sm-2 control-label" style="color: white;">Escribe tu nombre
							</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-user fa"
									aria-hidden="true"></i></span> <input type="text" class="form-control"
									name="nombre" id="nombre" value="${trabajadorCambiar.nombre}" required="required" placeholder="Escribe tu nombre" />
							</div>
						</div>
					</div>
					<input type="hidden" hidden="hidden" class="form-control" name="idTrabajador" id="idTrabajador" value="${trabajadorCambiar.idTrabajador}" />
					<div class="form-group">
					<!-- AQUI PONIA EMAIL EN EL FOR EN LUGAR DE USERNAME -->
						<label for="username" class="cols-sm-2 control-label"  style="color: white;">Escribe tus Apellidos
							</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="far fa-user"></i></span> <input
									type="text" required="required" class="form-control" value="${trabajadorCambiar.apellidos}" name="apellidos" id="apellidos"
									placeholder="Escribe tus apellidos" />
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="idioma" class="cols-sm-2 control-label"  style="color: white;">Escribe tu idioma
							</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="far fa-user"></i></span> <input
									type="text" required="required" class="form-control" value="${trabajadorCambiar.idioma}" name="idioma" id="idioma"
									placeholder="Escribe tu idioma" />
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="username" class="cols-sm-2 control-label"  style="color: white;">Edad</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-users fa"
									aria-hidden="true"></i></span> <input type="number" class="form-control"
									name="edad" id="edad" required="required" value="${trabajadorCambiar.edad}"  min="18" max="99" placeholder="Escribe tu edad" />
							</div>
						</div>
					</div>
					
					<c:if test="${trabajadorLogin.tipoAcceso == 2 }">
					
					<div class="form-group">
						<label for="username" class="cols-sm-2 control-label"  style="color: white;">Tipo Acceso</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-users fa"
									aria-hidden="true"></i></span> <input type="number" class="form-control"
									name="tipoAcceso" id="tipoAcceso" required="required" value="${trabajadorCambiar.tipoAcceso}"  min="1" max="2" placeholder="Escribe tipo Acceso" />
							</div>
						</div>
					</div>
				</c:if>
					
					<c:if test="${trabajadorLogin.idTrabajador == trabajadorCambiar.idTrabajador }">
					<div class="form-group">
						<label for="password" class="cols-sm-2 control-label"  style="color: white;">Contraseña</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i
									class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
									type="password" value="${trabajadorCambiar.password}" required="required" class="form-control" name="password"
									id="password" placeholder="Escribe tu contraseña" />
							</div>
						</div>
					</div>
					
					</c:if>

					<div class="form-group ">
					<button type="submit" class="btn btn-primary btn-lg btn-block login-button">Actualizar Perfil </button>
						
					</div>
				
				</form>
			</div>
		</div>
	
		
	</div>

	<script type="text/javascript" src="assets/js/bootstrap.js"></script>





	<!-- Optional JavaScript -->
	<!-- JQquery first, then Popper.js, the Bootsrap JS -->
	<script src="../js/jquery-3.2.1.slim.min.js"></script>
	<script src="../js/popper.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
</body>
</html>