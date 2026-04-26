package com.airbnb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    @Autowired
    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    // 1. Método para OBTENER todos los usuarios
    // URL: GET http://localhost:8080/api/usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // 2. Método para CREAR un nuevo usuario
    // URL: POST http://localhost:8080/api/usuarios
    @PostMapping
    public ResponseEntity<Object> createUsuario(@RequestBody Usuario usuario) {
        if (usuarioDAO.insertarUsuario(usuario)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Usuario creado exitosamente."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Error al crear el usuario."));
        }
    }

    // ✅ NUEVO MÉTODO PARA EL INICIO DE SESIÓN
    // URL: POST http://localhost:8080/api/usuarios/login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        String correo = loginData.get("correo");
        String contrasenia = loginData.get("contrasenia");

        // Lógica para validar el usuario y la contraseña
        Usuario usuario = usuarioDAO.validarUsuario(correo, contrasenia);

        if (usuario != null) {
            // Si el login es exitoso, devuelve el ID y el tipo de usuario
            return ResponseEntity.ok(
                    Map.of("message", "Login exitoso",
                            "idUsuario", usuario.getId_usuario(),
                            "tipoUsuario", usuario.getTipoUsuario())
            );
        } else {
            // Si el login falla, devuelve un error
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Credenciales incorrectas"));
        }
    }
}