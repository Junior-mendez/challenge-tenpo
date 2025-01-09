package cl.tenpo.challenge.adapters.repository;

import cl.tenpo.challenge.adapters.repository.entities.CallHistory;
import cl.tenpo.challenge.application.ports.output.CallHistoryOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CallHistoryRepositoryAdapter implements CallHistoryOutputPort {

    private final CallHistoryRepository callHistoryRepository;

    @Override
    public Mono<CallHistory> save(CallHistory callHistory) {
        return callHistoryRepository.save(callHistory);
    }

    @Override
    public Mono<Object> getAllCallsPaged(PageRequest pageRequest) {
        return callHistoryRepository.findAllBy(pageRequest)
                .collectList()
                .zipWith(this.callHistoryRepository.count())
                .map(t -> new PageImpl<>(t.getT1(), pageRequest, t.getT2()));
    }
}
