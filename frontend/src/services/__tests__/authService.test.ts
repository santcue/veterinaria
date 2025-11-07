import { describe, it, expect, vi, beforeEach } from 'vitest';
import { authService } from '../authService';

// Mock de axios
vi.mock('axios', () => {
  const mockAxios = {
    create: vi.fn(() => ({
      post: vi.fn(),
      get: vi.fn(),
      defaults: {
        headers: {
          common: {}
        }
      },
      interceptors: {
        request: {
          use: vi.fn()
        }
      }
    })),
    default: {
      create: vi.fn(() => ({
        post: vi.fn(),
        get: vi.fn(),
        defaults: {
          headers: {
            common: {}
          }
        },
        interceptors: {
          request: {
            use: vi.fn()
          }
        }
      }))
    }
  };
  return mockAxios;
});

describe('AuthService', () => {
  beforeEach(() => {
    vi.clearAllMocks();
    localStorage.clear();
  });

  it('debería hacer login exitosamente', async () => {
    const mockResponse = {
      data: {
        token: 'mock-jwt-token',
        email: 'admin@veterinaria.edu.co',
        nombre: 'Admin',
        roles: ['ADMIN'],
      },
    };

    // Mock del módulo axios
    const axiosModule = await import('axios');
    const mockApi = {
      post: vi.fn().mockResolvedValue(mockResponse),
      defaults: {
        headers: {
          common: {}
        }
      }
    };
    
    vi.mocked(axiosModule.default.create).mockReturnValue(mockApi as any);

    const result = await authService.login({
      email: 'admin@veterinaria.edu.co',
      password: 'password'
    });

    expect(result).toBeDefined();
    expect(result.token).toBe('mock-jwt-token');
  });

  it('debería validar token', async () => {
    const mockResponse = { data: { valid: true } };

    const axiosModule = await import('axios');
    const mockApi = {
      get: vi.fn().mockResolvedValue(mockResponse)
    };
    
    vi.mocked(axiosModule.default.create).mockReturnValue(mockApi as any);

    const result = await authService.validateToken('mock-token');

    expect(result).toBeDefined();
    expect(result.valid).toBe(true);
  });
});

