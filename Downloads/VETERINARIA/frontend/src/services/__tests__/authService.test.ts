import { describe, it, expect, vi, beforeEach } from 'vitest';
import * as authService from '../authService';

// Mock de axios
vi.mock('axios', () => ({
  default: {
    create: vi.fn(() => ({
      post: vi.fn(),
      get: vi.fn(),
    })),
  },
}));

describe('AuthService', () => {
  beforeEach(() => {
    vi.clearAllMocks();
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

    const axios = await import('axios');
    vi.mocked(axios.default.create().post).mockResolvedValue(mockResponse);

    const result = await authService.login('admin@veterinaria.edu.co', 'password');

    expect(result).toBeDefined();
    expect(result.token).toBe('mock-jwt-token');
  });

  it('debería validar token', async () => {
    const mockResponse = { data: { valid: true } };

    const axios = await import('axios');
    vi.mocked(axios.default.create().get).mockResolvedValue(mockResponse);

    const result = await authService.validateToken('mock-token');

    expect(result).toBeDefined();
  });
});

