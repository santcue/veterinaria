/**
 * Abstract Factory Pattern: Crea componentes según el tipo de dispositivo
 */
export interface ComponentFactory {
  createButton(): React.ComponentType<any>;
  createInput(): React.ComponentType<any>;
  createCard(): React.ComponentType<any>;
  createModal(): React.ComponentType<any>;
}

/**
 * Factory para dispositivos móviles
 */
export class MobileComponentFactory implements ComponentFactory {
  createButton(): React.ComponentType<any> {
    return ({ children, ...props }: any) => (
      <button
        className="w-full py-3 px-4 bg-blue-600 text-white rounded-lg font-medium touch-manipulation"
        {...props}
      >
        {children}
      </button>
    );
  }

  createInput(): React.ComponentType<any> {
    return ({ ...props }: any) => (
      <input
        className="w-full py-3 px-4 border border-gray-300 rounded-lg text-base"
        {...props}
      />
    );
  }

  createCard(): React.ComponentType<any> {
    return ({ children, ...props }: any) => (
      <div className="bg-white rounded-lg shadow-md p-4 mb-4" {...props}>
        {children}
      </div>
    );
  }

  createModal(): React.ComponentType<any> {
    return ({ children, isOpen, onClose, ...props }: any) => (
      isOpen ? (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-lg max-w-md w-full max-h-[90vh] overflow-y-auto">
            {children}
          </div>
        </div>
      ) : null
    );
  }
}

/**
 * Factory para dispositivos de escritorio
 */
export class DesktopComponentFactory implements ComponentFactory {
  createButton(): React.ComponentType<any> {
    return ({ children, ...props }: any) => (
      <button
        className="px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-colors"
        {...props}
      >
        {children}
      </button>
    );
  }

  createInput(): React.ComponentType<any> {
    return ({ ...props }: any) => (
      <input
        className="w-full py-2 px-3 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
        {...props}
      />
    );
  }

  createCard(): React.ComponentType<any> {
    return ({ children, ...props }: any) => (
      <div className="bg-white rounded-lg shadow-lg p-6 mb-4" {...props}>
        {children}
      </div>
    );
  }

  createModal(): React.ComponentType<any> {
    return ({ children, isOpen, onClose, ...props }: any) => (
      isOpen ? (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg max-w-2xl w-full max-h-[90vh] overflow-y-auto">
            {children}
          </div>
        </div>
      ) : null
    );
  }
}

/**
 * Factory selector según el tipo de dispositivo
 */
export class ComponentFactorySelector {
  static createFactory(): ComponentFactory {
    const isMobile = window.innerWidth < 768;
    return isMobile ? new MobileComponentFactory() : new DesktopComponentFactory();
  }
}

