package co.edu.veterinaria.mascotas.infrastructure.web.exception;

import co.edu.veterinaria.mascotas.domain.exception.EstrategiaValidacionNotFoundException;
import co.edu.veterinaria.mascotas.domain.exception.MascotaNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para respuestas consistentes
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MascotaNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleMascotaNotFound(MascotaNotFoundException ex) {
        log.warn("Mascota no encontrada: {}", ex.getMessage());
        Map<String, Object> body = crearErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
    
    @ExceptionHandler(EstrategiaValidacionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEstrategiaNotFound(EstrategiaValidacionNotFoundException ex) {
        log.error("Estrategia de validación no encontrada: {}", ex.getMessage());
        Map<String, Object> body = crearErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Argumento inválido: {}", ex.getMessage());
        Map<String, Object> body = crearErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(WebExchangeBindException ex) {
        log.warn("Error de validación: {}", ex.getMessage());
        Map<String, Object> body = crearErrorResponse(HttpStatus.BAD_REQUEST, "Error de validación");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        body.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        log.error("Error inesperado: ", ex);
        Map<String, Object> body = crearErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR, 
            "Error interno del servidor"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
    
    private Map<String, Object> crearErrorResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", "/api/mascotas");
        return body;
    }
}

