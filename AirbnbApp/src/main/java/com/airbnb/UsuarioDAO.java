package com.airbnb;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioDAO {

    // ✅ NUEVO MÉTODO: Autenticar un usuario por correo y contraseña
    public Usuario validarUsuario(String correo, String contrasenia) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contrasenia = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, contrasenia);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Si se encuentra un usuario, se crea el objeto y se devuelve
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("documentoIdentidad"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("contrasenia"),
                        rs.getString("tipoUsuario")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al validar usuario: " + e.getMessage());
        }
        return null; // Devuelve null si no se encuentra o hay un error
    }

    // (El resto de métodos: insertarUsuario, obtenerUsuarios, actualizarUsuario, eliminarUsuario...)
    // ... Tu código anterior sigue aquí ...
    public boolean insertarUsuario(Usuario u) {
        if (!validarUsuario(u, true)) return false;
        String sql = "INSERT INTO usuarios (nombre, documentoIdentidad, telefono, correo, contrasenia, tipoUsuario) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getDocumentoIdentidad());
            stmt.setString(3, u.getTelefono());
            stmt.setString(4, u.getCorreo());
            stmt.setString(5, u.getContrasenia());
            stmt.setString(6, u.getTipoUsuario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }
    // ...
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("documentoIdentidad"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("contrasenia"),
                        rs.getString("tipoUsuario")
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener usuarios: " + e.getMessage());
        }
        return lista;
    }
    // ...
    public boolean actualizarUsuario(Usuario u) {
        if (!validarUsuario(u, false)) return false;
        String sql = "UPDATE usuarios SET nombre=?, documentoIdentidad=?, telefono=?, correo=?, contrasenia=?, tipoUsuario=? WHERE id_usuario=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getDocumentoIdentidad());
            stmt.setString(3, u.getTelefono());
            stmt.setString(4, u.getCorreo());
            stmt.setString(5, u.getContrasenia());
            stmt.setString(6, u.getTipoUsuario());
            stmt.setInt(7, u.getId_usuario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }
    // ...
    public boolean eliminarUsuario(int id) {
        if (id <= 0) {
            System.out.println("Error: ID de usuario inválido.");
            return false;
        }
        String sql = "DELETE FROM usuarios WHERE id_usuario=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
    // ...
    private boolean validarUsuario(Usuario u, boolean isInsert) {
        if (u == null) {
            System.out.println("Error: El usuario no puede ser nulo.");
            return false;
        }
        if (!isInsert && u.getId_usuario() <= 0) {
            System.out.println("Error: ID de usuario inválido.");
            return false;
        }
        if (u.getNombre() == null || u.getNombre().trim().isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacío.");
            return false;
        }
        if (u.getDocumentoIdentidad() == null || u.getDocumentoIdentidad().trim().isEmpty()) {
            System.out.println("Error: El documento de identidad no puede estar vacío.");
            return false;
        }
        if (u.getCorreo() == null || !u.getCorreo().contains("@")) {
            System.out.println("Error: El correo electrónico no es válido.");
            return false;
        }
        if (u.getContrasenia() == null || u.getContrasenia().length() < 4) {
            System.out.println("Error: La contraseña debe tener al menos 4 caracteres.");
            return false;
        }
        if (u.getTipoUsuario() == null ||
                !(u.getTipoUsuario().equalsIgnoreCase("anfitrion") || u.getTipoUsuario().equalsIgnoreCase("huesped"))) {
            System.out.println("Error: El tipo de usuario debe ser 'anfitrion' o 'huesped'.");
            return false;
        }
        return true;
    }
}