import { useAuth } from '../contexts/AuthContext';
import { useNavigate } from 'react-router-dom';
import { ComponentFactorySelector } from '../patterns/factory/ComponentFactory';

const factory = ComponentFactorySelector.createFactory();
const Card = factory.createCard();

export default function DashboardPage() {
  const { user, hasRole } = useAuth();
  const navigate = useNavigate();

  const getDashboardContent = () => {
    if (hasRole('ADMIN')) {
      return (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <Card className="cursor-pointer hover:shadow-lg transition-shadow" onClick={() => navigate('/usuarios')}>
            <h3 className="text-xl font-semibold mb-2">Gestión de Usuarios</h3>
            <p className="text-gray-600">Administrar usuarios y roles</p>
          </Card>
          <Card className="cursor-pointer hover:shadow-lg transition-shadow" onClick={() => navigate('/mascotas')}>
            <h3 className="text-xl font-semibold mb-2">Mascotas</h3>
            <p className="text-gray-600">Gestionar mascotas</p>
          </Card>
          <Card className="cursor-pointer hover:shadow-lg transition-shadow" onClick={() => navigate('/reportes')}>
            <h3 className="text-xl font-semibold mb-2">Reportes</h3>
            <p className="text-gray-600">Ver reportes del sistema</p>
          </Card>
        </div>
      );
    }

    if (hasRole('VETERINARIO')) {
      return (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <Card className="cursor-pointer hover:shadow-lg transition-shadow" onClick={() => navigate('/citas')}>
            <h3 className="text-xl font-semibold mb-2">Mis Citas</h3>
            <p className="text-gray-600">Ver y gestionar citas</p>
          </Card>
          <Card className="cursor-pointer hover:shadow-lg transition-shadow" onClick={() => navigate('/historia-clinica')}>
            <h3 className="text-xl font-semibold mb-2">Historia Clínica</h3>
            <p className="text-gray-600">Consultar y crear historias</p>
          </Card>
          <Card className="cursor-pointer hover:shadow-lg transition-shadow" onClick={() => navigate('/prescripciones')}>
            <h3 className="text-xl font-semibold mb-2">Prescripciones</h3>
            <p className="text-gray-600">Crear prescripciones</p>
          </Card>
          <Card className="cursor-pointer hover:shadow-lg transition-shadow" onClick={() => navigate('/mascotas')}>
            <h3 className="text-xl font-semibold mb-2">Mascotas</h3>
            <p className="text-gray-600">Gestionar mascotas</p>
          </Card>
        </div>
      );
    }

    if (hasRole('RECEPCIONISTA')) {
      return (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <Card className="cursor-pointer hover:shadow-lg transition-shadow" onClick={() => navigate('/citas')}>
            <h3 className="text-xl font-semibold mb-2">Citas</h3>
            <p className="text-gray-600">Gestionar citas</p>
          </Card>
          <Card className="cursor-pointer hover:shadow-lg transition-shadow" onClick={() => navigate('/propietarios')}>
            <h3 className="text-xl font-semibold mb-2">Propietarios</h3>
            <p className="text-gray-600">Registrar propietarios</p>
          </Card>
          <Card className="cursor-pointer hover:shadow-lg transition-shadow" onClick={() => navigate('/pagos')}>
            <h3 className="text-xl font-semibold mb-2">Pagos</h3>
            <p className="text-gray-600">Gestionar pagos</p>
          </Card>
        </div>
      );
    }

    if (hasRole('ESTUDIANTE')) {
      return (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <Card className="cursor-pointer hover:shadow-lg transition-shadow" onClick={() => navigate('/mis-citas')}>
            <h3 className="text-xl font-semibold mb-2">Mis Citas Asignadas</h3>
            <p className="text-gray-600">Ver citas asignadas</p>
          </Card>
          <Card className="cursor-pointer hover:shadow-lg transition-shadow" onClick={() => navigate('/bitacora')}>
            <h3 className="text-xl font-semibold mb-2">Bitácora de Prácticas</h3>
            <p className="text-gray-600">Registrar prácticas</p>
          </Card>
        </div>
      );
    }

    return (
      <Card>
        <h3 className="text-xl font-semibold mb-2">Bienvenido</h3>
        <p className="text-gray-600">No tienes permisos asignados</p>
      </Card>
    );
  };

  return (
    <div className="container mx-auto p-6">
      <div className="mb-6">
        <h1 className="text-3xl font-bold text-gray-800 mb-2">
          Bienvenido, {user?.nombre}
        </h1>
        <p className="text-gray-600">Panel de control</p>
      </div>
      {getDashboardContent()}
    </div>
  );
}

