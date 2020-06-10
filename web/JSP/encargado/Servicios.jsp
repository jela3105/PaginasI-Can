<%-- 
    Document   : servicios
    Created on : Jun 9, 2020, 2:41:48 PM
    Author     : jela3
--%>

<%@page import="Clases.Servicio"%>
<%@page import="Clases.Producto"%>
<%@page import="Clases.Perro"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Clases.Cita"%>
<%@page import="Clases.Cita"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
            <nav class="responsive-table red">
                <div class="nav-wrapper">
                    <a href="home.jsp" class="brand-logo">Can-Contento</a>
                    <a href="#" data-target="mobile-demo" class="sidenav-trigger"><i class="material-icons">menu</i></a>

                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <li class=""><a  href="home.jsp">Home</a></li>
                        <li class="active"><a href="Servicios.jsp">Servicios</a></li>
                        <li class=""><a href="Notificaciones.jsp">Notificaciones</a></li>
                    </ul>
                </div>

            </nav>
            <ul id="mobile-demo" class="sidenav">
                <li class="active"><a  href="home.jsp">Home</a></li>
                <li class="menu__item"><a href="Servicios.jsp">Servicios</a></li>
                <li class="menu__item"><a href="Perfil.jsp">Mi perfil</a></li>
                <li class="menu__item"><a href="Notificaciones.jsp">Notificaciones</a></li>
            </ul>
        </header> 
        <section>
            <div class="container">
                <div class="row z-depth-5">
                    <h4>SERVICIOS</h4>

                    <%
                        ArrayList<Servicio> servicios = (ArrayList<Servicio>) request.getSession().getAttribute("servicios");
                        if (servicios != null) {
                            for (int i = 0; i < servicios.size(); i++) {

                    %>
                    <div class="col s6 m6 l3 ">
                        <div class="card small hoverable">
                            <div class="card-image waves-effect waves-block waves-light">
                                <img class="activator" src="../../Img/servicios/<%=servicios.get(i).getNombreservicio().replace(" ", "")%>.jpg" >
                            </div>
                            <div class="card-content">
                                <span class="card-title activator grey-text text-darken-4"><%=servicios.get(i).getNombreservicio()%><i class="material-icons right">more_vert</i></span>
                                <p><a href="Servicios.jsp?modal=Editarservicio&servicio=<%=servicios.get(i).getNombreservicio()%>">Editar servicio</a></p>
                            </div>
                            <div class="card-reveal">
                                <span class="card-title grey-text text-darken-4"><%=servicios.get(i).getNombreservicio()%><i class="material-icons right">close</i></span>
                                <p><%= servicios.get(i).getDescripcion()%></p>
                                <hr>

                                <%String[] arrayPrecios = servicios.get(i).getPrecio().split(";");
                                    for (int f = 0; f < arrayPrecios.length; f++) {

                                %>   

                                <p><%= arrayPrecios[f]%></p>
                                <Br>
                                <%
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                    <%                    }
                        } else {
                            out.print("<p class=\"red background white-text\">No hay servicios</p>");
                        }
                    %>
                    
                </div>
                <div class="row z-depth-5">
                    <h4>PRODUCTOS</h4>
                    <%
                        ArrayList<Producto> productos = (ArrayList<Producto>) request.getSession().getAttribute("productos");
                        if (productos != null) {
                            for (int i = 0; i < productos.size(); i++) {

                    %>
                    <div class="col s6 m6 l3 ">
                        <div class="card small hoverable">
                            <div class="card-image waves-effect waves-block waves-light">
                                <img class="activator" src="../../Img/productos/<%=productos.get(i).getNombre().replace(" ", "")%>.jpg" >
                            </div>
                            <div class="card-content">
                                <span class="card-title activator grey-text text-darken-4"><%=productos.get(i).getNombre()%><i class="material-icons right">more_vert</i></span>
                                <p><a href="Servicios.jsp?modal=AgregarProducto&Producto=<%=productos.get(i).getNombre()%>">Hacer pedido</a></p>
                            </div>
                            <div class="card-reveal">
                                <span class="card-title grey-text text-darken-4"><%=productos.get(i).getNombre()%><i class="material-icons right">close</i></span>
                                <p><%= productos.get(i).getDescripcion()%></p>
                                <hr>  
                                <Br>
                                <p>$ <%= productos.get(i).getPrecio()%></p>


                            </div>
                        </div>
                    </div>
                    <%                    }
                        } else {
                            out.print("<p class=\"red background white-text\">No hay productos</p>");
                        }
                    %>
                </div>
            </div> 
        </section>
        <section>
            <div id="Editarservicio" class="modal">
                <div class="modal-content">

                    <h2> Editar <%=request.getParameter("servicio")%> </h2>
                    <%
                        Servicio servicio = new Servicio();
                        for (int i = 0; i < servicios.size(); i++) {
                            if (servicios.get(i).getNombreservicio().equals(request.getParameter("servicio"))) {
                                servicio = servicios.get(i);
                            }
                        }
                    %>
                    <form action="..\..\Encargado" method="post" enctype="multipart/form-data">
                        <h5>Foto</h5>  
                        <div class="file-field input-field">
                            <div class="btn">
                                <span>Cambiar foto</span>
                                <input type="file" name="imagenp" accept="image/x-png,image/gif,image/jpeg">
                            </div>
                            <div class="file-path-wrapper">
                                <input class="file-path validate" type="text">
                            </div>
                        </div> 
                        <label for="nombreservicio">Nombre del servicio:</label>
                        <input id="nombreservicio" type="text" name='nombreservicio' class="input-field" value="<%=request.getParameter("servicio")%>">
                        <label for="textarea1">Descripción</label>
                        <textarea id="textarea1" class="materialize-textarea" name="descripcion"><%=servicio.getDescripcion()%></textarea>
                        <p>
                            <label>
                                <%if (servicio.isVisible()) {
                                        out.print("<input type='checkbox' checked='checked' name='visible'/>");
                                    } else {
                                        out.print("<input type='checkbox' name='visible' />");
                                    }%>
                                <span>Visible</span>
                            </label>
                        </p>
                        <textarea id="textarea1" class="materialize-textarea" name="precio"><%=servicio.getPrecio()%></textarea>
                        <input type="hidden" value="modificarServicio" name="action">
                        <input type="hidden" value="<%=request.getParameter("servicio")%>" name="servicio">
                        <input type="hidden" value="page" name="place">
                        <br>
                        <input type="submit" value="Editar" class="btn small yellow darken-2">
                    </form>
                </div>
            </div>
            <div id="AgregarProducto" class="modal">
                <div class="modal-content">
                    <h2> Hacer pedido de <%=request.getParameter("Producto")%> </h2>

                    <form action="..\..\Cliente" method="post">

                        <label>Cita para recoger pedido: </label>
                        <select name="cita">
                            <option disabled selected>Selecciona la cita</option>
                            <%                        ArrayList<Cita> citas = (ArrayList<Cita>) request.getSession().getAttribute("miniaturacitas");
                                if (citas != null) {
                                    for (int i = 0; i < citas.size(); i++) {
                                        if (!citas.get(i).isEstado()) {
                                            out.print("<option>" + citas.get(i).getCodigo() + "</option>");
                                        }
                                    }
                                }
                            %>
                        </select>

                        <div class="input-field">
                            <input id="cantidad" name="cantidad" type="number" min="1" max="5">
                            <label for="cantidad">Cantidad:</label>
                        </div> 
                        <input type="hidden" value="encargarProducto" name="action">
                        <input type="hidden" value="<%=request.getParameter("Producto")%>" name="producto">
                        <input type="hidden" value="page" name="place">
                        <br>
                        <input type="submit" value="Encargar" class="btn small" name="boton">
                    </form>
                </div>
            </div>
        </section>
    </body>
</html>
