import api from './authService';

export interface Propietario {
  id?: number;
  documento: string;
  nombres: string;
  apellidos: string;
  email: string;
  telefono?: string;
  direccion?: string;
  activo?: boolean;
}

export const propietarioService = {
  obtenerTodos: async (): Promise<Propietario[]> => {
    const response = await api.get('/propietarios');
    return response.data;
  },
  
  obtenerPorId: async (id: number): Promise<Propietario> => {
    const response = await api.get(`/propietarios/${id}`);
    return response.data;
  },
  
  crear: async (propietario: Propietario): Promise<Propietario> => {
    const response = await api.post('/propietarios', propietario);
    return response.data;
  },
  
  actualizar: async (id: number, propietario: Partial<Propietario>): Promise<Propietario> => {
    const response = await api.put(`/propietarios/${id}`, propietario);
    return response.data;
  },
  
  eliminar: async (id: number): Promise<void> => {
    await api.delete(`/propietarios/${id}`);
  }
};

