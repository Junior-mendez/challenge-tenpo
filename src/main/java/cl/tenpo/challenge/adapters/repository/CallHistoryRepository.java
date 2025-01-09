package cl.tenpo.challenge.adapters.repository;

import cl.tenpo.challenge.adapters.repository.entities.CallHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CallHistoryRepository extends R2dbcRepository<CallHistory, Integer> {

    Flux<CallHistory> findAllBy(Pageable pageable);
}

