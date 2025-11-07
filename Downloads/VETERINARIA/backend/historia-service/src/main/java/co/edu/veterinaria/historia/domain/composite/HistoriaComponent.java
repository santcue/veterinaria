package co.edu.veterinaria.historia.domain.composite;

import reactor.core.publisher.Mono;

/**
 * Composite Pattern: Componente base de la estructura jerárquica de Historia Clínica
 */
public interface HistoriaComponent {
    
    /**
     * Obtiene el ID del componente
     */
    Long getId();
    
    /**
     * Obtiene el tipo de componente
     */
    String getTipo();
    
    /**
     * Renderiza el componente (para visualización)
     */
    Mono<String> renderizar();
    
    /**
     * Valida el componente
     */
    Mono<Boolean> validar();
}

