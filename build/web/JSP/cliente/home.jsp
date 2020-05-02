<%-- 
    Document   : home
    Created on : Mar 6, 2020, 12:05:39 PM
    Author     : jela3
--%>
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
        <!-- Compiled and minified JavaScript -->
        <script src="../../JS/materialize.min.js"></script>  
        <script src="../../JS/Efectos.js"></script>
        <style>
            .overlay {
                position: fixed;
                top: 0;
                bottom: 0;
                left: 0;
                right: 0;
                background: rgba(0, 0, 0, 0.7);
                transition: opacity 500ms;
                visibility: hidden;
                opacity: 0;
            }
            .overlay:target {
                visibility: visible;
                opacity: 1;
            }
            #popupBody{
                width: 46%;
                padding: 2%;
                border-radius: 15px;
                box-shadow: 0 0 5px #CCC;
                background: #FFF;
                position: relative;
                transition: all 5s ease-in-out;
                margin: 4% auto;    
            }
            #cerrar{
                position: absolute;
                top: 20px;
                right: 30px;
                transition: all 200ms;
                font-size: 30px;
                font-weight: bold;
                text-decoration: none;
                color: #F00;
            }
        </style>
    </head>
    <body>
        <script type="text/javascript">
            $(document).ready(function () {
                setTimeout(function () {
                    $(".mensaje").fadeOut(3000);
                }, 3000);
            });

        </script>
        <p class="mensaje">
            <%
                if (request.getParameter("mens") != null) {
                    out.println(request.getParameter("mens"));
                }
            %>
        </p>        
        <header>
            <nav>
                <div class="nav-wrapper">
                    <a href="" class="brand-logo">Can-Contento</a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <li class="menu__item"><a class="menu__link select" href="home.jsp">Home</a></li>
                        <li class="menu__item"><a class="menu__link" href="Servicios.jsp">Servicios</a></li>
                        <li class="menu__item"><a class="menu__link" href="Perfil.jsp">Mi perfil</a></li>
                        <li class="menu__item"><a class="menu__link" href="Notificaciones.jsp">Notificaciones</a></li>
                    </ul>
                </div>
            </nav>
        </header>        
        <section id="agregarM">                
            <div class="row" sty>                                
                <form action='..\..\Cliente' method="post" enctype="multipart/form-data" class="col s12 m4 l4" >
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
                            <label for="fecha">Fecha de nacimiento(aproximada)</label>
                        </div>                                                                                                               
                    </div>     
                    <div class="row">
                        <div class="input-field col s12 m6">
                            <select name="generop">
                                <option disabled selected>Selecciona el genero</option>
                                <option>Macho</option>
                                <option>Hembra</option>
                            </select>
                            <label>Genero: </label>
                        </div>  
                        <div class="input-field col s12 m6">
                            <select name="tallap">
                                <option disabled selected>Selecciona la talla</option>
                                <option >Pequeño</option>
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
                                    <span>File</span>
                                    <input type="file" name="imagenp" accept="image/x-png,image/gif,image/jpeg">
                                </div>
                                <div class="file-path-wrapper">
                                    <input class="file-path validate" type="text">
                                </div>
                            </div>                                                                                                               
                        </div>
                        <div class="row">
                            <div class="input-field col s12 m12">
                                <input type="hidden" name="place" value="pag">
                                <input type="hidden" name="accion" value="registrarM">
                                <input type=submit value='Aceptar' class="btn">
                            </div>
                        </div>
                    </div>
                </form>                
                <div class="col s12 m8 l8">
                    <h4>MIS MASCOTAS</h4>
                    <%
                        ArrayList<Perro> perro = (ArrayList<Perro>) request.getSession().getAttribute("miniaturaperro");
                        if (perro != null) {
                            for (int i = 0; i < perro.size(); i++) {
                                String archivo = perro.get(i).getArchivoimg().toString();
                                String terminacion = archivo.substring(archivo.length() - 4, archivo.length());
                                out.println("<article " + "id=\"" + perro.get(i).getNombre() + "\"" + ">");
                                out.println("<h5>" + perro.get(i).getNombre() + "</h5>");
                                out.println("Citas realizadas " + perro.get(i).getCodigos());
                                out.println("<img src= ../../Img/" + request.getSession().getAttribute("correo") + "/" + perro.get(i).getNombre() + terminacion + " width='200' height='100'>");
                                out.println(" <a href=\"home.jsp?id=" + perro.get(i).getNombre() + "#Editar\"><input type=\"button\" value=\"Editar\" class=\"btn-small yellow darken-2\"></a>");
                                out.println(" <a href=\"home.jsp?id=" + perro.get(i).getNombre() + "#Eliminar\"><input type=\"button\" value=\"Eliminar\" class=\"btn-small deep-orange accent-4\"></a>");
                                out.println("  <a href=\"home.jsp?id=" + perro.get(i).getNombre() + "#Agendarcita\"><input type=\"button\" value=\"Agendar cita\" class=\"btn-small\"></a>");
                                out.println("</article>");
                            }
                        } else {
                            out.print("No hay mascotas");
                        }

                    %>
                </div>
            </div>
        </section>
        <section style="margin-top:4rem; margin-bottom: 10rem" class="container">
            <h4>MIS CITAS</h4>
            <table>
                <thead>
                    <tr>                        
                        <th>Citas</th>
                        <th>Fecha</th>
                        <th>Hora</th>
                        <th>Codigo</th>
                        <th>Estado</th>
                    </tr>                
                </thead>
                <tbody>
                    <%                        ArrayList<Cita> citas = (ArrayList<Cita>) request.getSession().getAttribute("miniaturacitas");
                        if (citas != null) {
                            for (int i = 0; i < citas.size(); i++) {
                                out.println("<tr " + "id=\"" + citas.get(i).getMascota() + "\"" + ">");
                                out.println("<td>" + citas.get(i).getMascota() + "</td>");
                                out.println("<td>" + citas.get(i).getFecha().substring(0, 10) + "</td>");
                                out.println("<td>" + citas.get(i).getHora().substring(0, 5) + "</td>");
                                out.println("<td>" + citas.get(i).getCodigo() + "</td>");
                                if (citas.get(i).isEstado()) {
                                    out.println("<td>Confirmada</td>");
                                } else {
                                    out.print("<td>No confirmada</td>");
                                }
                                out.println("<td><a href=\"home.jsp?id=" + citas.get(i).getMascota() + "&codigo=" +citas.get(i).getCodigo() +  "#Infocita\"><img src=\"../../Img/informacioncita.jpg\" with=\"30\" height=\"30\" alt=\"Mas información\" /> </a></td>");
                                out.println("</tr>");
                            }
                        } else {
                            out.print("<h3>No hay citas registradas</h3>");
                        }
                        //<input type=\"button\" value=\"Editar cita\" class=\"btn-small yellow darken-2\">
                    %>
                </tbody>
            </table>                
        </section>   
       
                    
</body>
</html>
