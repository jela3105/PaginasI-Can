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

                PreparedStatement ps1 = cn.prepareStatement("INSERT INTO persona (nom_pers, ape_pers, tel_pers,dir_pers,cor_usu,ape_pers) VALUES (?,?,?,?,?)");
                ps1.setString(1, usu.getNombre());
                ps1.setString(2, usu.getApellido());
                ps1.setString(3, usu.getTelefono());
                ps1.setString(4, usu.getDireccion());
                ps1.setString(5, usu.getCorreo());
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

    public static ArrayList<Producto> consultarProductos() {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        ArrayList<Producto> productos = new ArrayList();
        try {
            PreparedStatement ps = cn.prepareStatement("SELECT nom_art,des_art,fot_art,pre_art,exi_art FROM articulo WHERE (exi_art > 0)");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto pro = new Producto();
                pro.setNombre(rs.getString("nom_art"));
                pro.setDescripcion(rs.getString("des_art"));
                pro.setFoto(rs.getString("fot_art"));
                pro.setPrecio(rs.getFloat("pre_art"));
                productos.add(pro);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return productos;
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
            PreparedStatement ps = cn.prepareStatement("SELECT fec_cit, hor_cit, codi_cit, est_cit, nom_per, nom_ser  FROM cita NATURAL JOIN perro NATURAL JOIN servicio NATURAL JOIN descripcion WHERE cor_usu= ? AND fin_cit=0 ORDER BY est_cit DESC, fec_cit ASC, hor_cit ASC");
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
                cita.setServicio(rs.getString("nom_ser"));
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

            PreparedStatement ps = cn.prepareStatement("UPDATE cita SET fec_cit=? , hor_cit=? , est_cit=? , id_per=(SELECT id_per FROM perro WHERE nom_per=? and cor_usu=?) WHERE codi_cit=?");
            ps.setDate(1, java.sql.Date.valueOf(cita.getFecha()));
            System.out.println(java.sql.Date.valueOf(cita.getFecha()));
            ps.setTime(2, java.sql.Time.valueOf(cita.getHora() + ":00"));
            System.out.println(java.sql.Time.valueOf(cita.getHora() + ":00"));
            ps.setInt(3, 0);
            ps.setString(4, cita.getMascota());
            System.out.println(cita.getMascota());
            ps.setString(5, cita.getCliente());
            System.out.println(cita.getCliente());

            ps.setString(6, cita.getCodigo());
            System.out.println(cita.getCodigo());
            int i = ps.executeUpdate();
            resp = true;
            System.out.println("se ejecuto");
            System.out.println(i);

        } catch (Exception e) {
            e.toString();
        }
        return resp;
    }

    public static boolean altaServicio(DescripcionServicio descripcionServicio) {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        boolean resp = false;
        //INSERT INTO descripcion (com_des, cal_des, id_cit, id_ser, id_ven) VALUES ('f',5.0,(SELECT id_cit FROM cita WHERE codi_cit='20jela@efe05311900'),(SELECT id_ser FROM servicio WHERE nom_ser='Baño pequeño'),1);
        try {
            PreparedStatement ps = cn.prepareStatement("INSERT INTO descripcion (com_des, cal_des, id_cit, id_ser, id_ven) VALUES (NULL,NULL,(SELECT id_cit FROM cita WHERE codi_cit=?),(SELECT id_ser FROM servicio WHERE nom_ser=?),?)");
            ps.setString(1, descripcionServicio.getCodigoCita());
            ps.setString(2, descripcionServicio.getNombreServicio());
            //ps.setInt(3, descripcionServicio.getIdVenta());
            if (descripcionServicio.getIdVenta() == null) {
                ps.setNull(3, 0);
            } else {
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
            PreparedStatement ps = cn.prepareStatement("SELECT nom_ser, des_ser FROM servicio WHERE vis_ser=1");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Servicio ser = new Servicio();
                ser.setNombreservicio(rs.getString("nom_ser"));
                ser.setDescripcion(rs.getString("des_ser"));
                String precio = "";
                try {
                    PreparedStatement ps2 = cn.prepareStatement("SELECT tam_ser , pre_pre FROM precio NATURAL JOIN servicio WHERE nom_ser=?");
                    ps2.setString(1, ser.getNombreservicio());
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        precio = precio + rs2.getString("tam_ser") + ": $" + String.valueOf(rs2.getFloat("pre_pre") + ";");

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

    public static boolean encargarProducto(Encargo encargo) {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        boolean resp = false;
        try {
            PreparedStatement ps = cn.prepareStatement("SELECT id_ven FROM descripcion NATURAL JOIN cita WHERE codi_cit=? AND cor_usu=?");
            ps.setString(1, encargo.getCita());
            ps.setString(2, encargo.getCorreo());

            ResultSet rs = ps.executeQuery();
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("id_ven");
            }
            if (id == 0) {
                PreparedStatement ps1 = cn.prepareStatement("INSERT INTO venta (fin_ven, cor_usu) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
                ps1.setInt(1, 0);
                ps1.setString(2, encargo.getCorreo());
                ps1.executeUpdate();

                int idGenerado;
                ResultSet generatedKey = ps1.getGeneratedKeys();
                if (generatedKey.next()) {
                    idGenerado = generatedKey.getInt(1);
                    PreparedStatement ps2 = cn.prepareStatement("INSERT INTO ticket (can_tic, id_ven, id_art) VALUES (?,?,(SELECT id_art FROM articulo WHERE nom_art =?))");
                    ps2.setInt(1, encargo.getCantidad());
                    ps2.setInt(2, idGenerado);
                    ps2.setString(3, encargo.getArticulo());
                    int j = ps2.executeUpdate();
                    if (j == 1) {
                        PreparedStatement ps3 = cn.prepareStatement("UPDATE descripcion NATURAL JOIN cita SET id_ven=? WHERE codi_cit= ? ");
                        ps3.setInt(1, idGenerado);
                        ps3.setString(2, encargo.getCita());
                        int k = ps3.executeUpdate();
                        if (k == 1) {
                            resp = true;
                        }
                    }
                }

            } else {
                try {
                    PreparedStatement ps2 = cn.prepareStatement("SELECT nom_art FROM ticket NATURAL JOIN descripcion NATURAL JOIN cita NATURAL JOIN articulo WHERE codi_cit=? AND cor_usu=?");
                    ps2.setString(1, encargo.getCita());
                    System.out.println("La cita " + encargo.getCita());
                    ps2.setString(2, encargo.getCorreo());
                    System.out.println("El correo " + encargo.getCorreo());
                    System.out.println("El articulo " + encargo.getArticulo());
                    ResultSet rs2 = ps2.executeQuery();
                    boolean productoEncargado = false;
                    while (rs2.next()) {
                        System.out.println("El que pidio" + encargo.getArticulo());
                        System.out.println("lo que hay " + rs2.getString("nom_art"));
                        if (encargo.getArticulo().equals(rs2.getString("nom_art"))) {
                            productoEncargado = true;
                            break;
                        }
                    }
                    if (productoEncargado) {
                        PreparedStatement ps4 = cn.prepareStatement("UPDATE ticket NATURAL JOIN articulo NAUTRAL JOIN descripcion NATURAL JOIN cita SET can_tic=? WHERE nom_art =? AND codi_cit=?");
                        ps4.setInt(1, encargo.getCantidad());
                        ps4.setString(2, encargo.getArticulo());
                        ps4.setString(3, encargo.getCita());
                        ps4.executeUpdate();
                        resp = true;
                    } else {
                        System.out.println("entro aqui");
                        PreparedStatement ps3 = cn.prepareStatement("INSERT INTO ticket (can_tic, id_ven, id_art) VALUES (?,?,(SELECT id_art FROM articulo WHERE nom_art =?))");
                        ps3.setInt(1, encargo.getCantidad());
                        ps3.setInt(2, id);
                        ps3.setString(3, encargo.getArticulo());
                        ps3.executeUpdate();
                        resp = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    resp = false;
                }

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return resp;
    }

    public static ArrayList<Encargo> consultarEncargos(String correo) {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        ArrayList<Encargo> encargos = new ArrayList<Encargo>();
        try {
            PreparedStatement ps = cn.prepareStatement("select codi_cit, nom_art, can_tic from descripcion natural join cita natural join venta natural join ticket NATURAL JOIN articulo where id_ven IS NOT NULL AND cor_usu=? AND fin_cit=?");
            ps.setString(1, correo);
            ps.setInt(2, 0);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Encargo encargo = new Encargo();
                encargo.setCita(rs.getString("codi_cit"));
                encargo.setArticulo(rs.getString("nom_art"));
                encargo.setCantidad(rs.getInt("can_tic"));
                encargos.add(encargo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encargos;
    }

    public boolean editarServicio(DescripcionServicio descripcionServicio) {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        boolean resp = true;
        //INSERT INTO descripcion (com_des, cal_des, id_cit, id_ser, id_ven) VALUES ('f',5.0,(SELECT id_cit FROM cita WHERE codi_cit='20jela@efe05311900'),(SELECT id_ser FROM servicio WHERE nom_ser='Baño pequeño'),1);
        try {
            PreparedStatement ps = cn.prepareStatement("UPDATE descripcion NATURAL JOIN cita NATURAL JOIN servicio SET id_ser= ( SELECT id_ser FROM servicio WHERE nom_ser=? ) WHERE codi_cit=?");

            ps.setString(1, descripcionServicio.getNombreServicio());
            ps.setString(2, descripcionServicio.getCodigoCita());
            ps.executeUpdate();
            resp = true;

        } catch (Exception e) {
            System.out.println("f al insertar");
            System.out.println(e.toString());
            resp = false;
        }
        return resp;
    }

    public boolean borrarEncargo(Encargo encargo) {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        boolean resp = true;
        try {
            PreparedStatement ps = cn.prepareStatement("DELETE ticket FROM ticket NATURAL JOIN descripcion NATURAL JOIN cita NATURAL JOIN articulo WHERE nom_art=? AND codi_cit=? AND cor_usu=?");

            ps.setString(1, encargo.getArticulo());
            ps.setString(2, encargo.getCita());
            ps.setString(3, encargo.getCorreo());
            ps.executeUpdate();
            resp = true;

        } catch (Exception e) {
            System.out.println("f al borrar");
            System.out.println(e.toString());
            resp = false;
        }
        return resp;
    }

    public boolean cancelarCita(Cita cita, DescripcionServicio ds) {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        boolean resp = true;
        try {
            PreparedStatement ps = cn.prepareStatement("DELETE ticket FROM ticket NATURAL JOIN venta NATURAL JOIN descripcion NATURAL JOIN cita WHERE codi_cit=?");
            ps.setString(1, cita.getCodigo());
            ps.executeUpdate();

            PreparedStatement ps5 = cn.prepareStatement("UPDATE descripcion NATURAL JOIN cita SET id_ven=null WHERE codi_cit=?");
            ps5.setString(1, cita.getCodigo());

            PreparedStatement ps1 = cn.prepareStatement("DELETE venta FROM venta NATURAL JOIN descripcion NATURAL JOIN cita WHERE codi_cit=?");
            ps1.setString(1, cita.getCodigo());
            ps1.executeUpdate();

            PreparedStatement ps2 = cn.prepareStatement("DELETE descripcion FROM descripcion NATURAL JOIN cita WHERE codi_cit=?");
            ps2.setString(1, cita.getCodigo());
            ps2.executeUpdate();
            ps2.executeUpdate();

            PreparedStatement ps3 = cn.prepareStatement("DELETE cita FROM cita WHERE codi_cit=? AND cor_usu=?");
            ps3.setString(1, cita.getCodigo());
            ps3.setString(2, cita.getCliente());
            ps3.executeUpdate();

            resp = true;
        } catch (Exception e) {
            System.out.println("f al insertar");
            System.out.println(e.toString());
            resp = false;
        }
        return resp;
    }

    public Usuario consultarDatos(String correo) {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        Usuario usuario = new Usuario();
        try {
            PreparedStatement ps = cn.prepareStatement("SELECT nom_pers, ape_pers, tel_pers, dir_pers FROM usuario NATURAL JOIN persona WHERE cor_usu=?");
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                usuario.setNombre(rs.getString("nom_pers"));
                usuario.setApellido(rs.getString("ape_pers"));
                usuario.setDireccion(rs.getString("dir_pers"));
                usuario.setTelefono(rs.getString("tel_pers"));

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return usuario;
    }

    public boolean editarDatosUsuario(Usuario usuario, String correo) {
        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        boolean resp= true;
        try {
            PreparedStatement ps = cn.prepareStatement("UPDATE persona NATURAL JOIN usuario SET nom_pers=?, ape_pers=?, tel_pers=?, dir_pers=? WHERE cor_usu=?");
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getDireccion());
            ps.setString(5, correo);
            ps.executeUpdate();
            resp = true;
        } catch (Exception e) {
            e.printStackTrace();
            resp=false;
        }
        return resp;
    }
}
