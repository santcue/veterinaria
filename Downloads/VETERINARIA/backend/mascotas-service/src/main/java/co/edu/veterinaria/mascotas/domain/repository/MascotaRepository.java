package co.edu.veterinaria.mascotas.domain.repository;

import co.edu.veterinaria.mascotas.domain.model.Mascota;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MascotaRepository extends ReactiveCrudRepository<Mascota, Long> {
    
    Flux<Mascota> findByActivoTrue();
    
    @Query("SELECT * FROM mascota WHERE id_propietario = :idPropietario AND activo = true")
    Flux<Mascota> findByIdPropietarioAndActivoTrue(Long idPropietario);
    
    @Query("SELECT * FROM mascota WHERE id_especie = :idEspecie AND activo = true")
    Flux<Mascota> findByIdEspecieAndActivoTrue(Long idEspecie);
    
    @Query("SELECT * FROM mascota WHERE nombre LIKE :nombre AND activo = true")
    Flux<Mascota> findByNombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT * FROM mascota WHERE id = :id AND activo = true")
    Mono<Mascota> findByIdAndActivoTrue(Long id);
}

