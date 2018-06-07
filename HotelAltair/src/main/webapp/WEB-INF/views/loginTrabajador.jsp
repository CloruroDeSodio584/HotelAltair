<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="t"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ page session="false"%>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

    <script src="https://use.typekit.net/ayg4pcz.js"></script>
    <script>try{Typekit.load({ async: true });}catch(e){}</script>
    
    
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

   <div style="padding-top: 150px;" class="container">
	<div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Inicio Sesión</h3>
                </div>
                <div class="panel-body">
                    <f:form action="entrarTrabajador" commandName="tra" method="POST">
                        <fieldset>
                            <div class="form-group">
                               <f:input path="correo" type="email" id="correo" name="correo" required="required"
							class="form-control" placeholder="Correo" />
                            </div>
                            <div class="form-group">
                              <f:input path="password" type="password" id="password" name="password"
							required="required" class="form-control" placeholder="Password" />
                            </div>
                        
                            <input type="submit" class="btn btn-success btn-block"></button>
                        </fieldset>
                    </f:form>
                </div>
            </div>
        </div>
    </div>
</div>



<script src="<c:url value="/resources/js/jquery-3.2.1.slim.min.js" />"></script>
	<script src="<c:url value="/resources/js/popper.min.js" />"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>