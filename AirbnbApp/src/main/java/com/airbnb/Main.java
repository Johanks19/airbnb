package com.airbnb;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PropiedadDAO propiedadDAO = new PropiedadDAO();
        ReservaDAO reservaDAO = new ReservaDAO();
        int opcion;

        do {
            System.out.println("\n===== MENÚ PRINCIPAL AIRBNB =====");
            System.out.println("1. CRUD Usuarios");
            System.out.println("2. CRUD Propiedades");
            System.out.println("3. CRUD Reservas");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    menuUsuarios(sc, usuarioDAO);
                    break;
                case 2:
                    menuPropiedades(sc, propiedadDAO);
                    break;
                case 3:
                    menuReservas(sc, reservaDAO);
                    break;
                case 0:
                    System.out.println("👋 Saliendo del sistema...");
                    break;
                default:
                    System.out.println("⚠ Opción inválida");
            }
        } while (opcion != 0);

        sc.close();
    }

    // =================== SUBMENÚ USUARIOS ===================
    private static void menuUsuarios(Scanner sc, UsuarioDAO dao) {
        int opcion;
        do {
            System.out.println("\n===== MENÚ USUARIOS =====");
            System.out.println("1. Insertar usuario");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Actualizar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Iniciar sesión"); // 🔹 Nueva opción
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Documento: ");
                    String documento = sc.nextLine();
                    System.out.print("Teléfono: ");
                    String telefono = sc.nextLine();
                    System.out.print("Correo: ");
                    String correo = sc.nextLine();
                    System.out.print("Contraseña: ");
                    String contrasenia = sc.nextLine();
                    System.out.print("Tipo de usuario (anfitrion/huesped): ");
                    String tipoUsuario = sc.nextLine();

                    Usuario u = new Usuario(nombre, documento, telefono, correo, contrasenia, tipoUsuario);
                    if (dao.insertarUsuario(u)) {
                        System.out.println("✅ Usuario insertado correctamente");
                    } else {
                        System.out.println("❌ Error al insertar usuario");
                    }
                    break;

                case 2:
                    List<Usuario> usuarios = dao.obtenerUsuarios();
                    if (usuarios.isEmpty()) {
                        System.out.println("⚠ No hay usuarios registrados.");
                    } else {
                        System.out.println("===== LISTA DE USUARIOS =====");
                        for (Usuario us : usuarios) {
                            System.out.println(us);
                        }
                    }
                    break;

                case 3:
                    System.out.print("ID del usuario a actualizar: ");
                    int idUpdate = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nuevo nombre: ");
                    String nombreUp = sc.nextLine();
                    System.out.print("Nuevo documento: ");
                    String documentoUp = sc.nextLine();
                    System.out.print("Nuevo teléfono: ");
                    String telefonoUp = sc.nextLine();
                    System.out.print("Nuevo correo: ");
                    String correoUp = sc.nextLine();
                    System.out.print("Nueva contraseña: ");
                    String contraseniaUp = sc.nextLine();
                    System.out.print("Nuevo tipo usuario: ");
                    String tipoUsuarioUp = sc.nextLine();

                    Usuario uUpdate = new Usuario(idUpdate, nombreUp, documentoUp, telefonoUp, correoUp, contraseniaUp, tipoUsuarioUp);
                    if (dao.actualizarUsuario(uUpdate)) {
                        System.out.println("✅ Usuario actualizado correctamente");
                    } else {
                        System.out.println("❌ Error al actualizar usuario");
                    }
                    break;

                case 4:
                    System.out.print("ID del usuario a eliminar: ");
                    int idDelete = sc.nextInt();
                    sc.nextLine();
                    if (dao.eliminarUsuario(idDelete)) {
                        System.out.println("✅ Usuario eliminado correctamente");
                    } else {
                        System.out.println("❌ Error al eliminar usuario");
                    }
                    break;

                case 5:
                    System.out.print("Correo: ");
                    String loginCorreo = sc.nextLine();
                    System.out.print("Contraseña: ");
                    String loginContrasenia = sc.nextLine();

                    Usuario usuarioLogueado = dao.validarUsuario(loginCorreo, loginContrasenia);
                    if (usuarioLogueado != null) {
                        System.out.println("🎉 ¡Inicio de sesión exitoso! Bienvenido, " + usuarioLogueado.getNombre() + ".");
                    } else {
                        System.out.println("❌ Correo o contraseña incorrectos.");
                    }
                    break;

                case 0:
                    break;
                default:
                    System.out.println("⚠ Opción inválida");
            }
        } while (opcion != 0);
    }
    // ... El resto del código de la clase Main es el mismo ...
    private static void menuPropiedades(Scanner sc, PropiedadDAO dao) {
        int opcion;
        do {
            System.out.println("\n===== MENÚ PROPIEDADES =====");
            System.out.println("1. Insertar propiedad");
            System.out.println("2. Listar propiedades");
            System.out.println("3. Actualizar propiedad");
            System.out.println("4. Eliminar propiedad");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("ID anfitrión: ");
                    int idAnfitrion = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();
                    System.out.print("Descripción: ");
                    String descripcion = sc.nextLine();
                    System.out.print("Dirección: ");
                    String direccion = sc.nextLine();
                    System.out.print("Ciudad: ");
                    String ciudad = sc.nextLine();
                    System.out.print("País: ");
                    String pais = sc.nextLine();
                    System.out.print("Capacidad: ");
                    int capacidad = sc.nextInt();
                    System.out.print("Precio por noche: ");
                    double precio = sc.nextDouble();
                    sc.nextLine();

                    Propiedad p = new Propiedad(0, idAnfitrion, titulo, descripcion, direccion, ciudad, pais, capacidad, precio);
                    if (dao.insertarPropiedad(p)) {
                        System.out.println("✅ Propiedad insertada correctamente");
                    } else {
                        System.out.println("❌ Error al insertar propiedad");
                    }
                    break;

                case 2:
                    List<Propiedad> propiedades = dao.listarPropiedades();
                    if (propiedades.isEmpty()) {
                        System.out.println("⚠ No hay propiedades registradas.");
                    } else {
                        System.out.println("===== LISTA DE PROPIEDADES =====");
                        for (Propiedad prop : propiedades) {
                            System.out.println(prop);
                        }
                    }
                    break;

                case 3:
                    System.out.print("ID de la propiedad a actualizar: ");
                    int idUpdate = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nuevo título: ");
                    String tituloUp = sc.nextLine();
                    System.out.print("Nueva descripción: ");
                    String descripcionUp = sc.nextLine();
                    System.out.print("Nueva dirección: ");
                    String direccionUp = sc.nextLine();
                    System.out.print("Nueva ciudad: ");
                    String ciudadUp = sc.nextLine();
                    System.out.print("Nuevo país: ");
                    String paisUp = sc.nextLine();
                    System.out.print("Nueva capacidad: ");
                    int capacidadUp = sc.nextInt();
                    System.out.print("Nuevo precio por noche: ");
                    double precioUp = sc.nextDouble();
                    sc.nextLine();

                    Propiedad pUpdate = new Propiedad(idUpdate, 0, tituloUp, descripcionUp, direccionUp, ciudadUp, paisUp, capacidadUp, precioUp);
                    if (dao.actualizarPropiedad(pUpdate)) {
                        System.out.println("✅ Propiedad actualizada correctamente");
                    } else {
                        System.out.println("❌ Error al actualizar propiedad");
                    }
                    break;

                case 4:
                    System.out.print("ID de la propiedad a eliminar: ");
                    int idDelete = sc.nextInt();
                    sc.nextLine();
                    if (dao.eliminarPropiedad(idDelete)) {
                        System.out.println("✅ Propiedad eliminada correctamente");
                    } else {
                        System.out.println("❌ Error al eliminar propiedad");
                    }
                    break;

                case 0:
                    break;
                default:
                    System.out.println("⚠ Opción inválida");
            }
        } while (opcion != 0);
    }
    // ...
    private static void menuReservas(Scanner sc, ReservaDAO dao) {
        int opcion;
        do {
            System.out.println("\n===== MENÚ RESERVAS =====");
            System.out.println("1. Insertar reserva");
            System.out.println("2. Listar reservas");
            System.out.println("3. Actualizar reserva");
            System.out.println("4. Eliminar reserva");
            System.out.println("5. Confirmar reserva (Pagar)"); // 🔹 Nueva opción
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("ID usuario: ");
                    int idUsuario = sc.nextInt();
                    System.out.print("ID propiedad: ");
                    int idPropiedad = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Fecha inicio (YYYY-MM-DD): ");
                    String fechaInicio = sc.nextLine();
                    System.out.print("Fecha fin (YYYY-MM-DD): ");
                    String fechaFin = sc.nextLine();
                    Reserva r = new Reserva(idUsuario, idPropiedad, fechaInicio, fechaFin);
                    if (dao.insertarReserva(r)) {
                        System.out.println("✅ Reserva creada correctamente");
                    } else {
                        System.out.println("❌ Error al crear la reserva");
                    }
                    break;
                case 2:
                    List<Reserva> reservas = dao.obtenerReservas();
                    if (reservas.isEmpty()) {
                        System.out.println("⚠ No hay reservas registradas.");
                    } else {
                        System.out.println("===== LISTA DE RESERVAS =====");
                        for (Reserva res : reservas) {
                            System.out.println(res);
                        }
                    }
                    break;
                case 3:
                    System.out.print("ID de la reserva a actualizar: ");
                    int idUpdate = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nuevo ID de usuario: ");
                    int idUsuarioUp = sc.nextInt();
                    System.out.print("Nuevo ID de propiedad: ");
                    int idPropiedadUp = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nueva fecha de inicio (YYYY-MM-DD): ");
                    String fechaInicioUp = sc.nextLine();
                    System.out.print("Nueva fecha de fin (YYYY-MM-DD): ");
                    String fechaFinUp = sc.nextLine();
                    System.out.print("Nuevo total de pago: ");
                    double totalUp = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Nuevo estado: ");
                    String estadoUp = sc.nextLine();
                    Reserva rUpdate = new Reserva(idUpdate, idUsuarioUp, idPropiedadUp, fechaInicioUp, fechaFinUp, totalUp, estadoUp);
                    if (dao.actualizarReserva(rUpdate)) {
                        System.out.println("✅ Reserva actualizada correctamente");
                    } else {
                        System.out.println("❌ Error al actualizar reserva");
                    }
                    break;
                case 4:
                    System.out.print("ID de la reserva a eliminar: ");
                    int idDelete = sc.nextInt();
                    sc.nextLine();
                    if (dao.eliminarReserva(idDelete)) {
                        System.out.println("✅ Reserva eliminada correctamente");
                    } else {
                        System.out.println("❌ Error al eliminar reserva");
                    }
                    break;
                case 5:
                    System.out.print("ID de la reserva a confirmar: ");
                    int idConfirmar = sc.nextInt();
                    sc.nextLine();
                    if (dao.confirmarReserva(idConfirmar)) {
                        System.out.println("✅ Reserva " + idConfirmar + " confirmada con éxito. ¡Pago procesado!");
                    } else {
                        System.out.println("❌ Error al confirmar la reserva.");
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("⚠ Opción inválida");
            }
        } while (opcion != 0);
    }
}