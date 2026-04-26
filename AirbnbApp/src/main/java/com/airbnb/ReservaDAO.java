package com.airbnb;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReservaDAO {

    // ✅ NUEVO MÉTODO: Cambia el estado de una reserva a 'confirmada'
    public boolean confirmarReserva(int idReserva) {
        String sql = "UPDATE reservas SET estado = 'confirmada' WHERE id_reserva = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idReserva);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al confirmar la reserva: " + e.getMessage());
            return false;
        }
    }

    public boolean verificarDisponibilidad(int idPropiedad, LocalDate fechaInicio, LocalDate fechaFin) {
        String sql = "SELECT COUNT(*) FROM reservas " +
                "WHERE id_propiedad = ? " +
                "AND estado = 'confirmada' " +
                "AND ( " +
                "    (fechaInicio <= ? AND fechaFin >= ?) OR " +
                "    (fechaInicio BETWEEN ? AND ?) OR " +
                "    (fechaFin BETWEEN ? AND ?) " +
                ")";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPropiedad);
            ps.setDate(2, Date.valueOf(fechaFin));
            ps.setDate(3, Date.valueOf(fechaInicio));
            ps.setDate(4, Date.valueOf(fechaInicio));
            ps.setDate(5, Date.valueOf(fechaFin));
            ps.setDate(6, Date.valueOf(fechaInicio));
            ps.setDate(7, Date.valueOf(fechaFin));
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al verificar disponibilidad: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean insertarReserva(Reserva r) {
        try {
            LocalDate inicio = LocalDate.parse(r.getFechaInicio());
            LocalDate fin = LocalDate.parse(r.getFechaFin());

            if (inicio.isAfter(fin) || inicio.equals(fin)) {
                System.out.println("❌ La fecha de fin debe ser después de la fecha de inicio.");
                return false;
            }

            if (!verificarDisponibilidad(r.getIdPropiedad(), inicio, fin)) {
                System.out.println("❌ La propiedad no está disponible en las fechas seleccionadas.");
                return false;
            }

            String sqlProp = "SELECT precioNoche FROM propiedades WHERE id_propiedad = ?";
            String sqlInsert = "INSERT INTO reservas (id_usuario, id_propiedad, fechaInicio, fechaFin, totalPago, estado) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement stmtProp = conn.prepareStatement(sqlProp)) {

                // ✅ CORRECCIÓN: Usar getIdPropiedad() en lugar de getId_propiedad()
                stmtProp.setInt(1, r.getIdPropiedad());

                try (ResultSet rs = stmtProp.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("⚠ No se encontró la propiedad con id " + r.getIdPropiedad());
                        return false;
                    }
                    double precioNoche = rs.getDouble("precioNoche");
                    long noches = ChronoUnit.DAYS.between(inicio, fin);
                    double total = noches * precioNoche;

                    try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                        stmtInsert.setInt(1, r.getIdHuesped());
                        stmtInsert.setInt(2, r.getIdPropiedad()); // ✅ CORRECCIÓN
                        stmtInsert.setDate(3, Date.valueOf(inicio));
                        stmtInsert.setDate(4, Date.valueOf(fin));
                        stmtInsert.setDouble(5, total);
                        stmtInsert.setString(6, "pendiente");
                        return stmtInsert.executeUpdate() > 0;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error al insertar reserva: " + e.getMessage());
            return false;
        }
    }

    public List<Reserva> obtenerReservas() {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM reservas";
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Reserva r = new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_propiedad"),
                        rs.getString("fechaInicio"),
                        rs.getString("fechaFin"),
                        rs.getDouble("totalPago"),
                        rs.getString("estado")
                );
                lista.add(r);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al obtener reservas: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarReserva(Reserva r) {
        String sql = "UPDATE reservas SET id_usuario=?, id_propiedad=?, fechaInicio=?, fechaFin=?, totalPago=?, estado=? WHERE id_reserva=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, r.getIdHuesped());
            stmt.setInt(2, r.getIdPropiedad());
            stmt.setString(3, r.getFechaInicio());
            stmt.setString(4, r.getFechaFin());
            stmt.setDouble(5, r.getTotalPago());
            stmt.setString(6, r.getEstado());
            stmt.setInt(7, r.getId_reserva());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("❌ Error al actualizar reserva: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarReserva(int id) {
        String sql = "DELETE FROM reservas WHERE id_reserva=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar reserva: " + e.getMessage());
            return false;
        }
    }
}

