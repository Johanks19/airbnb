package com.airbnb;

public class Propiedad {
    private int idPropiedad;
    private int idAnfitrion;  // FK hacia usuario anfitrión
    private String titulo;
    private String descripcion;
    private String direccion;
    private String ciudad;
    private String pais;
    private int capacidad;
    private double precioNoche;
    private String nombreAnfitrion;

    // Constructor vacío
    public Propiedad() {}

    // Constructor con parámetros
    public Propiedad(int idPropiedad, int idAnfitrion, String titulo, String descripcion,
                     String direccion, String ciudad, String pais, int capacidad, double precioNoche) {
        this.idPropiedad = idPropiedad;
        this.idAnfitrion = idAnfitrion;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.pais = pais;
        this.capacidad = capacidad;
        this.precioNoche = precioNoche;
    }

    // Getters y setters
    public int getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(int idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    public int getIdAnfitrion() {
        return idAnfitrion;
    }

    public void setIdAnfitrion(int idAnfitrion) {
        this.idAnfitrion = idAnfitrion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }
    public String getNombreAnfitrion() { return nombreAnfitrion; }
    public void setNombreAnfitrion(String nombreAnfitrion) { this.nombreAnfitrion = nombreAnfitrion; }
    @Override
    public String toString() {
        return idPropiedad + " | " + titulo + " | " + descripcion + " | " +
                direccion + ", " + ciudad + ", " + pais + " | Capacidad: " + capacidad +
                " | Precio/Noche: $" + precioNoche +
                " | Anfitrión: " + (nombreAnfitrion != null ? nombreAnfitrion : "ID " + idAnfitrion);
    }


}

