package co.edu.veterinaria.auth.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("rol")
public class Rol {
    @Id
    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
}

