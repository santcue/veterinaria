package co.edu.veterinaria.auth.domain.repository;

import co.edu.veterinaria.auth.domain.model.Usuario;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsuarioRepository extends ReactiveCrudRepository<Usuario, Long> {
    Mono<Usuario> findByEmailAndActivoTrue(String email);
    Mono<Usuario> findByIdAndActivoTrue(Long id);
}

