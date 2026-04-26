package com.airbnb;

public class Reserva {
    private int id_reserva;
    private int idHuesped;     // ✅ CORRECCIÓN
    private int idPropiedad;   // ✅ CORRECCIÓN
    private String fechaInicio;
    private String fechaFin;
    private double totalPago;
    private String estado;

    // ✅ Constructor vacío
    public Reserva() {}

    // ✅ Constructor con todos los campos (para leer desde BD)
    public Reserva(int id_reserva, int idHuesped, int idPropiedad, String fechaInicio, String fechaFin, double totalPago, String estado) {
        this.id_reserva = id_reserva;
        this.idHuesped = idHuesped;
        this.idPropiedad = idPropiedad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.totalPago = totalPago;
        this.estado = estado;
    }

    // ✅ Constructor para nuevas reservas (el sistema calcula totalPago y pone estado por defecto)
    public Reserva(int idHuesped, int idPropiedad, String fechaInicio, String fechaFin) {
        this.idHuesped = idHuesped;
        this.idPropiedad = idPropiedad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.totalPago = 0.0;
        this.estado = "pendiente";
    }

    // ✅ Getters y Setters
    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public int getIdHuesped() { // ✅ CORRECCIÓN
        return idHuesped;
    }

    public void setIdHuesped(int idHuesped) { // ✅ CORRECCIÓN
        this.idHuesped = idHuesped;
    }

    public int getIdPropiedad() { // ✅ CORRECCIÓN
        return idPropiedad;
    }

    public void setIdPropiedad(int idPropiedad) { // ✅ CORRECCIÓN
        this.idPropiedad = idPropiedad;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(double totalPago) {
        this.totalPago = totalPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id_reserva=" + id_reserva +
                ", idHuesped=" + idHuesped +
                ", idPropiedad=" + idPropiedad +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", totalPago=" + totalPago +
                ", estado='" + estado + '\'' +
                '}';
    }
}