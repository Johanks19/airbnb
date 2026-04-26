package com.airbnb;

public class Usuario {
    private int id_usuario;
    private String nombre;
    private String documentoIdentidad;
    private String telefono;
    private String correo;
    private String contrasenia;
    private String tipoUsuario;

    // ✅ Constructor vacío
    public Usuario() {}

    // ✅ Constructor con todos los campos
    public Usuario(int id_usuario, String nombre, String documentoIdentidad, String telefono, String correo, String contrasenia, String tipoUsuario) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.documentoIdentidad = documentoIdentidad;
        this.telefono = telefono;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.tipoUsuario = tipoUsuario;
    }

    // ✅ Constructor sin ID (para insertar)
    public Usuario(String nombre, String documentoIdentidad, String telefono, String correo, String contrasenia, String tipoUsuario) {
        this.nombre = nombre;
        this.documentoIdentidad = documentoIdentidad;
        this.telefono = telefono;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters y setters
    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDocumentoIdentidad() { return documentoIdentidad; }
    public void setDocumentoIdentidad(String documentoIdentidad) { this.documentoIdentidad = documentoIdentidad; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasenia() { return contrasenia; }
    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }
    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    @Override
    public String toString() {
        return id_usuario + " | " + nombre + " | " + documentoIdentidad + " | " + telefono + " | " + correo + " | " + contrasenia + " | " + tipoUsuario;
    }
}

