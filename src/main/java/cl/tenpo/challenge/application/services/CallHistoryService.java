package cl.tenpo.challenge.application.services;

import cl.tenpo.challenge.adapters.repository.CallHistoryRepository;
import cl.tenpo.challenge.adapters.repository.entities.CallHistory;
import cl.tenpo.challenge.application.ports.input.CallHistoryInputPort;
import cl.tenpo.challenge.application.ports.output.CallHistoryOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CallHistoryService implements CallHistoryInputPort {

    private final CallHistoryOutputPort callHistoryOutputPort;

    @Override
    public Mono<Object> getCallHistoryPaged(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return callHistoryOutputPort.getAllCallsPaged(pageRequest);
    }
}
