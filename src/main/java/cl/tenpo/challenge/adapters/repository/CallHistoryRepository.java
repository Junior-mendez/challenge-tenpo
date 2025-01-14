package cl.tenpo.challenge.adapters.repository;

import cl.tenpo.challenge.adapters.repository.entities.CallHistoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CallHistoryRepository extends R2dbcRepository<CallHistoryEntity, Integer> {

  Flux<CallHistoryEntity> findAllBy(Pageable pageable);
}
