package com.airbnb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propiedades")
public class PropiedadController {

    private final PropiedadDAO propiedadDAO;

    @Autowired
    public PropiedadController(PropiedadDAO propiedadDAO) {
        this.propiedadDAO = propiedadDAO;
    }

    @GetMapping
    public ResponseEntity<List<Propiedad>> getAllPropiedades() {
        List<Propiedad> propiedades = propiedadDAO.listarPropiedades();
        return ResponseEntity.ok(propiedades);
    }

    @PostMapping
    public ResponseEntity<String> createPropiedad(@RequestBody Propiedad propiedad) {
        if (propiedadDAO.insertarPropiedad(propiedad)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Propiedad creada exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear la propiedad.");
        }
    }
}
