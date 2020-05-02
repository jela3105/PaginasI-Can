<%-- 
    Document   : SesionUsuario
    Created on : Nov 10, 2019, 12:36:02 AM
    Author     : jela3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>IniciarSesion</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" href="../Img/Techno-bit.png">
        <!-- Compiled and minified CSS -->
        <link rel="stylesheet" href="../Css/materialize.min.css">
        <link rel="stylesheet" href="Css/Estilos.css">
        <!-- Compiled and minified JavaScript -->
        <script src="../JS/materialize.min.js"></script> 
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
                        <li class="menu__item"><a class="menu__link select" href="../index.html">Inicio</a></li>
                        <li class="menu__item"><a class="menu__link" href="../HTML/SesionUsuario.html">Iniciar Sesión</a></li>
                        <li class="menu__item"><a class="menu__link" href="../HTML/RegistroUsuario.html">Registrarse</a></li>
                    </ul>
                </div>
            </nav>
        </header>
        <div class="container" style="margin-top: 4rem">
            <div class="row">
                <h3 style="margin-bottom: 2rem">Ingrese sus datos</h3>
                <div class="col hide-on-small-and-down m5 l5">
                    <img src="../Img/login.jpg">
                </div>
                <form action="..\Cliente" method="post" class="col s12 m7 l7">
                    <div class="row">
                        <div class="input-field col s12 m12">
                            <input id="corr_compara" name="corr_compara" type="text" >
                            <label for="corr_compara">Ingresa tu correo electrónico</label>
                        </div>                                                                                                               
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="contra_compara" name="contra_compara" type="password" >
                            <label for="contra_compara">Ingresa tu contraseña</label>
                        </div>                             
                    </div>
                    <div>
                        <input type="hidden" value="login" name="action">
                        <input type="hidden" value="page" name="place">
                        <input type="submit" value="Acceder" class="btn">
                    </div>
                </form>   
            </div>                                                          
        </div>
    </body>
</html>
