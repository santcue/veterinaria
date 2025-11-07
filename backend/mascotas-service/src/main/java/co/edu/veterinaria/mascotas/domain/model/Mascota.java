package co.edu.veterinaria.mascotas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("mascota")
public class Mascota {
    @Id
    private Long id;
    
    @Column("id_propietario")
    private Long idPropietario;
    
    @Column("id_especie")
    private Long idEspecie;
    
    @Column("id_raza")
    private Long idRaza;
    
    private String nombre;
    
    @Column("fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    private String sexo; // M, H
    
    private String color;
    
    private String microchip;
    
    @Column("peso_kg")
    private BigDecimal pesoKg;
    
    private Boolean esterilizado;
    
    private Boolean activo;
    
    @Column("fecha_registro")
    private LocalDateTime fechaRegistro;
}

