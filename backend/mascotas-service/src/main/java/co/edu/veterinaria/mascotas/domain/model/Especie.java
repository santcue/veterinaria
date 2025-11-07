package co.edu.veterinaria.mascotas.domain.model;

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
@Table("especie")
public class Especie {
    @Id
    private Long id;
    private String nombre;
    private Boolean activo;
}

