package co.edu.veterinaria.historia.domain.service;

import co.edu.veterinaria.historia.domain.composite.HistoriaClinicaComposite;
import co.edu.veterinaria.historia.domain.composite.ConsultaLeaf;
import co.edu.veterinaria.historia.domain.model.HistoriaClinica;
import co.edu.veterinaria.historia.domain.repository.HistoriaClinicaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Service Pattern: Lógica de negocio para Historia Clínica
 * Composite Pattern: Usa HistoriaClinicaComposite para estructuras jerárquicas
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HistoriaClinicaService {
    
    private final HistoriaClinicaRepository repository;
    
    public Mono<HistoriaClinica> crear(HistoriaClinica historia) {
        log.debug("Creando historia clínica para cita: {}", historia.getIdCita());
        historia.setFechaCierre(LocalDateTime.now());
        return repository.save(historia)
            .doOnSuccess(h -> log.info("Historia clínica creada: {}", h.getId()));
    }
    
    public Mono<HistoriaClinica> obtenerPorId(Long id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Historia clínica no encontrada")));
    }
    
    public Mono<HistoriaClinica> obtenerPorCita(Long idCita) {
        return repository.findByIdCita(idCita)
            .switchIfEmpty(Mono.error(new RuntimeException("Historia clínica no encontrada para la cita")));
    }
    
    /**
     * Crea una estructura Composite de la historia clínica
     */
    public Mono<HistoriaClinicaComposite> construirComposite(Long idHistoria) {
        return repository.findById(idHistoria)
            .flatMap(historia -> {
                HistoriaClinicaComposite composite = new HistoriaClinicaComposite();
                composite.setId(historia.getId());
                
                // Crear hoja de consulta
                ConsultaLeaf consulta = new ConsultaLeaf(
                    historia.getId(),
                    historia.getAnamnesis(),
                    historia.getExamenFisico(),
                    historia.getDiagnostico()
                );
                
                composite.agregarComponente(consulta);
                
                return Mono.just(composite);
            });
    }
    
    public Mono<String> renderizarHistoria(Long idHistoria) {
        return construirComposite(idHistoria)
            .flatMap(HistoriaClinicaComposite::renderizar);
    }
}

