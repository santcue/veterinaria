import { useState, useEffect } from 'react';
import { ComponentFactorySelector } from '../patterns/factory/ComponentFactory';
import { formMediator } from '../patterns/mediator/FormMediator';

const factory = ComponentFactorySelector.createFactory();
const Card = factory.createCard();

interface Notification {
  id: number;
  titulo: string;
  mensaje: string;
  fecha: Date;
  leida: boolean;
}

export default function NotificationCenter() {
  const [notifications, setNotifications] = useState<Notification[]>([]);
  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    // Suscribirse a eventos de notificaciones
    formMediator.subscribe('nueva-notificacion', (notificacion: Notification) => {
      setNotifications(prev => [notificacion, ...prev]);
    });

    // Cargar notificaciones existentes
    loadNotifications();
  }, []);

  const loadNotifications = async () => {
    // Aquí se cargarían las notificaciones desde el backend
    // Por ahora simulamos
    setNotifications([
      {
        id: 1,
        titulo: 'Nueva cita asignada',
        mensaje: 'Tienes una nueva cita asignada para mañana',
        fecha: new Date(),
        leida: false
      }
    ]);
  };

  const markAsRead = (id: number) => {
    setNotifications(prev =>
      prev.map(n => n.id === id ? { ...n, leida: true } : n)
    );
  };

  const unreadCount = notifications.filter(n => !n.leida).length;

  return (
    <div className="relative">
      <button
        onClick={() => setIsOpen(!isOpen)}
        className="relative p-2 text-gray-600 hover:text-gray-800"
      >
        <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
        </svg>
        {unreadCount > 0 && (
          <span className="absolute top-0 right-0 block h-2 w-2 rounded-full bg-red-500"></span>
        )}
      </button>

      {isOpen && (
        <div className="absolute right-0 mt-2 w-80 bg-white rounded-lg shadow-lg z-50 max-h-96 overflow-y-auto">
          <div className="p-4 border-b">
            <h3 className="font-semibold">Notificaciones</h3>
          </div>
          <div className="divide-y">
            {notifications.length === 0 ? (
              <div className="p-4 text-center text-gray-500">
                No hay notificaciones
              </div>
            ) : (
              notifications.map(notif => (
                <div
                  key={notif.id}
                  className={`p-4 cursor-pointer hover:bg-gray-50 ${!notif.leida ? 'bg-blue-50' : ''}`}
                  onClick={() => markAsRead(notif.id)}
                >
                  <h4 className="font-medium">{notif.titulo}</h4>
                  <p className="text-sm text-gray-600">{notif.mensaje}</p>
                  <p className="text-xs text-gray-400 mt-1">
                    {notif.fecha.toLocaleDateString()}
                  </p>
                </div>
              ))
            )}
          </div>
        </div>
      )}
    </div>
  );
}

