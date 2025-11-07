package co.edu.veterinaria.pagos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("pago")
public class Pago {
    @Id
    private Long id;
    
    @Column("id_factura")
    private Long idFactura;
    
    private BigDecimal monto;
    
    @Column("medio_pago")
    private String medioPago; // EFECTIVO, TARJETA, TRANSFERENCIA, PSE
    
    private String estado; // PENDIENTE, APROBADO, RECHAZADO
    private String referencia;
    
    @Column("fecha_pago")
    private LocalDateTime fechaPago;
}

