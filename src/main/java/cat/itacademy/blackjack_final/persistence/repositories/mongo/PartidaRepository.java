package cat.itacademy.blackjack_final.persistence.repositories.mongo;


import cat.itacademy.blackjack_final.persistence.entities.PartidaEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidaRepository extends ReactiveMongoRepository<PartidaEntity, String> {
}
