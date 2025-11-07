package co.edu.veterinaria.estudiantes.domain.observer;

import co.edu.veterinaria.estudiantes.domain.model.EstudiantePractica;
import reactor.core.publisher.Mono;

/**
 * Observer Pattern: Observadores de cambios en citas que afectan a estudiantes
 */
public interface CitaObserver {
    
    /**
     * Notifica cuando un estudiante es asignado a una cita
     */
    Mono<Void> onEstudianteAsignado(EstudiantePractica estudiante, Long idCita);
    
    /**
     * Notifica cuando una cita es cancelada
     */
    Mono<Void> onCitaCancelada(Long idCita);
    
    /**
     * Notifica cuando una cita cambia de horario
     */
    Mono<Void> onCitaReprogramada(Long idCita);
    
    /**
     * Obtiene el nombre del observador
     */
    String getNombre();
}

