-- Esquema de la base de datos del proyecto Airbnb
CREATE DATABASE IF NOT EXISTS airbnb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE airbnb;

CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario         INT AUTO_INCREMENT PRIMARY KEY,
    nombre             VARCHAR(100) NOT NULL,
    documentoIdentidad VARCHAR(20)  NOT NULL UNIQUE,
    telefono           VARCHAR(20),
    correo             VARCHAR(100) NOT NULL UNIQUE,
    contrasenia        VARCHAR(255) NOT NULL,
    tipoUsuario        ENUM('anfitrion', 'huesped') NOT NULL
);

CREATE TABLE IF NOT EXISTS propiedades (
    id_propiedad INT AUTO_INCREMENT PRIMARY KEY,
    id_anfitrion INT NOT NULL,
    titulo       VARCHAR(150) NOT NULL,
    descripcion  TEXT,
    direccion    VARCHAR(200),
    ciudad       VARCHAR(100),
    pais         VARCHAR(100),
    capacidad    INT NOT NULL CHECK (capacidad > 0),
    precioNoche  DECIMAL(10, 2) NOT NULL CHECK (precioNoche > 0),
    FOREIGN KEY (id_anfitrion) REFERENCES usuarios (id_usuario) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reservas (
    id_reserva   INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario   INT NOT NULL,
    id_propiedad INT NOT NULL,
    fechaInicio  DATE NOT NULL,
    fechaFin     DATE NOT NULL,
    totalPago    DECIMAL(10, 2) NOT NULL DEFAULT 0,
    estado       ENUM('pendiente', 'confirmada', 'cancelada') NOT NULL DEFAULT 'pendiente',
    FOREIGN KEY (id_usuario)   REFERENCES usuarios (id_usuario)     ON DELETE CASCADE,
    FOREIGN KEY (id_propiedad) REFERENCES propiedades (id_propiedad) ON DELETE CASCADE,
    CHECK (fechaFin > fechaInicio)
);
