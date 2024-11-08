// src/axios-config.ts
import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/api',
});

axiosInstance.interceptors.request.use(config => {
    const username = localStorage.getItem('username');
    const password = localStorage.getItem('password');

    if (username && password) {
        config.auth = {
            username: username,
            password: password
        };
    }
    return config;
});

export default axiosInstance;