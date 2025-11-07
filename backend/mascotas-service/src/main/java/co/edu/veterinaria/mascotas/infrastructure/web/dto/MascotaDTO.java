package co.edu.veterinaria.mascotas.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MascotaDTO {
    private Long id;
    
    @NotNull(message = "El ID del propietario es obligatorio")
    private Long idPropietario;
    
    @NotNull(message = "El ID de la especie es obligatorio")
    private Long idEspecie;
    
    private Long idRaza;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "El nombre no puede exceder 80 caracteres")
    private String nombre;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    
    @NotBlank(message = "El sexo es obligatorio")
    @Pattern(regexp = "^[MH]$", message = "El sexo debe ser M (Macho) o H (Hembra)")
    private String sexo;
    
    @Size(max = 60, message = "El color no puede exceder 60 caracteres")
    private String color;
    
    @Size(max = 80, message = "El microchip no puede exceder 80 caracteres")
    private String microchip;
    
    @DecimalMin(value = "0.0", message = "El peso debe ser mayor o igual a 0")
    @DecimalMax(value = "1000.0", message = "El peso debe ser menor o igual a 1000 kg")
    private BigDecimal pesoKg;
    
    private Boolean esterilizado;
    
    private Boolean activo;
}

