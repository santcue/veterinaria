import axios from 'axios';

const api = axios.create({
  baseURL: '/api'
});

export const propietarioService = {
  obtenerTodos: async () => {
    const response = await api.get('/propietarios');
    return response.data;
  }
};

