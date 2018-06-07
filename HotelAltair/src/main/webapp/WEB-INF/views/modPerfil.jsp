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
	<div class="container">
	
	
		<div class="row main">
			<div class="panel-heading">
				<div class="panel-title text-center">
					<h1 class="title"></h1>
					<hr />
				</div>
			</div>
		
			<div class="main-login main-center">
				<form action="actualizarPerfil" class="form-horizontal" method="POST" >

					<div class="form-group">
						<label for="name" class="cols-sm-2 control-label" style="color: white;">Escribe tu nombre
							</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-user fa"
									aria-hidden="true"></i></span> <input type="text" class="form-control"
									name="nombre" id="nombre" value="${clienteLogin.nombre}" required="required" placeholder="Escribe tu nombre" />
							</div>
						</div>
					</div>
					<input type="hidden" hidden="hidden" class="form-control" name="idCliente" id="idCliente" value="${clienteLogin.idCliente}" />
					<div class="form-group">
					<!-- AQUI PONIA EMAIL EN EL FOR EN LUGAR DE USERNAME -->
						<label for="username" class="cols-sm-2 control-label"  style="color: white;">Escribe tus Apellidos
							</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="far fa-user"></i></span> <input
									type="text" required="required" class="form-control" value="${clienteLogin.apellidos}" name="apellidos" id="apellidos"
									placeholder="Escribe tus apellidos" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="username" class="cols-sm-2 control-label"  style="color: white;">Edad</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-users fa"
									aria-hidden="true"></i></span> <input type="number" class="form-control"
									name="edad" id="edad" required="required" value="${clienteLogin.edad}"  min="18" max="99" placeholder="Escribe tu edad" />
							</div>
						</div>
					</div>
					
					<c:if test="${empty trabajadorLogin}">
					<div class="form-group">
						<label for="password" class="cols-sm-2 control-label"  style="color: white;">Contraseña</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i
									class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
									type="password" required="required" class="form-control" name="password"
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