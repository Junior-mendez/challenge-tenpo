package cl.tenpo.challenge.application.ports.input;

import cl.tenpo.challenge.adapters.repository.entities.CallHistory;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CallHistoryInputPort {

    Mono<Object> getCallHistoryPaged(int page, int size);
}
