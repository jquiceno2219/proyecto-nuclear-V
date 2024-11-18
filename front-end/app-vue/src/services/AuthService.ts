import axios from 'axios';

/**
 * Servicio de autenticación para la aplicación.
 * - `login`: Realiza la autenticación usando credenciales básicas y guarda los datos del usuario en `sessionStorage`.
 * - `logout`: Elimina los datos de autenticación del almacenamiento de sesión.
 * - Interceptor de Axios: Añade automáticamente las credenciales de autenticación a las solicitudes si el usuario está logueado.
 */


const API_URL = 'http://localhost:8080/api'; // Cambia esto a tu URL de API

class AuthService {
    login(username: string, password: string) {
        const headers = {
            Authorization: 'Basic ' + btoa(username + ':' + password),
        };

        return axios.get(`${API_URL}/users/list`, { headers })
            .then(response => {
                sessionStorage.setItem('user', JSON.stringify({ username, password }));
                return response.data;
            })
            .catch(error => {
                throw error;
            });
    }

    logout() {

        sessionStorage.removeItem('user');
    }


}

axios.interceptors.request.use(config => {
    const user = JSON.parse(sessionStorage.getItem('user') || '{}');
    if (user && user.username && user.password) {
        config.headers['Authorization'] = 'Basic ' + btoa(user.username + ':' + user.password);
    }
    return config;
}, error => {
    return Promise.reject(error);
});

export default new AuthService();