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
<body background="../images/top.jpg">
	<div class="container">
	
	
		<div class="row main">
			<div class="panel-heading">
				<div class="panel-title text-center">
					<h1 class="title"></h1>
					<hr />
				</div>
			</div>
		
			<div class="main-login main-center">
				<f:form action="registrarse" class="form-horizontal" commandName="reg" method="POST" >

					<div class="form-group">
						<label for="name" class="cols-sm-2 control-label">Escribe tu nombre
							</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-user fa"
									aria-hidden="true"></i></span> <input type="text" class="form-control"
									name="nombre" id="nombre" required="required" placeholder="Escribe tu nombre" />
							</div>
						</div>
					</div>

					<div class="form-group">
					<!-- AQUI PONIA EMAIL EN EL FOR EN LUGAR DE USERNAME -->
						<label for="username" class="cols-sm-2 control-label">Escribe tus Apellidos
							</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="far fa-user"></i></span> <input
									type="text" required="required" class="form-control" name="apellidos" id="apellidos"
									placeholder="Escribe tus apellidos" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="username" class="cols-sm-2 control-label">Edad</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-users fa"
									aria-hidden="true"></i></span> <input type="number" class="form-control"
									name="edad" id="edad" required="required"  min="14" max="99" placeholder="Escribe tu edad" />
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="confirm" class="cols-sm-2 control-label">Correo 
							</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fas fa-envelope"></i></span> <input
									type="email" required="required" class="form-control" name="correo"
									id="correo" placeholder="Correo" />
							</div>
						</div>
					</div>

					<div class="form-group">
						<label for="password" class="cols-sm-2 control-label">Contraseña</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i
									class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
									type="password" required="required" class="form-control" name="password"
									id="password" placeholder="Escribe tu contraseña" />
							</div>
						</div>
					</div>

					<div class="form-group ">
					<button type="submit" class="btn btn-primary btn-lg btn-block login-button">Registrar </button>
						
					</div>
				
				</f:form>
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