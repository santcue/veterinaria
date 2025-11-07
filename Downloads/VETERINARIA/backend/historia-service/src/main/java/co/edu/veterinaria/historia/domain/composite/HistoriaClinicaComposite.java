package co.edu.veterinaria.historia.domain.composite;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite Pattern: Representa la historia clínica completa (compuesto)
 */
@Slf4j
@Data
public class HistoriaClinicaComposite implements HistoriaComponent {
    
    private Long id;
    private String tipo = "HISTORIA_COMPLETA";
    private List<HistoriaComponent> componentes = new ArrayList<>();
    
    public void agregarComponente(HistoriaComponent componente) {
        componentes.add(componente);
    }
    
    public void removerComponente(HistoriaComponent componente) {
        componentes.remove(componente);
    }
    
    @Override
    public Mono<String> renderizar() {
        log.debug("Renderizando historia clínica completa con {} componentes", componentes.size());
        
        return Flux.fromIterable(componentes)
            .flatMap(HistoriaComponent::renderizar)
            .collectList()
            .map(partes -> String.join("\n---\n", partes));
    }
    
    @Override
    public Mono<Boolean> validar() {
        log.debug("Validando historia clínica completa");
        
        return Flux.fromIterable(componentes)
            .flatMap(HistoriaComponent::validar)
            .all(Boolean::booleanValue);
    }
}

