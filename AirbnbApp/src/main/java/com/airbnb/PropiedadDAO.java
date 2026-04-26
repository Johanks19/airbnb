package com.airbnb;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PropiedadDAO {

    // Insertar propiedad (solo anfitrión)
    public boolean insertarPropiedad(Propiedad p) {
        if (p == null) {
            System.out.println("Error: La propiedad no puede ser nula.");
            return false;
        }
        if (p.getIdAnfitrion() <= 0) {
            System.out.println("Error: ID del anfitrión inválido.");
            return false;
        }
        if (p.getTitulo() == null || p.getTitulo().trim().isEmpty()) {
            System.out.println("Error: El título no puede estar vacío.");
            return false;
        }
        if (p.getCapacidad() <= 0) {
            System.out.println("Error: La capacidad debe ser mayor a 0.");
            return false;
        }
        if (p.getPrecioNoche() <= 0) {
            System.out.println("Error: El precio por noche debe ser mayor a 0.");
            return false;
        }

        String sql = "INSERT INTO propiedades (id_anfitrion, titulo, descripcion, direccion, ciudad, pais, capacidad, precioNoche) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getIdAnfitrion());
            ps.setString(2, p.getTitulo());
            ps.setString(3, p.getDescripcion());
            ps.setString(4, p.getDireccion());
            ps.setString(5, p.getCiudad());
            ps.setString(6, p.getPais());
            ps.setInt(7, p.getCapacidad());
            ps.setDouble(8, p.getPrecioNoche());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar propiedad: " + e.getMessage());
        }
        return false;
    }

    // Listar propiedades (con nombre del anfitrión)
    public List<Propiedad> listarPropiedades() {
        List<Propiedad> lista = new ArrayList<>();
        String sql = "SELECT p.*, u.nombre AS nombreAnfitrion " +
                "FROM propiedades p " +
                "INNER JOIN usuarios u ON p.id_anfitrion = u.id_usuario";

        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Propiedad p = new Propiedad(
                        rs.getInt("id_propiedad"),
                        rs.getInt("id_anfitrion"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getString("direccion"),
                        rs.getString("ciudad"),
                        rs.getString("pais"),
                        rs.getInt("capacidad"),
                        rs.getDouble("precioNoche")
                );

                // ✅ Asignar el nombre del anfitrión
                p.setNombreAnfitrion(rs.getString("nombreAnfitrion"));

                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar propiedades: " + e.getMessage());
        }
        return lista;
    }

    // Actualizar propiedad
    public boolean actualizarPropiedad(Propiedad p) {
        if (p == null) {
            System.out.println("Error: La propiedad no puede ser nula.");
            return false;
        }
        if (p.getIdPropiedad() <= 0) {
            System.out.println("Error: ID de propiedad inválido.");
            return false;
        }
        if (p.getTitulo() == null || p.getTitulo().trim().isEmpty()) {
            System.out.println("Error: El título no puede estar vacío.");
            return false;
        }
        if (p.getCapacidad() <= 0) {
            System.out.println("Error: La capacidad debe ser mayor a 0.");
            return false;
        }
        if (p.getPrecioNoche() <= 0) {
            System.out.println("Error: El precio por noche debe ser mayor a 0.");
            return false;
        }

        String sql = "UPDATE propiedades SET titulo=?, descripcion=?, direccion=?, ciudad=?, pais=?, capacidad=?, precioNoche=? WHERE id_propiedad=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getDescripcion());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getCiudad());
            ps.setString(5, p.getPais());
            ps.setInt(6, p.getCapacidad());
            ps.setDouble(7, p.getPrecioNoche());
            ps.setInt(8, p.getIdPropiedad());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar propiedad: " + e.getMessage());
        }
        return false;
    }

    // Eliminar propiedad
    public boolean eliminarPropiedad(int idPropiedad) {
        if (idPropiedad <= 0) {
            System.out.println("Error: ID de propiedad inválido.");
            return false;
        }

        String sql = "DELETE FROM propiedades WHERE id_propiedad=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPropiedad);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar propiedad: " + e.getMessage());
        }
        return false;
    }
    // Obtener propiedad por ID
    public Propiedad obtenerPropiedadPorId(int idPropiedad) {
        Propiedad propiedad = null;
        String sql = "SELECT * FROM propiedades WHERE id_propiedad = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPropiedad);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    propiedad = new Propiedad(
                            rs.getInt("id_propiedad"),
                            rs.getInt("id_anfitrion"),
                            rs.getString("titulo"),
                            rs.getString("descripcion"),
                            rs.getString("direccion"),
                            rs.getString("ciudad"),
                            rs.getString("pais"),
                            rs.getInt("capacidad"),
                            rs.getDouble("precioNoche")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener propiedad por ID: " + e.getMessage());
        }
        return propiedad;
    }
}


