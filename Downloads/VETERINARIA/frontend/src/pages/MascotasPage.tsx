import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { ComponentFactorySelector } from '../patterns/factory/ComponentFactory';
import { formMediator } from '../patterns/mediator/FormMediator';
import { mascotaService } from '../services/mascotaService';
import { useState, useEffect } from 'react';

const factory = ComponentFactorySelector.createFactory();
const Button = factory.createButton();
const Input = factory.createInput();
const Card = factory.createCard();

export default function MascotasPage() {
  const queryClient = useQueryClient();
  const [nombre, setNombre] = useState('');

  const { data: mascotas, isLoading } = useQuery({
    queryKey: ['mascotas'],
    queryFn: () => mascotaService.obtenerTodas()
  });

  const crearMutation = useMutation({
    mutationFn: mascotaService.crear,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['mascotas'] });
      formMediator.notify('mascota-creada');
    }
  });

  useEffect(() => {
    formMediator.subscribe('mascota-creada', () => {
      setNombre('');
      alert('Mascota creada exitosamente');
    });
  }, []);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Lógica de creación
  };

  if (isLoading) return <div>Cargando...</div>;

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Gestión de Mascotas</h1>
      
      <Card>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block mb-2">Nombre</label>
            <Input
              value={nombre}
              onChange={(e: any) => setNombre(e.target.value)}
              placeholder="Nombre de la mascota"
            />
          </div>
          <Button type="submit">Crear Mascota</Button>
        </form>
      </Card>

      <div className="mt-6">
        <h2 className="text-xl font-semibold mb-2">Lista de Mascotas</h2>
        {mascotas?.map((mascota: any) => (
          <Card key={mascota.id}>
            <h3 className="font-semibold">{mascota.nombre}</h3>
            <p className="text-sm text-gray-600">ID: {mascota.id}</p>
          </Card>
        ))}
      </div>
    </div>
  );
}

