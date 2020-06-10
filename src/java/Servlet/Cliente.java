package Servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Clases.Cita;
import Clases.DescripcionServicio;
import Clases.Encargo;
import Clases.Perro;
import Clases.Producto;
import Clases.Servicio;
import Clases.Usuario;
import Clases.UsuarioBD;
import Conexion.Conexion;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 * @author jela3
 */
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class Cliente extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String ac = request.getParameter("action");
        if (ac.equals("altaCliente")) {
            altaCliente(request, response);
        } else if (ac.equals("login")) {
            login(request, response);
        } else if (ac.equals("registrarMascota")) {
            regisrarMascota(request, response);
        } else if (ac.equals("obtenerProductos")) {
            obtenerProductos(request, response);
        } else if (ac.equals("eliminarMascota")) {
            eliminarMascota(request, response);
        } else if (ac.equals("editarMascota")) {
            editarMascota(request, response);
        } else if (ac.equals("agendarCita")) {
            agendarCita(request, response);
        } else if (ac.equals("encargarProducto")) {
            encargarProducto(request, response);
        } else if (ac.equals("editarCita")) {
            editarCita(request, response);
        } else if (ac.equals("editarDatos")) {
            editarDatos(request, response);
        }

        //
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();

        sesion.invalidate();
        response.sendRedirect("index.html");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void altaCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nombre = request.getParameter("nom_clie");
        String apellido = request.getParameter("ape_clie");
        String direccion = request.getParameter("dir_clie");
        String correo = request.getParameter("cor_clie");
        String telefono = request.getParameter("tel_clie");
        String contraseña = request.getParameter("con_clie");
        String contraseña2 = request.getParameter("con2_clie");
        String place = request.getParameter("place");

        if (nombre.equals("")
                || apellido.equals("")
                || direccion.equals("")
                || correo.equals("")
                || telefono.equals("")
                || contraseña.equals("")
                || contraseña2.equals("")) {

            System.out.println("Campos vacios");
            if (place.equals("page")) {
                String men = "Llena todos los campos";
                response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);
            } else if (place.equals("app")) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("Registro", "camposVacios");
                    PrintWriter pw = response.getWriter();
                    pw.write(jsonObject.toString());
                    pw.print(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (contraseña.equals(contraseña2)) {
                if (validarTel(telefono) == null) {
                    if (place.equals("page")) {
                        String men = "Numero de telefono invalido";
                        response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);
                    } else if (place.equals("app")) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("Registro", "telefonoIncorrecto");
                            PrintWriter pw = response.getWriter();
                            pw.write(jsonObject.toString());
                            pw.print(jsonObject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        Usuario usuario = new Usuario();
                        usuario.setNombre(nombre);
                        usuario.setApellido(apellido);
                        usuario.setDireccion(direccion);
                        usuario.setCorreo(correo);
                        usuario.setTelefono(telefono);
                        usuario.setContraseña(contraseña);
                        usuario.setTipoUsuario(3);

                        UsuarioBD usuarioBD = new UsuarioBD();
                        if (usuarioBD.registrarUsuario(usuario)) {
                            String context = request.getServletContext().getRealPath("/Img");
                            File carpeta = new File(context + "\\" + correo);
                            carpeta.mkdirs();
                            if (place.equals("page")) {
                                String men = "Registro correcto";
                                response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);
                            } else if (place.equals("app")) {
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("Registro", "correcto");
                                    PrintWriter pw = response.getWriter();
                                    pw.write(jsonObject.toString());
                                    pw.print(jsonObject.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            if (place.equals("page")) {
                                String men = "Error al registrar, intenta usando otro correo";
                                response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);
                            } else if (place.equals("app")) {
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("Registro", "fallo");
                                    PrintWriter pw = response.getWriter();
                                    pw.write(jsonObject.toString());
                                    pw.print(jsonObject.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (Exception efe) {
                        System.out.println(efe.toString());
                        if (place.equals("page")) {
                            System.out.println("Respuesta pagina");
                            String men = "El numero de telefono no es telefono";
                            response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);
                        } else if (place.equals("app")) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("Registro", "FalloN");
                                PrintWriter pw = response.getWriter();
                                pw.write(jsonObject.toString());
                                pw.print(jsonObject.toString());
                                System.out.println("Registro fallo telefono no es numero" + jsonObject.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } else {
                if (place.equals("page")) {
                    String men = "Contraseñas no coinciden";
                    response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);
                } else if (place.equals("app")) {
                    System.out.println("contraseñasNoCoinciden");
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("Registro", "contraseñasNoCoinciden");
                        PrintWriter pw = response.getWriter();
                        pw.write(jsonObject.toString());
                        pw.print(jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }//fin de metodo altaCliente

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String correo = request.getParameter("correo");
        String contra = request.getParameter("contra");
        String place = request.getParameter("place");
        HttpSession misesion = request.getSession();
        if (correo.equals("") || contra.equals("")) {
            if (place.equals("page")) {
                String men = "Llena todos los campos";
                response.sendRedirect("JSP/SesionUsuario.jsp?mens=" + men);
            } else if (place.equals("app")) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("Login", "camposVacios");
                    PrintWriter pw = response.getWriter();
                    pw.write(jsonObject.toString());
                    pw.print(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Usuario usuario = UsuarioBD.verificarUsuario(correo);
            if (correo.equals(usuario.getCorreo())) {
                if ((contra.equals(usuario.getContraseña()))) {
                    switch (usuario.getTipoUsuario()) {
                        case 3:
                            misesion = request.getSession(true);
                            misesion.setAttribute("correo", usuario.getCorreo());
                            misesion.setAttribute("miniaturaperro", miniaturaMascota(usuario.getCorreo()));
                            misesion.setAttribute("miniaturacitas", miniaturaCita(usuario.getCorreo()));
                            misesion.setAttribute("servicios", servicios());
                            misesion.setAttribute("productos", productos());
                            misesion.setAttribute("encargos", encargos(usuario.getCorreo()));
                            misesion.setAttribute("datoscliente", datosCliente(usuario.getCorreo()));
                            if (place.equals("page")) {
                                response.sendRedirect("JSP/cliente/home.jsp?id=" + miniaturaMascota(usuario.getCorreo()).get(0).getNombre());
                            } else if (place.equals("app")) {
                                JSONObject jsonObject1 = new JSONObject();
                                JSONArray jsArray = new JSONArray(productos());
                                try {
                                    jsonObject1.put("Login", "Cliente");
                                    PrintWriter pw = response.getWriter();
                                    pw.write(jsonObject1.toString());
                                    pw.write(jsArray.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case 2:                            
                            if (place.equals("page")) {
                                response.sendRedirect("HTML/empleado/home.html");
                            } else if (place.equals("app")) {
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("Login", "empleado");
                                    PrintWriter pw = response.getWriter();
                                    pw.write(jsonObject.toString());
                                    pw.print(jsonObject.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case 1:
                            misesion.setAttribute("correo", usuario.getCorreo());
                            System.out.println("El correo es " + usuario.getCorreo());
                            Encargado encargado = new Encargado();
                            misesion.setAttribute("servicios", encargado.serviciosEncargado());
                            misesion.setAttribute("productos", encargado.productosEncargado());
                            if (place.equals("page")) {
                                response.sendRedirect("JSP/encargado/home.jsp");
                            } else if (place.equals("app")) {
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("Login", "Encargado");
                                    PrintWriter pw = response.getWriter();
                                    pw.write(jsonObject.toString());
                                    pw.print(jsonObject.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        default:
                            break;
                    }
                } else {
                    if (place.equals("page")) {
                        String error = "Contraseña incorrecta";
                        response.sendRedirect("JSP/SesionUsuario.jsp?mens=" + error);
                    } else if (place.equals("app")) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("Login", "contraseñaIncorrecta");
                            PrintWriter pw = response.getWriter();
                            pw.write(jsonObject.toString());
                            pw.print(jsonObject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                if (place.equals("page")) {
                    String usua = "Usuario no encontrado";
                    response.sendRedirect("JSP/SesionUsuario.jsp?mens=" + usua);
                } else if (place.equals("app")) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("Login", "usuarioNoEncontrado");
                        PrintWriter pw = response.getWriter();
                        pw.write(jsonObject.toString());
                        pw.print(jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    private void regisrarMascota(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession misesion = (HttpSession) request.getSession();
        String correo = (String) misesion.getAttribute("correo");
        String place = request.getParameter("place");
        if (correo == null) {
            if (place.equals("page")) {
                response.sendRedirect("HTML/SesionUsuario.html");
            }
        } else {
            String nombre = request.getParameter("nomp");
            String nacimiento = request.getParameter("fecha");//aaaa-mm-dd
            String genero = request.getParameter("generop");
            String talla = request.getParameter("tallap");
            InputStream filecontent = null;
            String context = request.getServletContext().getRealPath("/Img");
            try {
                Part filePart = request.getPart("imagenp");
                if (filePart.getSize() > 0) {
                    System.out.println("la imagen se llama " + filePart.getName());
                    System.out.println("la imagen se midio " + filePart.getSize());
                    System.out.println("el content type " + filePart.getContentType());
                    System.out.println(context + "\\" + correo + "\\" + nombre + ".png");
                    filecontent = filePart.getInputStream();

                    OutputStream os = new FileOutputStream(context + "\\" + correo + "\\" + nombre + ".png");
                    System.out.println(context + "\\" + correo + "\\" + nombre + ".png");
                    int read = 0;
                    final byte[] bytes = new byte[1024];

                    while ((read = filecontent.read(bytes)) != -1) {
                        os.write(bytes, 0, read);
                    }
                    filecontent.close();
                    os.flush();
                    os.close();
                }
            } catch (Exception ex) {
                System.out.println("fichero: " + ex.getMessage());
            }
            if (nombre.equals("")
                    || (nacimiento.equals(""))
                    || (genero.equals(""))
                    || (talla.equals(""))
                    || (filecontent == null)) {

                if (place.equals("page")) {
                    System.out.println("Respuesta pagina");
                    System.out.println(nombre);
                    System.out.println(nacimiento);
                    System.out.println(genero);
                    System.out.println(talla);
                    String men = "Llena todos los campos";
                    response.sendRedirect("JSP/cliente/home.jsp?mens=" + men + "&id=" + miniaturaCita((String) request.getSession().getAttribute("correo")).get(0).getMascota());
                } else if (place.equals("app")) {

                }
            } else {
                Perro pe = new Perro();
                pe.setNombre(nombre);
                pe.setNacimiento(nacimiento);
                if (genero.equals("Macho")) {
                    pe.setGenero(true);//true macho
                } else {
                    pe.setGenero(false);//false hembra
                }
                pe.setTalla(talla);
                pe.setCodigos(0);
                pe.setDueno(correo);
                pe.setArchivoimg(context + "\\" + correo + "\\" + nombre + ".png");

                UsuarioBD usuarioBD = new UsuarioBD();
                boolean registrado = usuarioBD.registrarMascota(pe);
                if (registrado) {
                    misesion.removeAttribute("miniaturaperro");
                    misesion.setAttribute("miniaturaperro", miniaturaMascota(correo));
                    if (place.equals("page")) {
                        String men = "Registro correcto";
                        response.sendRedirect("JSP/cliente/home.jsp?mans=" + men + "&id=" + miniaturaCita((String) request.getSession().getAttribute("correo")).get(0).getMascota());
                    } else if (place.equals("app")) {

                    }
                } else {
                    if (place.equals("page")) {
                        String men = "Error al registrar";
                        response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);
                    } else if (place.equals("app")) {

                    }
                }
            }
        }
    }

    private void obtenerProductos(HttpServletRequest request, HttpServletResponse response) {

    }

    private String validarTel(String tel_clie) {
        int contador = 0;
        String comparar = "0123456789";
        for (int i = 0; i < tel_clie.length(); i++) {
            for (int j = 0; j < comparar.length(); j++) {
                if (tel_clie.charAt(i) == comparar.charAt(j)) {
                    contador++;
                }
            }
        }
        if (contador == 8 || contador == 10) {
            return tel_clie;
        } else {
            return null;
        }
    }

    public ArrayList<Perro> miniaturaMascota(String correo) throws IOException {
        UsuarioBD usu = new UsuarioBD();
        ArrayList<Perro> perro = usu.consutarMiniaturaPerro(correo);
        return perro;

    }

    public ArrayList<Encargo> encargos(String correo) throws IOException {
        UsuarioBD usu = new UsuarioBD();
        ArrayList<Encargo> encargos = usu.consultarEncargos(correo);
        return encargos;
    }

    private void eliminarMascota(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String correo = (String) request.getSession().getAttribute("correo");
        String mascota = request.getParameter("mascota");
        if (mascota != null) {
            UsuarioBD usu = new UsuarioBD();
            boolean eliminado = usu.eliminarMascota(mascota, correo);
            if (eliminado == true) {
                request.getSession().removeAttribute("miniaturaperro");
                request.getSession().setAttribute("miniaturaperro", miniaturaMascota(correo));
                String context = request.getServletContext().getRealPath("/Img");
                File f = new File(context + "\\" + correo + "\\" + mascota + ".png");
                System.out.println(context + "\\" + correo + "\\" + mascota + ".png");
                f.delete();
                if (request.getParameter("place").equals("page")) {
                    String men = "Se ha eliminado " + mascota;
                    response.sendRedirect("JSP/cliente/home.jsp?=" + men + "&id=" + miniaturaCita((String) request.getSession().getAttribute("correo")).get(0).getMascota());

                } else if (request.getParameter("place").equals("app")) {

                }
            }
        } else {

        }
    }

    private void editarMascota(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession misesion = (HttpSession) request.getSession();
        String correo = (String) misesion.getAttribute("correo");
        String place = request.getParameter("place");
        if (correo == null) {
            if (place.equals("page")) {
                response.sendRedirect("HTML/SesionUsuario.html");
            }
        } else {
            String nombre = request.getParameter("nombreperro");
            String nacimiento = request.getParameter("fechanacimiento");//aaaa-mm-dd
            String genero = request.getParameter("generoperro");
            String talla = request.getParameter("tallaperro");
            InputStream filecontent = null;
            String context = request.getServletContext().getRealPath("/Img");
            try {
                Part filePart = request.getPart("imagenp");
                if (filePart.getSize() > 0) {
                    System.out.println(context + "\\" + correo + "\\" + nombre + ".png");
                    filecontent = filePart.getInputStream();
                    OutputStream os = new FileOutputStream(context + "\\" + correo + "\\" + nombre + ".png");
                    System.out.println(context + "\\" + correo + "\\" + nombre + ".png");
                    int read = 0;
                    final byte[] bytes = new byte[1024];
                    while ((read = filecontent.read(bytes)) != -1) {
                        os.write(bytes, 0, read);
                    }
                    filecontent.close();
                    os.flush();
                    os.close();

                }

            } catch (Exception ex) {
                System.out.println("fichero: " + ex.getMessage());
            }

            if (nombre.equals("")
                    || (nacimiento.equals(""))
                    || (genero.equals(""))
                    || (talla.equals(""))) {

                if (place.equals("page")) {
                    String men = "Llena todos los campos";
                    response.sendRedirect("JSP/cliente/home.jsp?mens=" + men + "&id=" + miniaturaCita((String) request.getSession().getAttribute("correo")).get(0).getMascota());
                } else if (place.equals("app")) {

                }
            } else {
                if (filecontent != null) {
                    Perro perro = new Perro();
                    perro.setNombre(nombre);
                    perro.setNacimiento(nacimiento);
                    if (genero.equals("Macho")) {
                        perro.setGenero(true);//true macho
                    } else {
                        perro.setGenero(false);//false hembra
                    }
                    perro.setTalla(talla);
                    perro.setDueno(correo);
                    perro.setArchivoimg(context + "\\" + correo + "\\" + nombre + ".png");
                    File imgcambiar = new File(context + "\\" + correo + "\\" + request.getParameter("mascota") + ".png");
                    imgcambiar.renameTo(new File(context + "\\" + correo + "\\" + nombre + ".png"));

                    UsuarioBD usuarioBD = new UsuarioBD();
                    boolean editado = usuarioBD.editarMascota(perro, request.getParameter("mascota"));
                    if (editado == true) {
                        misesion.removeAttribute("miniaturaperro");
                        misesion.setAttribute("miniaturaperro", miniaturaMascota(correo));
                        if (place.equals("page")) {
                            String men = "Editado correcto";
                            response.sendRedirect("JSP/cliente/home.jsp" + men + "&id=" + miniaturaCita((String) request.getSession().getAttribute("correo")).get(0).getMascota());
                        } else if (place.equals("app")) {

                        }
                    } else {
                        if (place.equals("page")) {
                            String men = "Error al editar";
                            response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);
                        } else if (place.equals("app")) {
                        }
                    }
                } else {
                    //no hay imagen
                    Perro pe = new Perro();
                    pe.setNombre(nombre);
                    pe.setNacimiento(nacimiento);
                    if (genero.equals("Macho")) {
                        pe.setGenero(true);//true macho
                    } else {
                        pe.setGenero(false);//false hembra
                    }
                    pe.setTalla(talla);
                    pe.setDueno(correo);
                    File imgcambiar = new File(context + "\\" + correo + "\\" + request.getParameter("mascota") + ".png");
                    imgcambiar.renameTo(new File(context + "\\" + correo + "\\" + nombre + ".png"));
                    UsuarioBD u = new UsuarioBD();
                    if (u.editarMascota(pe, request.getParameter("mascota"))) {
                        misesion.removeAttribute("miniaturaperro");
                        misesion.setAttribute("miniaturaperro", miniaturaMascota(correo));
                        if (place.equals("page")) {
                            String men = "Registro correcto";
                            response.sendRedirect("JSP/cliente/home.jsp?mens=" + men + "&id=" + miniaturaCita((String) request.getSession().getAttribute("correo")).get(0).getMascota());
                        } else if (place.equals("app")) {
                        }
                        //En caso de que no se haya podido registrar    
                    } else {
                        if (place.equals("page")) {
                            String men = "Error al registrar";
                            response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);
                        } else if (place.equals("app")) {
                        }
                    }
                }
            }
        }
    }

    private void agendarCita(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //vaciar variables
        HttpSession misesion = (HttpSession) request.getSession();
        String correo = (String) misesion.getAttribute("correo");
        String fecha = request.getParameter("fechacita");
        String hora = request.getParameter("horacita");
        String mascota = request.getParameter("mascota");
        String servicio = request.getParameter("servicio");

        //validacion sesion iniciada
        if (correo == null) {
            if (request.getParameter("place").equals("page")) {
                response.sendRedirect("HTML/SesionUsuario.html");
            } else if (request.getParameter("app").equals("app")) {

            }

        } else {
            if (fecha.equals("") || hora.equals("") || mascota.equals("") || servicio.equals("")) {
                if (request.getParameter("place").equals("page")) {
                    String mens = "llene todos los campos";
                    response.sendRedirect("JSP/cliente/home.jsp?mens=" + mens);
                } else if (request.getParameter("app").equals("app")) {

                }
            } else {
                Cita cita = new Cita();
                cita.setCliente(correo);
                cita.setEstado(false);
                cita.setFecha(fecha);
                cita.setHora(hora);
                cita.setMascota(mascota);
                cita.setCodigo(cita.generarCodigo());
                DescripcionServicio ds = new DescripcionServicio();
                ds.setCodigoCita(cita.generarCodigo());
                ds.setNombreServicio(servicio);
                UsuarioBD usu = new UsuarioBD();
                if (usu.altaCita(cita) && usu.altaServicio(ds)) {
                    if (request.getParameter("place").equals("page")) {
                        misesion.removeAttribute("miniaturacitas");
                        misesion.setAttribute("miniaturacitas", miniaturaCita((String) misesion.getAttribute("correo")));
                        String men = "Cita enviada, espera la respuesta de confirmacion";
                        response.sendRedirect("JSP/cliente/home.jsp?mens=" + men + "&id=" + miniaturaCita((String) request.getSession().getAttribute("correo")).get(0).getMascota());
                    } else if (request.getParameter("place").equals("app")) {

                    }
                }

            }
        }
    }

    public ArrayList<Cita> miniaturaCita(String correo) throws IOException {
        UsuarioBD usu = new UsuarioBD();
        ArrayList<Cita> citas = usu.consutarMiniaturaCita(correo);
        return citas;
    }

    public ArrayList<Servicio> servicios() {
        UsuarioBD usu = new UsuarioBD();
        ArrayList<Servicio> servicios = usu.consultarServicios();
        return servicios;
    }

    public ArrayList<Producto> productos() {
        UsuarioBD usu = new UsuarioBD();
        ArrayList<Producto> productos = usu.consultarProductos();
        return productos;
    }

    private void encargarProducto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession misesion = (HttpSession) request.getSession();
        String correo = (String) request.getSession().getAttribute("correo");
        String producto = request.getParameter("producto");
        String cantidad = request.getParameter("cantidad");
        String cita = request.getParameter("cita");
        String place = request.getParameter("place");
        if (correo != null) {
            if (producto.equals("") || cantidad.equals("") || cita.equals("")) {
                if (place.equals("page")) {
                    String men = "Llena todos los campos por favor";
                    response.sendRedirect("JSP/cliente/Servicios.jsp?mens=" + men);
                } else if (place.equals("app")) {

                }
            } else {
                if (request.getParameter("boton").equals("Borrar")) {
                    Encargo encargo = new Encargo();
                    encargo.setArticulo(producto);
                    encargo.setCorreo(correo);
                    encargo.setCita(cita);
                    UsuarioBD usu = new UsuarioBD();
                    if (usu.borrarEncargo(encargo)) {
                        if (place.equals("page")) {
                            String men = "Se ha borrado el encargo exitosamente";
                            misesion.removeAttribute("encargos");
                            misesion.setAttribute("encargos", encargos(correo));
                            response.sendRedirect("JSP/cliente/Servicios.jsp?mens=" + men);
                        } else if (place.equals("app")) {

                        }
                    }
                } else {
                    Encargo encargo = new Encargo();
                    encargo.setArticulo(producto);
                    encargo.setCantidad(Integer.parseInt(cantidad));
                    encargo.setCorreo(correo);
                    encargo.setCita(cita);
                    UsuarioBD usu = new UsuarioBD();
                    if (usu.encargarProducto(encargo)) {
                        if (place.equals("page")) {
                            String men = "Se ha realizado en encargo exitosamente";
                            misesion.removeAttribute("encargos");
                            misesion.setAttribute("encargos", encargos(correo));
                            response.sendRedirect("JSP/cliente/Servicios.jsp?mens=" + men);
                        } else if (place.equals("app")) {

                        }
                    } else {
                        String men = "Algo salio mal";
                        misesion.removeAttribute("encargos");
                        misesion.setAttribute("encargos", encargos(correo));
                        response.sendRedirect("JSP/cliente/Servicios.jsp?mens=" + men);
                    }
                }
            }
        } else {
            if (place.equals("page")) {
                response.sendRedirect("HTML/SesionUsuario.html");
            }
        }
    }

    private void editarCita(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession misesion = (HttpSession) request.getSession();
        String correo = (String) misesion.getAttribute("correo");
        String place = request.getParameter("place");
        if (correo == null) {
            if (place.equals("page")) {
                response.sendRedirect("HTML/SesionUsuario.html");
            }
        } else {
            if (request.getParameter("boton").equals("Editar")) {
                String codigo = request.getParameter("codigo");
                String fecha = request.getParameter("fechacita");
                String hora = request.getParameter("horacita");
                String mascota = request.getParameter("mascota");
                String servicio = request.getParameter("servicio");
                if (fecha.equals("") || hora.equals("") || mascota.equals("") || servicio.equals("")) {
                    if (request.getParameter("place").equals("page")) {
                        String mens = "llene todos los campos";
                        response.sendRedirect("JSP/cliente/home.jsp?mens=" + mens);
                    } else if (request.getParameter("app").equals("app")) {

                    }
                } else {
                    Cita cita = new Cita();
                    cita.setCliente(correo);
                    cita.setEstado(false);
                    cita.setFecha(fecha);
                    cita.setHora(hora);
                    cita.setMascota(mascota);
                    cita.setCodigo(codigo);
                    DescripcionServicio ds = new DescripcionServicio();
                    ds.setCodigoCita(codigo);
                    ds.setNombreServicio(servicio);
                    UsuarioBD usu = new UsuarioBD();
                    usu.editarCita(cita);
                    usu.editarServicio(ds);
                    if (request.getParameter("place").equals("page")) {
                        misesion.removeAttribute("miniaturacitas");
                        misesion.setAttribute("miniaturacitas", miniaturaCita((String) misesion.getAttribute("correo")));
                        String men = "Edicion enviada, espera la respuesta de confirmacion";
                        response.sendRedirect("JSP/cliente/home.jsp?mens=" + men + "&id=" + miniaturaCita((String) request.getSession().getAttribute("correo")).get(0).getMascota());
                    } else if (request.getParameter("place").equals("app")) {

                    }
                }
            } else if (request.getParameter("boton").equals("Borrar")) {
                String codigo = request.getParameter("codigo");
                String fecha = request.getParameter("fechacita");
                String hora = request.getParameter("horacita");
                String mascota = request.getParameter("mascota");
                String servicio = request.getParameter("servicio");
                if (fecha.equals("") || hora.equals("") || mascota.equals("") || servicio.equals("")) {
                    if (request.getParameter("place").equals("page")) {
                        String mens = "llene todos los campos";
                        response.sendRedirect("JSP/cliente/home.jsp?mens=" + mens);
                    } else if (request.getParameter("app").equals("app")) {

                    }
                } else {
                    Cita cita = new Cita();
                    cita.setCliente(correo);
                    cita.setEstado(false);
                    cita.setFecha(fecha);
                    cita.setHora(hora);
                    cita.setMascota(mascota);
                    System.out.println(codigo);
                    cita.setCodigo(codigo);
                    DescripcionServicio ds = new DescripcionServicio();
                    ds.setCodigoCita(codigo);
                    ds.setNombreServicio(servicio);
                    UsuarioBD usu = new UsuarioBD();
                    usu.cancelarCita(cita, ds);
                    if (request.getParameter("place").equals("page")) {
                        misesion.removeAttribute("miniaturacitas");
                        misesion.setAttribute("miniaturacitas", miniaturaCita((String) misesion.getAttribute("correo")));
                        misesion.removeAttribute("encargos");
                        misesion.setAttribute("encargos", encargos((String) misesion.getAttribute("correo")));
                        String men = "Se ha cancelado la cita";
                        response.sendRedirect("JSP/cliente/home.jsp?mens=" + men);
                    } else if (request.getParameter("place").equals("app")) {

                    }
                }
            }
        }
    }

    private Usuario datosCliente(String correo) {
        UsuarioBD usu = new UsuarioBD();
        return usu.consultarDatos(correo);

    }

    private void editarDatos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession misesion = (HttpSession) request.getSession();
        if(misesion!=null){
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String telefono = request.getParameter("telefono");
            String direccion = request.getParameter("direccion");
            if(telefono.length()==8 || telefono.length()== 10){
                Usuario usuario = new Usuario();
                usuario.setNombre(nombre);
                usuario.setApellido(apellido);
                usuario.setTelefono(telefono);
                usuario.setDireccion(direccion);
                UsuarioBD usuBD = new UsuarioBD();
                if(usuBD.editarDatosUsuario(usuario, (String) misesion.getAttribute("correo"))){
                    if (request.getParameter("place").equals("page")) {
                        misesion.removeAttribute("datoscliente");
                        misesion.setAttribute("datoscliente", datosCliente((String) misesion.getAttribute("correo")));
                        String men = "Se ha modificado exitosamente";
                        response.sendRedirect("JSP/cliente/Perfil.jsp?mens=" + men);
                    } else if (request.getParameter("place").equals("app")) {

                    }
                }
            }else{
                
            }
        }
    }


}
