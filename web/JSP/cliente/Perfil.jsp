<%-- 
    Document   : Perfil
    Created on : Dec 21, 2019, 10:15:03 PM
    Author     : jela3
--%>

<%@page import="Clases.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Servicios</title>
        <link rel="icon" href="../../Img/Techno-bit.png">
        <!-- Compiled and minified CSS -->
        <link rel="stylesheet" href="../../Css/materialize.min.css">
        <link rel="stylesheet" href="../../Css/Estilos.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!-- Compiled and minified JavaScript -->
        <script src="../../JS/materialize.min.js"></script>  
        <script src="../../JS/Efectos.js"></script>
    </head>
    <body onload="javascript:abrirModal('<%= request.getParameter("modal")%>')">
        <header>
            <nav>
                <div class="nav-wrapper red">
                    <a href="" class="brand-logo">Can-Contento</a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <li class=""><a href="home.jsp">Home</a></li>
                        <li class=""><a href="../../JSP/cliente/Servicios.jsp">Servicios</a></li>
                        <li class="active"><a href="Perfil.jsp">Mi perfil</a></li>
                        <li class=""><a href="Notificaciones.jsp">Notificaciones</a></li>
                    </ul>
                </div>
            </nav>
        </header>
        <section>
            <div class="container">
                <%if (request.getSession().getAttribute("correo") != null) {%>
                <div class="container">
                    <div class="row">
                        <h3>DATOS DE USUARIO: </h3>

                        <% Usuario usuario = new Usuario();
                            usuario = (Usuario) request.getSession().getAttribute("datoscliente");
                        %>
                        <p><h5>Nombre:</h5> <%=usuario.getNombre()%></p>
                        <p><h5>Apellido: </h5><%=usuario.getApellido()%></p>
                        <p><h5>Telefono:</h5> <%=usuario.getTelefono()%></p>
                        <p><h5>Dirección:</h5> <%= usuario.getDireccion()%> </p>
                        <p><h5>Correo electronico: </h5> <%= request.getSession().getAttribute("correo").toString()%> </p>

                    </div>
                    <a href="Perfil.jsp?modal=Editardatos"><input type="button" value="Editar datos" class="btn-small"></a>
                </div>
                <%
                } else {
                %>
                <div class="altura">
                    <h3> NO HAY SESION INICIADA</h3>
                    <p class="red-text">Inicia sesión nuevamente para poder acceder a todos nuestros servicios</p>
                </div>
                <%
                    }
                %>
            </div>
        </section>
        <section>
            <div id="Editardatos" class="modal">
                <div class="modal-content">
                    <% Usuario usuario = new Usuario();
                        usuario = (Usuario) request.getSession().getAttribute("datoscliente");
                    %>
                    <h2> Editar datos: </h2>

                    <form action="..\..\Cliente" method="post">
                        <div class="row">
                            <div class="input-field col s12 m12">
                                <input id="nomusu" name="nombre" type="text" value="<%=usuario.getNombre()%>">
                                <label for="nomusu">Nombre</label>
                            </div>                                                                                                               
                        </div> 
                        <div class="row">
                            <div class="input-field col s12 m12">
                                <input id="apellido" name="apellido" type="text" value="<%=usuario.getApellido()%>">
                                <label for="apellido">Apellido</label>
                            </div>                                                                                                               
                        </div>
                        <div class="row">
                            <div class="input-field col s12 m12">
                                <input id="telefono" name="telefono" type="text" value="<%=usuario.getTelefono()%>">
                                <label for="telefono">Telefono</label>
                            </div>                                                                                                               
                        </div>
                        <div class="row">
                            <div class="input-field col s12 m12">
                                <input id="direccion" name="direccion" type="text" value="<%=usuario.getDireccion()%>">
                                <label for="direccion">Dirección</label>
                            </div>                                                                                                               
                        </div>
                        <input type="hidden" value="editarDatos" name="action">
                        <input type="hidden" value="page" name="place">
                        <input type="submit" value="Editar" class="btn-small yellow darken-2">
                    </form>
                </div>
            </div>
        </section>
    </body>
    <footer class="page-footer grey darken-3">
        <div class="container">
            <div class="row">
                <div class="col l6 s12">
                    <h5 class="white-text">Dirección</h5>
                    <p class="grey-text text-lighten-4">Golfo de Campeche # 51, Col. Tacuba 11410 Ciudad de México</p>
                    <h5 class="white-text">Número de contacto</h5>
                    <p class="grey-text text-lighten-4">Teléfono: 55 1295 6883</p>
                </div>
                <div class="col l4 offset-l2 s12">
                    <h5 class="white-text">Links</h5>
                    <ul>
                        <li><a class="grey-text text-lighten-3" href="https://www.facebook.com/CanContentoMascotas/"><img src="../../Img/facebook.png" style="width: 50px"></a></li>
                        <li class="grey-text text-lighten-3">Facebook</li>
                        <li><a class="grey-text text-lighten-3" href="#!"><img src="../../Img/youtube.png" style="width: 50px"></a></li>
                        <li class="grey-text text-lighten-3">Youtube</li>
                        <li><a class="grey-text text-lighten-3" href="#!"><img src="../../Img/twitter.png" style="width: 50px"></a></li>        
                        <li class="grey-text text-lighten-3">Twitter</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="footer-copyright">
            <div class="container">
                <p class="copy">&copy; Techno-Bit</p>                   
            </div>
        </div>
    </footer>
</html>
