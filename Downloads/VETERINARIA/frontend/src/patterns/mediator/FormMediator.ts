/**
 * Mediator Pattern: Coordina la comunicación entre componentes de formularios
 */
export class FormMediator {
  private components: Map<string, any> = new Map();
  private listeners: Map<string, Function[]> = new Map();

  /**
   * Registra un componente en el mediador
   */
  register(componentId: string, component: any): void {
    this.components.set(componentId, component);
  }

  /**
   * Notifica a todos los componentes suscritos a un evento
   */
  notify(event: string, data?: any): void {
    const listeners = this.listeners.get(event) || [];
    listeners.forEach(listener => listener(data));
  }

  /**
   * Suscribe un listener a un evento
   */
  subscribe(event: string, listener: Function): void {
    if (!this.listeners.has(event)) {
      this.listeners.set(event, []);
    }
    this.listeners.get(event)!.push(listener);
  }

  /**
   * Obtiene un componente registrado
   */
  getComponent(componentId: string): any {
    return this.components.get(componentId);
  }
}

// Instancia singleton del mediador
export const formMediator = new FormMediator();

