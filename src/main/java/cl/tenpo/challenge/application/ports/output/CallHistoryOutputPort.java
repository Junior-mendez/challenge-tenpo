package cl.tenpo.challenge.application.ports.output;

import cl.tenpo.challenge.adapters.repository.entities.CallHistoryEntity;
import cl.tenpo.challenge.domain.models.CallHistory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;

public interface CallHistoryOutputPort {

  Mono<Boolean> save(CallHistory callHistory);

  Mono<PageImpl<CallHistoryEntity>> getAllCallsPaged(PageRequest pageRequest);
}
