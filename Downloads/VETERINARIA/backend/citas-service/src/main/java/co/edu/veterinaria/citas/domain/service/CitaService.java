package co.edu.veterinaria.citas.domain.service;

import co.edu.veterinaria.citas.domain.model.Cita;
import co.edu.veterinaria.citas.domain.repository.CitaRepository;
import co.edu.veterinaria.citas.domain.strategy.AsignacionHorarioStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service Pattern: Lógica de negocio para Citas
 * Strategy Pattern: Usa AsignacionHorarioStrategy para asignar horarios
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CitaService {
    
    private final CitaRepository citaRepository;
    private final List<AsignacionHorarioStrategy> estrategias;
    
    public Flux<Cita> obtenerTodas() {
        return citaRepository.findAll();
    }
    
    public Mono<Cita> obtenerPorId(Long id) {
        return citaRepository.findById(id);
    }
    
    public Mono<Cita> crear(String estrategiaNombre, Cita cita) {
        // Buscar estrategia
        AsignacionHorarioStrategy estrategia = estrategias.stream()
            .filter(e -> e.getNombre().equalsIgnoreCase(estrategiaNombre))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Estrategia no encontrada: " + estrategiaNombre));
        
        // Asignar horario usando estrategia
        return estrategia.asignarHorario(cita)
            .flatMap(horario -> {
                cita.setFechaHora(horario);
                cita.setFechaCreacion(LocalDateTime.now());
                return citaRepository.save(cita);
            });
    }
    
    public Mono<Cita> confirmar(Long id) {
        return citaRepository.findById(id)
            .flatMap(cita -> {
                // Estado CONFIRMADA = 2
                cita.setIdEstado(2L);
                return citaRepository.save(cita);
            });
    }
}

