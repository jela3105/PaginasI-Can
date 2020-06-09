<%-- 
    Document   : Servicios
    Created on : Nov 11, 2019, 12:14:46 PM
    Author     : jela3
--%>

<%@page import="Clases.Cita"%>
<%@page import="Clases.Perro"%>
<%@page import="Clases.Producto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Clases.Servicio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
                        <li class="menu__item"><a class="menu__link select" href="home.jsp">Home</a></li>
                        <li class="active"><a class="menu__link" href="../../JSP/cliente/Servicios.jsp">Servicios</a></li>
                        <li class=""><a class="menu__link" href="Perfil.jsp">Mi perfil</a></li>
                        <li class="menu__item"><a class="menu__link" href="Notificaciones.jsp">Notificaciones</a></li>
                    </ul>
                </div>
            </nav>
        </header> 


        <% /*
            Connection c = null;
            Statement stm = null;
            Statement espacia = null;
            ResultSet trae, espaciados = null;
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                c = DriverManager.getConnection("jdbc:mysql://localhost/canContento", "root", "n0m3l0");
                stm = c.createStatement();
                espacia = c.createStatement();
            } catch (SQLException e) {
                out.print(e.toString());
            }
            if (request.getParameter("OK") != null) {
                String serv1 = request.getParameter("producto1");
                String serv2 = request.getParameter("producto2");
                String serv3 = request.getParameter("producto3");
                String serv4 = request.getParameter("producto4");
                String serv5 = request.getParameter("producto5");
                String serv6 = request.getParameter("producto6");
                String serv7 = request.getParameter("producto7");
                String servCombo = request.getParameter("Servicio");

                trae = stm.executeQuery("Select nom_art from articulo where exi_art > 0;");
                int contador = 0;
                while (trae.next()) {
                    contador++;
                }
                if (!serv1.equals("")) {
                    out.print("<br><br>");
                    out.println(serv1);
                }
                if (!serv2.equals("")) {
                    out.print("<br><br>");
                    out.println(serv2);
                }
                if (!serv3.equals("")) {
                    out.print("<br><br>");
                    out.println(serv3);
                }
                if (!serv4.equals("")) {
                    out.print("<br><br>");
                    out.println(serv4);
                }
                if (!serv5.equals("")) {
                    out.print("<br><br>");
                    out.println(serv5);
                }
                if (!serv6.equals("")) {
                    out.print("<br><br>");
                    out.println(serv6);
                }
                if (!serv7.equals("")) {
                    out.print("<br><br>");
                    out.println(serv7);
                }
                if (!servCombo.equals("")) {
                    out.print("<br><br>");
                    out.println(servCombo);
                }

            } else {

                int espacios = 0;
                try {
                    trae = stm.executeQuery("Select nom_art from articulo where exi_art > 0;");
                    out.print("<h1>Selecciona los articulos que deseas comprar</h1>");
                    out.print("<br><br><br>");
                    out.print("<form action='Servicios.jsp' method='post'>");
                    out.print("<table border='2'>");
                    out.print("<tr>");
                    out.print("<td>Productos</td>");
                    while (trae.next()) {
                        out.print("<td>'" + trae.getString("nom_art") + "'</td>");
                        espacios++;
                    }
                    out.print("<td>");
                    out.print("<select name ='Servicio'>");
                    out.print("<option value=null>Selecciona un servicio</option>");
                    out.print("<option value='serv1'>Corte de pelo</option>");
                    out.print("<option value='serv2'>Baño</option>");
                    out.print("<option value='serv3'>Paseo</option>");
                    out.print("</select>");
                    out.print("</td>");
                    out.print("</tr><tr><td>Cantidades</td>");
                    for (int i = 1; i <= espacios + 1; i++) {
                        out.print("<td><Select name='producto'" + i + "''>"
                                + "<option value='0'>0</option>"
                                + "<option value='1'>1</option>"
                                + "<option value='2'>2</option>"
                                + "<option value='3'>3</option>"
                                + "<option value='4'>4</option>"
                                + "<option value='5'>5</option>"
                                + "<option value='6'>6</option>"
                                + "<option value='7'>7</option>"
                                + "<option value='8'>8</option>"
                                + "<option value='9'>9</option>"
                                + "</td>");
                    }
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("<input type=submit name=OK value=Agregar>");
                } catch (Exception e) {
                    out.print(e.toString());
                }
                out.print("<form>");
            }
             */

        %>
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
                                <p><a href="Servicios.jsp?modal=Agendarcita&servicio=<%=servicios.get(i).getNombreservicio()%>">Agendar cita</a></p>
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
            <div id="Agendarcita" class="modal">
                <div class="modal-content">
                    <h2> Agendar <%=request.getParameter("servicio")%> </h2>

                    <form action="..\..\Cliente" method="post">
                        <label>Fecha:</label>
                        <input type="date" name='fechacita' class="datepicker-calendar-container">
                        <label>Hora: </label>
                        <input type="time" name="horacita">
                        <label>Servicio para: </label>
                        <select name="mascota">
                            <option disabled selected>Selecciona la mascota</option>
                            <%  ArrayList<Perro> perros = (ArrayList<Perro>) request.getSession().getAttribute("miniaturaperro");
                                if (perros != null) {
                                    for (int i = 0; i < perros.size(); i++) {
                                        out.print("<option>" + perros.get(i).getNombre() + "</option>");
                                    }
                                }

                            %>


                        </select>
                        <label>Tipo de servicio: </label>
                        <input type="hidden" value="agendarCita" name="action">
                        <input type="hidden" value="<%=request.getParameter("servicio")%>" name="servicio">
                        <input type="hidden" value="page" name="place">
                        <br>
                        <input type="submit" value="Agendar" class="btn small">
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
