import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import NotificationCenter from './NotificationCenter';

interface LayoutProps {
  children: React.ReactNode;
}

export default function Layout({ children }: LayoutProps) {
  const { user, logout, hasRole } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <nav className="bg-blue-600 text-white shadow-lg">
        <div className="container mx-auto px-4">
          <div className="flex justify-between items-center h-16">
            <div className="flex items-center space-x-8">
              <Link to="/dashboard" className="text-xl font-bold">
                Sistema Veterinaria
              </Link>
              <div className="hidden md:flex space-x-4">
                <Link to="/dashboard" className="hover:bg-blue-700 px-3 py-2 rounded">
                  Dashboard
                </Link>
                {hasRole('VETERINARIO') || hasRole('ADMIN') && (
                  <Link to="/mascotas" className="hover:bg-blue-700 px-3 py-2 rounded">
                    Mascotas
                  </Link>
                )}
                {hasRole('RECEPCIONISTA') || hasRole('ADMIN') && (
                  <Link to="/propietarios" className="hover:bg-blue-700 px-3 py-2 rounded">
                    Propietarios
                  </Link>
                )}
                {(hasRole('VETERINARIO') || hasRole('RECEPCIONISTA') || hasRole('ADMIN')) && (
                  <Link to="/citas" className="hover:bg-blue-700 px-3 py-2 rounded">
                    Citas
                  </Link>
                )}
              </div>
            </div>
            <div className="flex items-center space-x-4">
              <NotificationCenter />
              <span className="text-sm">{user?.nombre}</span>
              <button
                onClick={handleLogout}
                className="bg-blue-700 hover:bg-blue-800 px-4 py-2 rounded"
              >
                Salir
              </button>
            </div>
          </div>
        </div>
      </nav>
      <main className="container mx-auto py-8 px-4">
        {children}
      </main>
    </div>
  );
}

