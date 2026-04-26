package com.airbnb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections; // Importa Collections para Map

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaDAO reservaDAO;

    @Autowired
    public ReservaController(ReservaDAO reservaDAO) {
        this.reservaDAO = reservaDAO;
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> getAllReservas() {
        List<Reserva> reservas = reservaDAO.obtenerReservas();
        return ResponseEntity.ok(reservas);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createReserva(@RequestBody Reserva reserva) {
        if (reservaDAO.insertarReserva(reserva)) {
            // ✅ CORRECCIÓN: Devolver un objeto JSON con un mensaje de éxito
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Collections.singletonMap("message", "Reserva creada exitosamente."));
        } else {
            // ✅ CORRECCIÓN: Devolver un objeto JSON con un mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "Error al crear la reserva."));
        }
    }
}
