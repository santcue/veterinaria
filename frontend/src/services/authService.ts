import axios, { InternalAxiosRequestConfig } from 'axios';

const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json'
  }
});

export interface LoginRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  email: string;
  nombre: string;
  roles: string[];
}

export interface User {
  email: string;
  nombre: string;
  roles: string[];
}

export const authService = {
  login: async (credentials: LoginRequest): Promise<AuthResponse> => {
    const response = await api.post('/auth/login', credentials);
    const data = response.data;
    
    // Guardar token en localStorage
    localStorage.setItem('token', data.token);
    localStorage.setItem('user', JSON.stringify({
      email: data.email,
      nombre: data.nombre,
      roles: data.roles
    }));
    
    // Configurar token en axios
    api.defaults.headers.common['Authorization'] = `Bearer ${data.token}`;
    
    return data;
  },
  
  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    delete api.defaults.headers.common['Authorization'];
  },
  
  getCurrentUser: (): User | null => {
    const userStr = localStorage.getItem('user');
    if (!userStr) return null;
    return JSON.parse(userStr);
  },
  
  getToken: (): string | null => {
    return localStorage.getItem('token');
  },
  
  isAuthenticated: (): boolean => {
    return !!localStorage.getItem('token');
  },
  
  hasRole: (role: string): boolean => {
    const user = authService.getCurrentUser();
    return user?.roles.includes(role) || false;
  },
  
  validateToken: async (token: string): Promise<{ valid: boolean }> => {
    const response = await api.get('/auth/validate', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    // El backend devuelve un Boolean, lo convertimos al formato esperado
    return { valid: response.data === true };
  }
};

// Configurar interceptor para agregar token a todas las peticiones
api.interceptors.request.use((config: InternalAxiosRequestConfig) => {
  const token = authService.getToken();
  if (token && config.headers) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;

