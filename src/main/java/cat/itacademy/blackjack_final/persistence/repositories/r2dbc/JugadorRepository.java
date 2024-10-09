package cat.itacademy.blackjack_final.persistence.repositories.r2dbc;

import cat.itacademy.blackjack_final.persistence.entities.JugadorEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface JugadorRepository extends ReactiveCrudRepository<JugadorEntity, Long> {
    Mono<JugadorEntity> findByNom(String name);
}
