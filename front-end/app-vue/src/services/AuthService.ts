import axios from 'axios';
import type {User} from "@/models/User";


/**
 * Servicio de autenticación para la aplicación.
 * - `login`: Realiza la autenticación usando credenciales básicas y guarda los datos del usuario en `sessionStorage`.
 * - `logout`: Elimina los datos de autenticación del almacenamiento de sesión.
 * - Interceptor de Axios: Añade automáticamente las credenciales de autenticación a las solicitudes si el usuario está logueado.
 */


const API_URL = 'http://localhost:8080/api';
class AuthService {
    login(username: string, password: string) {
        return axios.get(`${API_URL}/users/list`, {
            auth: {
                username: username,
                password: password
            }
        })
            .then(response => {
                const user = response.data.find((u: User) => u.userName === username);

                if (user) {
                    sessionStorage.setItem('user', JSON.stringify({
                        ...user,
                        username: username,
                        password: password // Considera no almacenar la contraseña
                    }));
                    return user;
                } else {
                    throw new Error('Usuario no encontrado');
                }
            })
            .catch(error => {
                // Manejo de errores más específico
                if (error.response) {
                    // La solicitud se realizó y el servidor respondió con un código de estado
                    // que está fuera del rango de 2xx
                    console.error('Error en la respuesta del servidor:', error.response.data);
                    throw new Error('Credenciales incorrectas o usuario no encontrado');
                } else if (error.request) {
                    // La solicitud se realizó pero no se recibió respuesta
                    console.error('No se recibió respuesta del servidor:', error.request);
                    throw new Error('No se pudo conectar al servidor');
                } else {
                    // Algo sucedió al configurar la solicitud que provocó un error
                    console.error('Error al configurar la solicitud:', error.message);
                    throw new Error('Error en la solicitud');
                }
            });
    }

    logout() {
        sessionStorage.removeItem('user');
    }
}
axios.interceptors.request.use(config => {
    const user = JSON.parse(sessionStorage.getItem('user') || '{}');
    if (user && user.username && user.password) {
        config.auth = {
            username: user.username,
            password: user.password
        };
    }
    return config;
}, error => {
    return Promise.reject(error);
});

export default new AuthService();
