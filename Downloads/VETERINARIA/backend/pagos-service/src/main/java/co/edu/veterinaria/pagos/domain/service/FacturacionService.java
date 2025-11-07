package co.edu.veterinaria.pagos.domain.service;

import co.edu.veterinaria.pagos.domain.model.Factura;
import co.edu.veterinaria.pagos.domain.model.Pago;
import co.edu.veterinaria.pagos.domain.repository.FacturaRepository;
import co.edu.veterinaria.pagos.domain.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service Pattern: Lógica de negocio para Facturación y Pagos
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FacturacionService {
    
    private final FacturaRepository facturaRepository;
    private final PagoRepository pagoRepository;
    
    public Mono<Factura> generarFactura(Factura factura) {
        log.debug("Generando factura para cita: {}", factura.getIdCita());
        
        // Generar número de factura único
        String numeroFactura = "FAC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        factura.setNumeroFactura(numeroFactura);
        factura.setEstado("PENDIENTE");
        factura.setFechaEmision(LocalDateTime.now());
        factura.setFechaVencimiento(LocalDateTime.now().plusDays(30));
        
        // Calcular total
        BigDecimal total = factura.getItems().stream()
            .map(item -> item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        factura.setTotal(total);
        
        return facturaRepository.save(factura)
            .doOnSuccess(f -> log.info("Factura generada: {}", f.getNumeroFactura()));
    }
    
    public Mono<Factura> obtenerPorId(Long id) {
        return facturaRepository.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Factura no encontrada")));
    }
    
    public Flux<Factura> obtenerPorPropietario(Long idPropietario) {
        return facturaRepository.findByIdPropietario(idPropietario);
    }
    
    public Mono<Pago> registrarPago(Pago pago) {
        log.debug("Registrando pago para factura: {}", pago.getIdFactura());
        
        return facturaRepository.findById(pago.getIdFactura())
            .switchIfEmpty(Mono.error(new RuntimeException("Factura no encontrada")))
            .flatMap(factura -> {
                if ("PAGADA".equals(factura.getEstado())) {
                    return Mono.error(new RuntimeException("La factura ya está pagada"));
                }
                
                pago.setFechaPago(LocalDateTime.now());
                pago.setEstado("APROBADO");
                
                // Actualizar estado de factura
                factura.setEstado("PAGADA");
                
                return facturaRepository.save(factura)
                    .then(pagoRepository.save(pago));
            })
            .doOnSuccess(p -> log.info("Pago registrado exitosamente"));
    }
}

