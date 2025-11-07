package co.edu.veterinaria.historia.domain.composite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Composite Pattern: Hoja que representa una consulta individual
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaLeaf implements HistoriaComponent {
    
    private Long id;
    private String anamnesis;
    private String examenFisico;
    private String diagnostico;
    
    @Override
    public String getTipo() {
        return "CONSULTA";
    }
    
    @Override
    public Mono<String> renderizar() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== CONSULTA ===\n");
        sb.append("Anamnesis: ").append(anamnesis).append("\n");
        sb.append("Examen Físico: ").append(examenFisico).append("\n");
        sb.append("Diagnóstico: ").append(diagnostico);
        return Mono.just(sb.toString());
    }
    
    @Override
    public Mono<Boolean> validar() {
        boolean valida = anamnesis != null && !anamnesis.trim().isEmpty() &&
                        diagnostico != null && !diagnostico.trim().isEmpty();
        return Mono.just(valida);
    }
}

