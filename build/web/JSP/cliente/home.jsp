<%-- 
    Document   : home
    Created on : Mar 6, 2020, 12:05:39 PM
    Author     : jela3
--%>
<%@page import="Clases.Encargo"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %> 
<%@page import="Clases.Servicio"%>
<%@page import="Clases.Cita"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Clases.Perro"%>
<%@ page session="true" %>

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
                        <li class="active"><a  href="home.jsp">Home</a></li>
                        <li class=""><a href="Servicios.jsp">Servicios</a></li>
                        <li class=""><a href="Perfil.jsp">Mi perfil</a></li>
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
            <div class="row" sty>   

                <div class="col s12 m8 l3" >

                    <h4>MIS MASCOTAS</h4>
                    <div class="altura">
                        <%
                            ArrayList<Perro> perro = (ArrayList<Perro>) request.getSession().getAttribute("miniaturaperro");
                            if (perro != null) {
                                for (int i = 0; i < perro.size(); i++) {
                                    String archivo = perro.get(i).getArchivoimg().toString();
                                    String terminacion = archivo.substring(archivo.length() - 4, archivo.length());
                        %>
                        <article id="<%=perro.get(i).getNombre()%>">
                            <h5><%=perro.get(i).getNombre()%></h5>
                            Citas finalizadas <%=perro.get(i).getCodigos()%>
                            <img src="../../Img/<%=request.getSession().getAttribute("correo") + "/" + perro.get(i).getNombre() + terminacion%>" width='300' height='150'>
                            <a class="modal-trigger" href="home.jsp?id=<%=perro.get(i).getNombre()%>&modal=Editar"><input type="button" value="Editar" class="btn-small yellow darken-2"></a>
                            <a href="home.jsp?id=<%=perro.get(i).getNombre()%>&modal=Agendarcita"><input type="button" value="Agendar cita" class="btn-small"></a>
                        </article>
                        <%
                            }
                        } else {
                        %>
                        <p class="red background white-text">No hay mascotas</p>
                        <%
                            }
                        %>
                    </div>
                </div>     
                <div class="col s12 m8 l7 ">
                    <h4>MIS CITAS</h4>
                    <div class="altura">
                        <table>

                            <%                        ArrayList<Cita> citas = (ArrayList<Cita>) request.getSession().getAttribute("miniaturacitas");
                                if (citas != null) {

                            %>
                            <thead>
                                <tr>                        
                                    <th>Perro</th>
                                    <th>Fecha</th>
                                    <th>Hora</th>
                                    <th>Codigo</th>
                                    <th>Estado</th>
                                </tr>                
                            </thead>
                            <%                                for (int i = 0; i < citas.size(); i++) {
                            %>

                            <tbody>
                                <tr id="<%=citas.get(i).getMascota()%>">
                                    <td><%=citas.get(i).getMascota()%></td>
                                    <td><%=citas.get(i).getFecha().substring(0, 10)%></td>
                                    <td><%=citas.get(i).getHora().substring(0, 5)%></td>
                                    <td><%=citas.get(i).getCodigo()%></td>
                                    <%
                                        if (citas.get(i).isEstado()) {
                                    %>
                                    <td class="green-text">Confirmada</td>
                                    <%
                                    } else {
                                    %>
                                    <td class="red-text">No confirmada</td>
                                    <%
                                        }
                                    %>
                                    <td><a href="home.jsp?id=<%=citas.get(i).getMascota()%>&codigo=<%=citas.get(i).getCodigo()%>&modal=Editarcita"><i class="material-icons black-text">edit</i></td>
                                    <td><a href="home.jsp?id=<%=citas.get(i).getMascota()%>&codigo=<%=citas.get(i).getCodigo()%>&modal=Infocita"><i class="material-icons black-text">info</i></td>
                                </tr>
                                <%
                                    }
                                } else {
                                %>
                            <p class="red background white-text">No hay citas registradas</p>
                            <%
                                }
                            %>
                            </tbody> 
                        </table>                         
                    </div>
                </div>
                <form action='..\..\Cliente' method="post" enctype="multipart/form-data" class="col s12 m4 l2 z-depth-1-half">
                    <h4>REGISTRAR MASCOTA</h4>
                    <div class="row">
                        <div class="input-field col s12 m12">
                            <input id="nomp" name="nomp" type="text" >
                            <label for="nomp">Nombre</label>
                        </div>                                                                                                               
                    </div> 
                    <div class="row">
                        <div class="input-field col s12 m12">
                            <input id="fecha" name="fecha" type="text" class="datepicker">
                            <label for="fecha">Fecha de nacimiento</label>
                        </div>                                                                                                               
                    </div>     
                    <div class="row">
                        <div class="input-field col s12 m12">
                            <select name="generop">
                                <option disabled selected>Selecciona</option>
                                <option>Macho</option>
                                <option>Hembra</option>
                            </select>
                            <label>Genero: </label>
                        </div>  

                    </div>  
                    <div class="row">
                        <div class="input-field col s12 m12">
                            <select name="tallap">
                                <option disabled selected>Selecciona</option>
                                <option >Chico</option>
                                <option >Mediano</option>
                                <option >Grande</option>
                                <option >Gigante</option>
                            </select>
                            <label>Talla: </label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 m12">
                            <div class="file-field input-field">
                                <div class="btn">
                                    <span>Foto</span>
                                    <input type="file" name="imagenp" accept="image/x-png,image/gif,image/jpeg">
                                </div>
                                <div class="file-path-wrapper">
                                    <input class="file-path validate" type="text">
                                </div>
                            </div>                                                                                                               
                        </div>
                        <div class="row">
                            <div class="input-field col s12 m12">
                                <input type="hidden" name="place" value="page">
                                <input type="hidden" name="action" value="registrarMascota">
                                <input type=submit value='Aceptar' class="btn">
                            </div>
                        </div>
                    </div>
                </form>     
            </div>               
        </section>
        <section>

            <div id="Editar" class="modal">
                <h2>Editar a <%=request.getParameter("id")%></h2>
                <div class="modal-content">
                    <form action="..\..\Cliente" method="post" enctype="multipart/form-data">
                        <h3>Foto</h3>

                        <%
                            if (perro != null) {
                                Perro perroSelected = new Perro();
                                //Buscamos el objeto del perro seleccionado en el array
                                for (int i = 0; i < perro.size(); i++) {
                                    if (perro.get(i).getNombre().equals(request.getParameter("id"))) {
                                        perroSelected = perro.get(i);
                                    }
                                }
                                out.println("<img src= ../../Img/" + request.getSession().getAttribute("correo") + "/" + perroSelected.getNombre() + ".png width='200' height='100'>");

                        %>
                        <div class="file-field input-field">
                            <div class="btn">
                                <span>Cambiar foto</span>
                                <input type="file" name="imagenp" accept="image/x-png,image/gif,image/jpeg">
                            </div>
                            <div class="file-path-wrapper">
                                <input class="file-path validate" type="text">
                            </div>
                        </div> 

                        <h3>Nombre</h3><input type=text name='nombreperro' value="<%=perroSelected.getNombre()%>"><br>
                        <h3>Nacimiento (Una fecha aproximada)</h3>
                        <input type="date"  name="fechanacimiento"  value="<%=perroSelected.getNacimiento()%>">
                        <h3>Genero</h3>
                        <select name="generoperro">
                            <%if (perroSelected.getGenero()) {
                                    out.print("<option selected>Macho</option>");
                                    out.print("<option>Hembra</option>");
                                } else {
                                    out.print("<option>Macho</option>");
                                    out.print("<option selected>Hembra</option>");
                                }%>
                        </select>
                        <h3>talla</h3>
                        <select name="tallaperro">
                            <%
                                    String talla = perroSelected.getTalla();
                                    if (talla != null) {
                                        if (talla.equals("Chico")) {
                                            out.print("<option selected>Chico</option>");
                                            out.print("<option>Mediano</option>");
                                            out.print("<option>Grande</option>");
                                        } else if (talla.equals("Mediano")) {
                                            out.print("<option>Chico</option>");
                                            out.print("<option selected>Mediano</option>");
                                            out.print("<option>Grande</option>");
                                        } else if (talla.equals("Grande")) {
                                            out.print("<option>Chico</option>");
                                            out.print("<option>Mediano</option>");
                                            out.print("<option selected>Grande</option>");
                                        }
                                    }
                                }
                            %>

                        </select>

                        <input type="hidden" value="editarMascota" name="action">
                        <input type="hidden" value="<%= request.getParameter("id")%>" name="mascota">
                        <input type="hidden" value="page" name="place">
                        <br>
                        <input type="submit" value="Editar" class="btn-small yellow darken-2">
                    </form>
                    <a href="home.jsp"><input type="button" value="Cancelar" class="btn-small red darken-2"></a>
                </div>

            </div>
            <div id="Infocita" class="modal">

                <%  String fecha = "0";
                    String hora = "0";
                    String servicio = "";
                    if (request.getParameter("codigo") != null) {
                        Cita cita = new Cita();
                        for (int i = 0; i < citas.size(); i++) {
                            if (citas.get(i).getCodigo().equals(request.getParameter("codigo"))) {
                                cita = citas.get(i);
                            } else {

                            }
                        }
                        fecha = cita.getFecha().substring(6, 10) + "-" + cita.getFecha().substring(0, 2) + "-" + cita.getFecha().substring(3, 5);
                        hora = cita.getHora().substring(0, 5);
                        servicio = cita.getServicio();

                    }
                %>
                <div class="modal-content">
                        <h2>Informacion cita: <%
                            out.print(request.getParameter("codigo"));%></h2>

                    <form >
                        <label>Fecha de la cita: </label>
                        <input type="date" disabled value="<%out.print(fecha);%>">
                        <label>Hora de la cita: </label>
                        <input type="time" disabled value="<%out.print(hora);%>">
                        <label>Servicio</label>
                        <input type="text" disabled value="<%out.print(servicio);%>">
                        <br>

                    </form>
                    <%
                        ArrayList<Encargo> encargos = (ArrayList<Encargo>) request.getSession().getAttribute("encargos");
                        if (encargos != null) {
                    %>
                    <ul class="collapsible">
                        <li>
                            <div class="collapsible-header">
                                <i class="material-icons">shopping_cart</i>
                                Pedido:
                                <span class="badge"></span>
                            </div>
                            <div class="collapsible-body">
                                <ul class="collection">
                                    <%int contar = 0;
                                        for (int i = 0; i < encargos.size(); i++) {
                                            if (encargos.get(i).getCita().equals(request.getParameter("codigo"))) {
                                                contar++;

                                    %>
                                    <li class="collection-item avatar">
                                        <img src="../../Img/productos/<%=encargos.get(i).getArticulo().replace(" ", "")%>.jpg" alt="" class="circle">
                                        <span class="title"><%=encargos.get(i).getArticulo()%></span>
                                        <div class="secondary-content"><%= encargos.get(i).getCantidad()%>
                                        </div>
                                    </li>
                                    <%
                                            }
                                        }
                                        if (contar == 0) {
                                    %>
                                    <h5>NO HAY ENCARGOS</h5>
                                    <p>Para realizar un pedido ve a la sección de servicios y elige alguno de nuestros productos</p>
                                    <%
                                        }
                                    %>
                                </ul>
                            </div>
                        </li>
                    </ul>
                    <%
                        }%>

                </div>
            </div>

            <div id="Agendarcita" class="modal">
                <div class="modal-content">
                    <h2> Agendar cita para <%=request.getParameter("id")%></h2>

                    <form action="..\..\Cliente" method="post">

                        <label>Fecha: </label>
                        <input type="date" name='fechacita' class="date-text">

                        <label>Hora: </label>
                        <input type="time" name="horacita">

                        <label for="tiposervicio">Tipo de servicio: </label>
                        <select id='tiposervicio' name="servicio">
                            <option disabled selected>Selecciona el servicio</option>
                            <%  ArrayList<Servicio> servicios = (ArrayList<Servicio>) request.getSession().getAttribute("servicios");

                                for (int i = 0; i < servicios.size(); i++) {
                                    out.print("<option>" + servicios.get(i).getNombreservicio() + "</option>");
                                }
                            %>
                        </select>

                        <input type="hidden" value="agendarCita" name="action">
                        <input type="hidden" value="<%if (request.getParameter("id") != null) {
                                out.print(request.getParameter("id"));
                            }%>" name="mascota">
                        <input type="hidden" value="page" name="place">
                        <br>
                        <input type="submit" value="Agendar" class="btn small">
                    </form>
                </div>
            </div>
            <div id="Editarcita" class="modal">

                <%
                    if (request.getParameter("codigo") != null) {
                        Cita cita = new Cita();
                        for (int i = 0; i < citas.size(); i++) {
                            if (citas.get(i).getCodigo().equals(request.getParameter("codigo"))) {
                                cita = citas.get(i);
                            } else {

                            }
                        }
                        fecha = cita.getFecha().substring(6, 10) + "-" + cita.getFecha().substring(0, 2) + "-" + cita.getFecha().substring(3, 5);
                        hora = cita.getHora().substring(0, 5);
                        servicio = cita.getServicio();
                    }
                %>
                <div class="modal-content">
                        <h2>Editar cita: <%
                            out.print(request.getParameter("codigo"));%></h2>

                    <form action="..\..\Cliente" method="post">
                        <label>Fecha de la cita: </label>
                        <input type="date" name='fechacita' value="<%out.print(fecha);%>">
                        <label>Hora de la cita: </label>
                        <input type="time" name="horacita" value="<%out.print(hora);%>">
                        <label for="tiposervicio">Tipo de servicio: </label>
                        <select id='tiposervicio' name="servicio">
                            <option disabled selected>Selecciona el servicio</option>
                            <%
                                for (int i = 0; i < servicios.size(); i++) {
                                    if (servicio.equals(servicios.get(i).getNombreservicio())) {
                                        out.print("<option selected>" + servicios.get(i).getNombreservicio() + "</option>");
                                    } else {
                                        out.print("<option>" + servicios.get(i).getNombreservicio() + "</option>");
                                    }

                                }
                            %>
                        </select>
                        <input type="hidden" value="editarCita" name="action">
                        <input type="hidden" value="<%out.print(request.getParameter("id"));%>" name="mascota">
                        <input type="hidden" value="<%=request.getParameter("codigo")%>" name="codigo">
                        <input type="hidden" value="page" name="place">
                        <br>
                        <input type="submit" value="Editar" class="btn-small yellow darken-2" name="boton">
                        <input type="submit" value="Borrar" class="btn-small red" name="boton">
                    </form>
                    <%

                        if (encargos != null) {
                    %>
                    <ul class="collapsible">
                        <li>
                            <div class="collapsible-header">
                                <i class="material-icons">shopping_cart</i>
                                Pedido:
                                <span class="badge"></span>
                            </div>
                            <div class="collapsible-body">
                                <ul class="collection">
                                    <%int contar = 0;
                                        for (int i = 0; i < encargos.size(); i++) {
                                            if (encargos.get(i).getCita().equals(request.getParameter("codigo"))) {
                                                contar++;

                                    %>
                                    <li class="collection-item avatar">
                                        <img src="../../Img/productos/<%=encargos.get(i).getArticulo().replace(" ", "")%>.jpg" alt="" class="circle">
                                        <span class="title"><%=encargos.get(i).getArticulo()%></span>
                                        <p>
                                             <form action="..\..\Cliente" method="post">
                                                <div class="input-field">
                                                    <input id="cantidad" name="cantidad" type="number" min="1" max="5" value="<%= encargos.get(i).getCantidad()%>">
                                                    <label for="cantidad">Cantidad:</label>
                                                </div> 
                                                <input type="hidden" value="encargarProducto" name="action">
                                                <input type="hidden" value="<%=encargos.get(i).getArticulo()%>" name="producto">
                                                <input type="hidden" value="<%=request.getParameter("codigo")%>" name="cita">
                                                <input type="hidden" value="page" name="place">
                                                <br>
                                                <input type="submit" value="Encargar" class="btn-small yellow darken-2" name="boton">
                                                <input type="submit" value="Borrar" class="btn-small red" name="boton">
                                            </form>
                                        </p>
                                        <div class="secondary-content col s8 m8">
                                           
                                        </div>
                                    </li>
                                    <%
                                            }
                                        }
                                        if (contar == 0) {
                                    %>
                                    <h5>NO HAY ENCARGOS</h5>
                                    <p>Para realizar un pedido ve a la sección de servicios y elige alguno de nuestros productos</p>
                                    <%
                                        }
                                    %>
                                </ul>
                            </div>
                        </li>
                    </ul>
                    <%
                        }%>

                </div>
            </div>
        </section>
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

    </body>
</html>
