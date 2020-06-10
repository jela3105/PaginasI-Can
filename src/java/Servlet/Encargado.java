/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Clases.EncargadoBD;
import Clases.Producto;
import Clases.Servicio;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author jela3
 */
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class Encargado extends HttpServlet {

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
        if (ac.equals("modificarServicio")) {
            modificarServicio(request, response);
        } else if (ac.equals("agregarServicio")) {
            agregarServicio(request, response);
        }
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
        processRequest(request, response);
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

    private void modificarServicio(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession misesion = (HttpSession) request.getSession();
        String correo = (String) misesion.getAttribute("correo");
        String place = request.getParameter("place");

        if (correo == null) {
            if (place.equals("page")) {
                response.sendRedirect("HTML/SesionUsuario.html");
            }
        } else {
            String nombreOriginal = request.getParameter("servicio");
            String nombreCambiado = request.getParameter("nombreservicio");
            String descripcion = request.getParameter("descripcion");
            String visible = request.getParameter("visible");
            String precio = request.getParameter("precio");
            InputStream filecontent = null;
            String context = request.getServletContext().getRealPath("/Img");
            try {
                Part filePart = request.getPart("imagenp");
                if (filePart.getSize() > 0) {
                    System.out.println(context + "\\servicios" + "\\" + nombreCambiado + ".jpg");
                    filecontent = filePart.getInputStream();
                    OutputStream os = new FileOutputStream(context + "\\servicios" + "\\" + nombreCambiado + ".jpg");
                    System.out.println(context + "\\" + correo + "\\" + nombreCambiado + ".jpg");
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

            Servicio servicio = new Servicio();
            servicio.setNombreservicio(nombreCambiado);
            servicio.setDescripcion(descripcion);
            if (visible.equals("on")) {
                servicio.setVisible(true);
            } else {
                servicio.setVisible(false);
            }
            servicio.setPrecio(precio);

            File imgcambiar = new File(context + "\\servicios" + "\\" + nombreOriginal + ".jpg");
            imgcambiar.renameTo(new File(context + "\\servicios" + "\\" + nombreCambiado + ".jpg"));

            EncargadoBD eBD = new EncargadoBD();
            if (eBD.editarServicio(servicio, nombreOriginal)) {
                //System.out.println(precio.replaceAll("(\n|\r)", ";"));
                misesion.removeAttribute("servicios");
                misesion.setAttribute("servicios", serviciosEncargado());
                if (request.getParameter("place").equals("page")) {
                    response.sendRedirect("JSP/encargado/Servicios.jsp");
                }
            }

        }
    }

    public ArrayList<Servicio> serviciosEncargado() {
        EncargadoBD eBD = new EncargadoBD();
        return eBD.consultarServicios();
    }

    public ArrayList<Producto> productosEncargado() {
        EncargadoBD eBD = new EncargadoBD();
        return eBD.consultarProductos();
    }

    private void agregarServicio(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession misesion = (HttpSession) request.getSession();
        String correo = (String) misesion.getAttribute("correo");
        String place = request.getParameter("place");
        if (correo == null) {
            if (place.equals("page")) {
                response.sendRedirect("HTML/SesionUsuario.html");
            }
        } else {
            String nombre = request.getParameter("nombreservicio");
            String descripcion = request.getParameter("descripcion");
            String visible = request.getParameter("visible");
            String precio = request.getParameter("precio");

            InputStream filecontent = null;
            String context = request.getServletContext().getRealPath("/Img");
            try {
                Part filePart = request.getPart("imagenp");
                if (filePart.getSize() > 0) {
                    System.out.println(context + "\\servicios" + "\\" + nombre + ".jpg");
                    filecontent = filePart.getInputStream();
                    OutputStream os = new FileOutputStream(context + "\\servicios" + "\\" + nombre + ".jpg");
                    System.out.println(context + "\\" + correo + "\\" + nombre + ".jpg");
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
            if (nombre.equals("") || (descripcion.equals("")) || (precio.equals(""))) {
                if (place.equals("page")) {
                    String men = "Llena todos los campos";
                    response.sendRedirect("JSP/encargado/home.jsp?mens=" + men);
                } else if (place.equals("app")) {

                }
            } else {
                Servicio servicio = new Servicio();
                servicio.setNombreservicio(nombre);
                servicio.setDescripcion(descripcion);
                if (visible.equals("on")) {
                    servicio.setVisible(true);
                } else {
                    servicio.setVisible(false);
                }
                servicio.setPrecio(precio);
                EncargadoBD eBD = new EncargadoBD();
            if (eBD.altaServicio(servicio)) {
                //System.out.println(precio.replaceAll("(\n|\r)", ";"));
                misesion.removeAttribute("servicios");
                misesion.setAttribute("servicios", serviciosEncargado());
                if (request.getParameter("place").equals("page")) {
                    response.sendRedirect("JSP/encargado/Servicios.jsp");
                }
            }
            }
        }

    }

}
