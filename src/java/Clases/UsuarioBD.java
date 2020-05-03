/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author jela3
 */
public class UsuarioBD {

    public static boolean registrarUsuario(Usuario usu) {
        boolean resp = false;

        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();

        try {
            PreparedStatement ps = cn.prepareStatement("INSERT INTO usuario VALUES (?,?,?)");
            ps.setString(1, usu.getCorreo());
            ps.setString(2, usu.getContraseña());
            ps.setInt(3, usu.getTipoUsuario());
            int i = ps.executeUpdate();

            //En caso de que se haya podido agregar el usuario correctamente
            if (i == 1) {

                PreparedStatement ps1 = cn.prepareStatement("INSERT INTO persona (nom_pers,tel_pers,dir_pers,cor_usu) VALUES (?,?,?,?)");
                ps1.setString(1, usu.getNombre());
                ps1.setString(2, usu.getTelefono());
                ps1.setString(3, usu.getDireccion());
                ps1.setString(4, usu.getCorreo());
                int j = ps1.executeUpdate();
                if (j == 1) {
                    resp = true;
                }
            } else {
                resp = false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return resp;
    }

    public static Usuario verificarUsuario(String correo) {
        Usuario usu = new Usuario();

        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();

        try {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM usuario WHERE (cor_usu = ?)");
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                usu.setCorreo(rs.getString("cor_usu"));
                usu.setContraseña(rs.getString("con_usu"));
                usu.setTipoUsuario(Integer.parseInt(rs.getString("id_tip")));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return usu;
    }

    public static boolean registrarMascota(Perro per) {
        boolean resp = false;

        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        try {
            boolean genero = per.getGenero();
            PreparedStatement ps = cn.prepareStatement("INSERT INTO perro(nom_per,fot_per,nac_per,gen_per,tall_per,cod_per,cor_usu) VALUES (?,?,?,?,?,?,?)");
            ps.setString(1, per.getNombre());
            ps.setString(2, per.getArchivoimg());
            ps.setString(3, per.getNacimiento());
            if (genero == true) {
                //Macho
                ps.setInt(4, 1);
            } else {
                //Hembra
                ps.setInt(4, 0);
            }
            ps.setString(5, per.getTalla());
            ps.setInt(6, 0);
            ps.setString(7, per.getDueno());
            int i = ps.executeUpdate();
            if (i == 1) {
                resp = true;
            }
        } catch (Exception e) {

        }
        return resp;
    }

    public static ArrayList<Producto> obtenerProductos() {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        ArrayList<Producto> obtenido = new ArrayList();
        try {
            PreparedStatement ps = cn.prepareStatement("SELECT (nom_art,des_art,fot_art,pre_art,exi_art) FROM usuario WHERE (exi_art > 0)");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto pro = new Producto();
                pro.setNombre(rs.getString("nom_art"));
                pro.setDes(rs.getString("des_art"));
                //convertir blob a inputStream
                //pro.setFoto(rs.getBlob("fot_art"));
                pro.setPrecio(rs.getFloat("pre_art"));
                obtenido.add(pro);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return obtenido;
    }

    public static ArrayList<Perro> consutarMiniaturaPerro(String correo) {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        ArrayList<Perro> obtenido = new ArrayList();
        try {
            PreparedStatement ps = cn.prepareStatement("SELECT id_per, nom_per, fot_per, nac_per, gen_per, tall_per, cod_per  FROM perro  WHERE (cor_usu= ?)");
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Perro perro = new Perro();
                perro.setId(rs.getInt("id_per"));
                perro.setNombre(rs.getString("nom_per"));
                perro.setArchivoimg(rs.getString("fot_per"));
                perro.setGenero(rs.getBoolean("gen_per"));
                perro.setNacimiento(rs.getString("nac_per"));
                perro.setTalla(rs.getString("tall_per"));
                //perro.setCodigos(rs.getInt("cod_per"));
                obtenido.add(perro);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return obtenido;
    }

    public static boolean eliminarMascota(String nom, String due) {

        boolean resp = false;
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();

        try {
            PreparedStatement ps = cn.prepareStatement("DELETE FROM perro WHERE nom_per=? and cor_usu=?");
            ps.setString(1, nom);
            ps.setString(2, due);
            int i = ps.executeUpdate();

            //En caso de que se haya podido eliminar mascota correctamente
            if (i == 1) {
                resp = true;
            } else {
                resp = false;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return resp;
    }

    public boolean editarMascota(Perro pe, String nomanterior) {
        boolean resp = false;

        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        if (pe.getArchivoimg() != null) {
            try {
                boolean genero = pe.getGenero();
                PreparedStatement ps = cn.prepareStatement(" UPDATE perro SET nom_per=?, fot_per=?, nac_per=?, gen_per=?, tall_per=? WHERE nom_per=? AND cor_usu=?;");
                ps.setString(1, pe.getNombre());
                ps.setString(2, pe.getArchivoimg());
                ps.setString(3, pe.getNacimiento());
                if (genero == true) {
                    //Macho
                    ps.setInt(4, 1);
                } else {
                    //Hembra
                    ps.setInt(4, 0);
                }
                ps.setString(5, pe.getTalla());
                ps.setString(6, nomanterior);
                ps.setString(7, pe.getDueno());
                int i = ps.executeUpdate();
                if (i == 1) {
                    resp = true;
                }
            } catch (Exception e) {

            }
            return resp;
        } else {
            try {
                boolean genero = pe.getGenero();
                PreparedStatement ps = cn.prepareStatement(" UPDATE perro SET nom_per=?, nac_per=?, gen_per=?, tall_per=? WHERE nom_per=? AND cor_usu=?;");
                ps.setString(1, pe.getNombre());
                ps.setString(2, pe.getNacimiento());
                if (genero == true) {
                    //Macho
                    ps.setInt(3, 1);
                } else {
                    //Hembra
                    ps.setInt(3, 0);
                }
                ps.setString(4, pe.getTalla());
                ps.setString(5, nomanterior);
                ps.setString(6, pe.getDueno());
                int i = ps.executeUpdate();
                if (i == 1) {
                    resp = true;
                }
            } catch (Exception e) {

            }
        }
        return resp;
    }

    public static boolean altaCita(Cita cita) {
        boolean resp = false;
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();

        try {
            PreparedStatement ps = cn.prepareStatement("INSERT into cita (fec_cit, hor_cit, est_cit, codi_cit, id_per, cor_usu, fin_cit) VALUES (?,?,?,?,(SELECT id_per FROM perro WHERE nom_per=? and cor_usu=?),?,?)");
            ps.setDate(1, java.sql.Date.valueOf(cita.getFecha()));
            ps.setTime(2, java.sql.Time.valueOf(cita.getHora() + ":00"));
            ps.setInt(3, 0);
            ps.setString(4, cita.getCodigo());
            ps.setString(5, cita.getMascota());
            ps.setString(6, cita.getCliente());
            //ps.setString(7, String.valueOf(rs.getInt("id_per")));
            ps.setString(7, cita.getCliente());
            ps.setInt(8, 0);
            int i = ps.executeUpdate();
            System.out.println("se ejecuto");
            System.out.println(i);
            if (i == 1) {
                resp = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    public static ArrayList<Cita> consutarMiniaturaCita(String correo) {
        System.out.println("pidiocitas");
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        ArrayList<Cita> obtenido = new ArrayList();
        try {
            PreparedStatement ps = cn.prepareStatement("SELECT fec_cit, hor_cit, codi_cit, est_cit, nom_per  FROM cita NATURAL JOIN perro WHERE cor_usu= ? AND fin_cit=0");
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cita cita = new Cita();
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                cita.setFecha(df.format(rs.getDate("fec_cit")));
                cita.setHora(rs.getTime("hor_cit") + "00");
                cita.setCodigo(rs.getString("codi_cit"));
                cita.setMascota(rs.getString("nom_per"));
                cita.setEstado(rs.getBoolean("est_cit"));
                obtenido.add(cita);

                System.out.println(cita.getMascota());
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return obtenido;
    }

    public static boolean editarCita(Cita cita) {
        System.out.println("si llama al metodo");
        boolean resp = false;
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();

        try {

            PreparedStatement ps2 = cn.prepareStatement("SELECT id_per FROM perro WHERE nom_per=? and cor_usu=?");
            ps2.setString(1, cita.getMascota());
            ps2.setString(2, cita.getCliente());
            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {
                PreparedStatement ps = cn.prepareStatement("UPDATE cita SET fec_cit=?, hor_cit=?, est_cit=?, codi_cit=?, cor_usu=?, fin_cit=? WHERE cod_cit=?");
                ps.setDate(1, java.sql.Date.valueOf(cita.getFecha()));
                ps.setTime(2, java.sql.Time.valueOf(cita.getHora() + ":00"));
                ps.setInt(3, 0);
                ps.setString(4, cita.getCodigo());
                ps.setString(5, String.valueOf(rs.getInt("id_per")));
                ps.setString(6, cita.getCliente());
                ps.setInt(7, 0);
                int i = ps.executeUpdate();
                System.out.println("se ejecuto");
                System.out.println(i);
                if (i == 1) {
                    resp = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    public static boolean altaServicio(DescripcionServicio descripcionServicio) {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        boolean resp= false;
        //INSERT INTO descripcion (com_des, cal_des, id_cit, id_ser, id_ven) VALUES ('f',5.0,(SELECT id_cit FROM cita WHERE codi_cit='20jela@efe05311900'),(SELECT id_ser FROM servicio WHERE nom_ser='Baño pequeño'),1);
        try {
            PreparedStatement ps = cn.prepareStatement("INSERT INTO descripcion (com_des, cal_des, id_cit, id_ser, id_ven) VALUES (NULL,NULL,(SELECT id_cit FROM cita WHERE codi_cit=?),(SELECT id_ser FROM servicio WHERE nom_ser=?),?)");
            ps.setString(1, descripcionServicio.getCodigoCita());
            ps.setString(2, descripcionServicio.getNombreServicio());
            //ps.setInt(3, descripcionServicio.getIdVenta());
            if(descripcionServicio.getIdVenta()==null){
                ps.setNull(3, 0);
            }else{
                ps.setInt(3, descripcionServicio.getIdVenta());
            }
            int i = ps.executeUpdate();
            System.out.println("se ejecuto");
            System.out.println(i);
            if (i == 1) {
                resp = true;
            }
        } catch (Exception e) {
            System.out.println("f al insertar");
            System.out.println(e.toString());
        }
        return resp;
    }

    public static ArrayList<Servicio> consultarServicios() {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        ArrayList<Servicio> servicios = new ArrayList<Servicio>();
        try {
            PreparedStatement ps = cn.prepareStatement("SELECT nom_ser, des_ser, pre_ser FROM servicio");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Servicio ser = new Servicio();
                ser.setNombreservicio(rs.getString("nom_ser"));
                ser.setDescripcion(rs.getString("des_ser"));
                ser.setPrecio(rs.getFloat("pre_ser"));
                servicios.add(ser);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return servicios;
    }
}
