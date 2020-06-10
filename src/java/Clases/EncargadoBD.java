/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Conexion.Conexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author jela3
 */
public class EncargadoBD {

    public static ArrayList<Producto> consultarProductos() {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        ArrayList<Producto> productos = new ArrayList();
        try {
            PreparedStatement ps = cn.prepareStatement("SELECT nom_art,des_art,pre_art,exi_art FROM articulo");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto pro = new Producto();
                pro.setNombre(rs.getString("nom_art"));
                pro.setDescripcion(rs.getString("des_art"));
                pro.setPrecio(rs.getFloat("pre_art"));
                pro.setExistencia(rs.getInt("exi_art"));
                productos.add(pro);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return productos;
    }

    public ArrayList<Servicio> consultarServicios() {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        ArrayList<Servicio> servicios = new ArrayList<Servicio>();
        try {
            PreparedStatement ps = cn.prepareStatement("SELECT nom_ser, des_ser, vis_ser FROM servicio");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Servicio ser = new Servicio();
                ser.setNombreservicio(rs.getString("nom_ser"));
                ser.setDescripcion(rs.getString("des_ser"));
                if (rs.getInt("vis_ser") == 1) {
                    ser.setVisible(true);
                } else {
                    ser.setVisible(false);
                }
                String precio = "";
                try {
                    PreparedStatement ps2 = cn.prepareStatement("SELECT tam_ser , pre_pre FROM precio NATURAL JOIN servicio WHERE nom_ser=?");
                    ps2.setString(1, ser.getNombreservicio());
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        precio = precio + rs2.getString("tam_ser") + ": $" + String.valueOf(rs2.getFloat("pre_pre") + "\n");

                    }
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                ser.setPrecio(precio);
                servicios.add(ser);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("servicios consultados");
        return servicios;
    }

    public boolean editarServicio(Servicio servicio, String nombreOriginal) {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        boolean resp = false;
        //INSERT INTO descripcion (com_des, cal_des, id_cit, id_ser, id_ven) VALUES ('f',5.0,(SELECT id_cit FROM cita WHERE codi_cit='20jela@efe05311900'),(SELECT id_ser FROM servicio WHERE nom_ser='Baño pequeño'),1);
        try {
            PreparedStatement ps = cn.prepareStatement("UPDATE servicio SET nom_ser=?, des_ser=?, vis_ser=? WHERE nom_ser=?");
            ps.setString(1, servicio.getNombreservicio());
            ps.setString(2, servicio.getDescripcion());
            if (servicio.isVisible()) {
                ps.setInt(3, 1);
            } else {
                ps.setInt(3, 0);
            }
            ps.setString(4, nombreOriginal);
            ps.executeUpdate();
            System.out.println("se ejecuto");
            String preciosJuntos = servicio.getPrecio().replaceAll("(\n|\r)", ";");
            String[] precios = preciosJuntos.split(";;");
            for (int i = 0; i < precios.length; i++) {
                float precio = Float.parseFloat(precios[i].substring(precios[i].indexOf("$") + 1));
                String tamano = precios[i].substring(0, precios[i].indexOf(":"));
                System.out.println(tamano);
                System.out.println(precio + " f");
                PreparedStatement ps1 = cn.prepareStatement("UPDATE servicio NATURAL JOIN precio SET pre_pre=? WHERE nom_ser=? AND tam_ser=?");
                ps1.setFloat(1, precio);
                ps1.setString(2, servicio.getNombreservicio());
                ps1.setString(3, tamano);
                ps1.execute();
            }
            //PreparedStatement ps1 = cn.prepareStatement("UPDATE servicio NATURAL JOIN precio SET ");
            resp = true;
        } catch (Exception e) {
            System.out.println("f al editar");
            System.out.println(e.toString());
        }
        return resp;
    }

    public boolean altaServicio(Servicio servicio) {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        boolean resp = false;
        try {
            PreparedStatement ps = cn.prepareStatement("INSERT INTO servicio (nom_ser,des_ser,vis_ser) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            System.out.println("antes de ejecutar es statement");
            ps.setString(1, servicio.getNombreservicio());
            ps.setString(2, servicio.getDescripcion());
            if (servicio.isVisible()) {
                ps.setInt(3, 1);
            } else {
                ps.setInt(3, 0);
            }
            ps.executeUpdate();
            System.out.println("se ejecuto");
            int idGenerado;
            ResultSet generatedKey = ps.getGeneratedKeys();
            if (generatedKey.next()) {
                idGenerado = generatedKey.getInt(1);
                String preciosJuntos = servicio.getPrecio().replaceAll("(\n|\r)", ";");
                String[] precios = preciosJuntos.split(";;");
                for (int i = 0; i < precios.length; i++) {
                    float precio = Float.parseFloat(precios[i].substring(precios[i].indexOf("$") + 1));
                    String tamano = precios[i].substring(0, precios[i].indexOf(":"));
                    System.out.print(idGenerado + tamano + precio);
                    PreparedStatement ps1 = cn.prepareStatement("INSERT INTO precio (id_ser, tam_ser, pre_pre) VALUES (?,?,?)");
                    ps1.setInt(1, idGenerado);
                    ps1.setString(2, tamano);
                    ps1.setFloat(3, precio);
                    ps1.executeUpdate();

                }
            }

            resp = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    public boolean editarProducto(Producto producto, String nombreOriginal) {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        boolean resp = false;
        try {
            PreparedStatement ps = cn.prepareStatement("UPDATE articulo SET nom_art=?, des_art=?, pre_art=?, exi_art=? WHERE nom_art=?");
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setFloat(3, producto.getPrecio());
            ps.setInt(4, producto.getExistencia());
            ps.setString(5, nombreOriginal);
            ps.executeUpdate();
            resp = true;
        } catch (Exception e) {
            e.printStackTrace();
            resp=false;
        }
        return resp;
    }

}
