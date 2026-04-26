// ✅ Código que se ejecuta en todas las páginas donde se carga este script
console.log("El archivo script.js se ha cargado correctamente.");

// Lógica para el formulario de registro
document.getElementById('register-form')?.addEventListener('submit', function(event) {
    event.preventDefault();

    const nombre = document.getElementById('reg-nombre').value;
    const correo = document.getElementById('reg-correo').value;
    const documentoIdentidad = document.getElementById('reg-doc').value;
    const contrasenia = document.getElementById('reg-contrasenia').value;
    const telefono = document.getElementById('reg-telefono').value;

    const nuevoUsuario = {
        nombre: nombre,
        correo: correo,
        documentoIdentidad: documentoIdentidad,
        contrasenia: contrasenia,
        telefono: telefono,
        tipoUsuario: "huesped"
    };

    fetch('http://localhost:8080/api/usuarios', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(nuevoUsuario)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(errorData => {
                throw new Error(errorData.message);
            });
        }
        return response.json();
    })
    .then(data => {
        alert(data.message + " ¡Ahora puedes iniciar sesión!");
        document.getElementById('register-form').reset();
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al crear el usuario: ' + error.message);
    });
});

// ✅ Lógica para el formulario de login (CORREGIDA)
document.getElementById('login-form')?.addEventListener('submit', function(event) {
    event.preventDefault();

    const correo = document.getElementById('login-correo').value;
    const contrasenia = document.getElementById('login-contrasenia').value;

    const loginData = {
        correo: correo,
        contrasenia: contrasenia
    };

    fetch('http://localhost:8080/api/usuarios/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Credenciales incorrectas.');
        }
        return response.json();
    })
    .then(data => {
        // ✅ CORRECCIÓN: Guarda el ID del usuario en localStorage
        localStorage.setItem('idUsuario', data.idUsuario);
        alert("¡Has iniciado sesión con éxito! Redirigiendo a propiedades...");
        window.location.href = 'propiedades.html';
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al iniciar sesión: ' + error.message);
    });
});

// ✅ Código que solo se ejecuta en la página de propiedades
document.addEventListener('DOMContentLoaded', function() {
    if (window.location.pathname.endsWith('propiedades.html')) {
        fetchPropiedades();
        setupReservaModal();
    }
});

function fetchPropiedades() {
    fetch('/api/propiedades')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener las propiedades: ' + response.statusText);
            }
            return response.json();
        })
        .then(propiedades => {
            const container = document.getElementById('propiedades-container');
            container.innerHTML = '';

            if (propiedades.length === 0) {
                container.innerHTML = '<p>No hay propiedades disponibles en este momento.</p>';
                return;
            }

            propiedades.forEach(propiedad => {
                const card = document.createElement('div');
                card.classList.add('propiedad-card');

                card.innerHTML = `
                    <h3>${propiedad.titulo}</h3>
                    <p><strong>Descripción:</strong> ${propiedad.descripcion}</p>
                    <p><strong>Ubicación:</strong> ${propiedad.ciudad}, ${propiedad.pais}</p>
                    <p><strong>Capacidad:</strong> ${propiedad.capacidad} huéspedes</p>
                    <p><strong>Precio por Noche:</strong> $${propiedad.precioNoche}</p>
                    <p><strong>Anfitrión:</strong> ${propiedad.nombreAnfitrion || 'N/A'}</p>
                    <button class="boton-reserva" data-id="${propiedad.idPropiedad}">Reservar</button>
                `;

                container.appendChild(card);
            });

            // ✅ CORRECCIÓN: Agregar el listener a cada botón individualmente después de su creación
            document.querySelectorAll('.boton-reserva').forEach(button => {
                button.addEventListener('click', (e) => {
                    const propiedadId = e.target.dataset.id;
                    const reservaModal = document.getElementById('reserva-modal');
                    const hiddenInput = document.getElementById('propiedadId-reserva');
                    if (reservaModal && hiddenInput) {
                        hiddenInput.value = propiedadId;
                        reservaModal.style.display = 'flex';
                    }
                });
            });
        })
        .catch(error => {
            console.error('Error:', error);
            const container = document.getElementById('propiedades-container');
            container.innerHTML = `<p style="color: red;">${error.message}</p>`;
        });
}

function setupReservaModal() {
    const reservaModal = document.getElementById('reserva-modal');
    const closeButton = document.querySelector('.close-button');

    if (closeButton && reservaModal) {
        closeButton.addEventListener('click', () => {
            reservaModal.style.display = 'none';
        });
    }

    const reservaForm = document.getElementById('reserva-form');
    if (reservaForm) {
        reservaForm.addEventListener('submit', function(event) {
            event.preventDefault();

            const propiedadId = document.getElementById('propiedadId-reserva').value;
            const fechaInicio = document.getElementById('fechaInicio').value;
            const fechaFin = document.getElementById('fechaFin').value;

            // ✅ CORRECCIÓN: Obtiene el ID del usuario de localStorage
            const idHuesped = localStorage.getItem('idUsuario');

            if (!idHuesped) {
                alert("Error: No se encontró el ID del usuario. Por favor, inicia sesión de nuevo.");
                return;
            }

            const reserva = {
                idPropiedad: parseInt(propiedadId),
                fechaInicio: fechaInicio,
                fechaFin: fechaFin,
                idHuesped: parseInt(idHuesped)
            };

            fetch('http://localhost:8080/api/reservas', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(reserva)
            })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => {
                        throw new Error(errorData.message);
                    });
                }
                return response.json();
            })
            .then(data => {
                alert(data.message);
                if (reservaModal) {
                    reservaModal.style.display = 'none';
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error en la reserva: ' + error.message);
            });
        });
    }
}