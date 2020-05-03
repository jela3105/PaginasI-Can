package Clases;

/**
 *
 * @author Josue Lopez
 * @version 28/04/2020
 *
 */
public class Cita {

    //correo del cliente
    private String cliente;
    //fecha que se programo la cita
    private String fecha;
    //hora en que se programo la cita
    private String hora;
    //true: completada; false:pendiente
    private boolean estado;
    //codigo identificador de la cita
    private String codigo;
    //fecha en que se finalizo la cita
    private String fincita;
    //mascota que va a tener la cita
    private String mascota;

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFincita() {
        return fincita;
    }

    public void setFincita(String fincita) {
        this.fincita = fincita;
    }

    public String getMascota() {
        return mascota;
    }

    public void setMascota(String mascota) {
        this.mascota = mascota;
    }

    /**
     * Este metodo genera el codido de la cita que se esta programando
     *
     * @return: Codigo de identificacion de la cita
     */
    public String generarCodigo() {
        return fecha.substring(2, 4)
                + cliente.substring(0, 5)
                + mascota.substring(0, 3)
                + fecha.substring(5, 7)
                + fecha.substring(8, 10)
                + hora.substring(0, 2) + hora.substring(3, 5);
    }
}
