import api from './authService';

export interface Mascota {
  id?: number;
  idPropietario: number;
  idEspecie: number;
  idRaza?: number;
  nombre: string;
  fechaNacimiento?: string;
  sexo: 'M' | 'H';
  color?: string;
  microchip?: string;
  pesoKg?: number;
  esterilizado?: boolean;
  activo?: boolean;
}

export const mascotaService = {
  obtenerTodas: async (): Promise<Mascota[]> => {
    const response = await api.get('/mascotas');
    return response.data;
  },
  
  obtenerPorId: async (id: number): Promise<Mascota> => {
    const response = await api.get(`/mascotas/${id}`);
    return response.data;
  },
  
  crear: async (mascota: Mascota): Promise<Mascota> => {
    const response = await api.post('/mascotas', mascota);
    return response.data;
  },
  
  actualizar: async (id: number, mascota: Partial<Mascota>): Promise<Mascota> => {
    const response = await api.put(`/mascotas/${id}`, mascota);
    return response.data;
  },
  
  eliminar: async (id: number): Promise<void> => {
    await api.delete(`/mascotas/${id}`);
  }
};

