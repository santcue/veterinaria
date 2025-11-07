import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json'
  }
});

export const mascotaService = {
  obtenerTodas: async () => {
    const response = await api.get('/mascotas');
    return response.data;
  },
  
  obtenerPorId: async (id: number) => {
    const response = await api.get(`/mascotas/${id}`);
    return response.data;
  },
  
  crear: async (mascota: any) => {
    const response = await api.post('/mascotas', mascota);
    return response.data;
  },
  
  actualizar: async (id: number, mascota: any) => {
    const response = await api.put(`/mascotas/${id}`, mascota);
    return response.data;
  },
  
  eliminar: async (id: number) => {
    await api.delete(`/mascotas/${id}`);
  }
};

