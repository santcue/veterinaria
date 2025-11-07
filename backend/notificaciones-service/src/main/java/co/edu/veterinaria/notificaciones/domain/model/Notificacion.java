package co.edu.veterinaria.notificaciones.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {
    private Long id;
    private String destinatario;
    private String tipoCanal;
    private String titulo;
    private String mensaje;
    private String estado;
}

