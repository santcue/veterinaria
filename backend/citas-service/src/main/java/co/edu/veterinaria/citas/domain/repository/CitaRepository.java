package co.edu.veterinaria.citas.domain.repository;

import co.edu.veterinaria.citas.domain.model.Cita;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface CitaRepository extends ReactiveCrudRepository<Cita, Long> {
    
    @Query("SELECT * FROM cita WHERE id_mascota = :idMascota")
    Flux<Cita> findByIdMascota(Long idMascota);
    
    @Query("SELECT * FROM cita WHERE id_veterinario = :idVeterinario")
    Flux<Cita> findByIdVeterinario(Long idVeterinario);
    
    @Query("SELECT * FROM cita WHERE fecha_hora BETWEEN :inicio AND :fin")
    Flux<Cita> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT * FROM cita WHERE id = :id AND id_estado = :idEstado")
    Mono<Cita> findByIdAndIdEstado(Long id, Long idEstado);
}

