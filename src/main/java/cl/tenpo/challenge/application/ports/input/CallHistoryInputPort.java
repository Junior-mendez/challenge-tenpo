package cl.tenpo.challenge.application.ports.input;

import cl.tenpo.challenge.adapters.dtos.CallHistoryResponse;
import reactor.core.publisher.Mono;

public interface CallHistoryInputPort {

  Mono<CallHistoryResponse> getCallHistoryPaged(int page, int size);
}
