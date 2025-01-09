package cl.tenpo.challenge.application.ports.output;

import cl.tenpo.challenge.adapters.repository.entities.CallHistory;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;

public interface CallHistoryOutputPort {

    Mono<CallHistory> save(CallHistory callHistory);

    Mono<Object> getAllCallsPaged(PageRequest pageRequest);
}
