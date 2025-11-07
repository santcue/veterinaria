package co.edu.veterinaria.servicios.domain.service;

import co.edu.veterinaria.servicios.domain.factory.ServicioFactory;
import co.edu.veterinaria.servicios.domain.model.Servicio;
import co.edu.veterinaria.servicios.domain.repository.ServicioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service Pattern: Lógica de negocio para Servicios
 * Factory Method Pattern: Usa ServicioFactory para crear diferentes tipos
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServicioService {
    
    private final ServicioRepository repository;
    private final List<ServicioFactory> factories;
    
    public Flux<Servicio> obtenerTodos() {
        return repository.findByActivoTrue();
    }
    
    public Mono<Servicio> obtenerPorId(Long id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Servicio no encontrado")));
    }
    
    public Mono<Servicio> crear(String tipoServicio, Servicio servicio) {
        log.debug("Creando servicio de tipo: {}", tipoServicio);
        
        ServicioFactory factory = factories.stream()
            .filter(f -> f.getTipoServicio().equalsIgnoreCase(tipoServicio))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Tipo de servicio no válido: " + tipoServicio));
        
        servicio.setTipoServicio(tipoServicio);
        servicio.setActivo(true);
        servicio.setFechaCreacion(LocalDateTime.now());
        
        return factory.crearServicio(servicio)
            .flatMap(repository::save)
            .doOnSuccess(s -> log.info("Servicio creado: {}", s.getId()));
    }
}

