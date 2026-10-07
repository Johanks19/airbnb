# 🏡 Airbnb Clone — API REST con Spring Boot y MySQL

Plataforma de alquiler de alojamientos inspirada en Airbnb. Los **anfitriones** publican propiedades y los **huéspedes** las reservan. El sistema calcula el costo total según las noches y evita reservas que se crucen en las mismas fechas.

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7-6DB33F?logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-build-C71A36?logo=apachemaven)

## ✨ Funcionalidades

- **Usuarios:** registro e inicio de sesión con roles (`anfitrion` / `huesped`).
- **Propiedades:** publicación y listado con el nombre del anfitrión (consulta con `JOIN`).
- **Reservas:** cálculo automático del total (`noches × precioNoche`) y validación de disponibilidad para evitar reservas cruzadas.
- **Frontend** en HTML, CSS y JavaScript que consume la API con `fetch`.
- **Consultas parametrizadas** (`PreparedStatement`) para proteger contra inyección SQL.

## 🏗️ Arquitectura

```
Frontend (HTML/JS)  ──HTTP/JSON──▶  Controllers (REST)  ──▶  DAO (JDBC)  ──▶  MySQL
                                    UsuarioController        UsuarioDAO
                                    PropiedadController      PropiedadDAO
                                    ReservaController        ReservaDAO
```

El proyecto usa el patrón **DAO (Data Access Object)**: los controladores reciben las peticiones HTTP y delegan el acceso a datos a clases especializadas.

## 🗄️ Modelo de datos

```
usuarios (1) ──< propiedades (1) ──< reservas >── (1) usuarios
```

El esquema completo está en [`AirbnbApp/database/schema.sql`](AirbnbApp/database/schema.sql).

## 🔌 Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/api/usuarios` | Registrar usuario |
| `POST` | `/api/usuarios/login` | Iniciar sesión |
| `GET`  | `/api/usuarios` | Listar usuarios |
| `GET`  | `/api/propiedades` | Listar propiedades |
| `POST` | `/api/propiedades` | Publicar propiedad |
| `GET`  | `/api/reservas` | Listar reservas |
| `POST` | `/api/reservas` | Crear reserva |

Ejemplo de reserva:

```json
POST /api/reservas
{
  "idHuesped": 2,
  "idPropiedad": 1,
  "fechaInicio": "2026-12-20",
  "fechaFin": "2026-12-24"
}
```

## 🚀 Cómo ejecutarlo

**Requisitos:** Java 17, Maven y MySQL 8.

1. Crea la base de datos:
   ```bash
   mysql -u root -p < AirbnbApp/database/schema.sql
   ```
2. Define las credenciales como variables de entorno (no se guardan en el código):
   ```powershell
   $env:DB_USER="root"
   $env:DB_PASSWORD="tu_contraseña"
   ```
3. Ejecuta la aplicación:
   ```bash
   cd AirbnbApp
   mvn spring-boot:run
   ```
4. Abre <http://localhost:8080>.

## 🛣️ Próximas mejoras

- [ ] Encriptar contraseñas con BCrypt y autenticación con JWT
- [ ] Migrar de JDBC a Spring Data JPA
- [ ] Pruebas unitarias con JUnit y Mockito
- [ ] Documentación interactiva con Swagger / OpenAPI
- [ ] Despliegue con Docker

## 👤 Autor

**Johan Sneyder Blanco** — Estudiante de Ingeniería de Sistemas
[GitHub](https://github.com/Johanks19)
