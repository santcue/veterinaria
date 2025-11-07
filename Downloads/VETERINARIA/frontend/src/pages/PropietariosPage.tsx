import { useQuery } from '@tanstack/react-query';
import { propietarioService } from '../services/propietarioService';

export default function PropietariosPage() {
  const { data: propietarios } = useQuery({
    queryKey: ['propietarios'],
    queryFn: () => propietarioService.obtenerTodos()
  });

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Propietarios</h1>
      {/* Implementación de lista de propietarios */}
    </div>
  );
}

