package cl.tenpo.challenge.adapters.repository;

import cl.tenpo.challenge.adapters.repository.entities.CallHistoryEntity;
import cl.tenpo.challenge.application.ports.output.CallHistoryOutputPort;
import cl.tenpo.challenge.domain.models.CallHistory;
import java.time.LocalDateTime;
import java.util.Objects;
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
  public Mono<Boolean> save(CallHistory callHistory) {
    return callHistoryRepository
        .save(buildCallHistoryEntity(callHistory))
        .flatMap(e -> Mono.just(Objects.nonNull(e)));
  }

  @Override
  public Mono<PageImpl<CallHistoryEntity>> getAllCallsPaged(PageRequest pageRequest) {
    return callHistoryRepository
        .findAllBy(pageRequest)
        .collectList()
        .zipWith(this.callHistoryRepository.count())
        .map(t -> new PageImpl<>(t.getT1(), pageRequest, t.getT2()));
  }

  private CallHistoryEntity buildCallHistoryEntity(CallHistory callHistory) {
    return CallHistoryEntity.builder()
        .date(LocalDateTime.now().toString())
        .response(callHistory.getResponse())
        .request(callHistory.getRequest())
        .endpoint(callHistory.getEndpoint())
        .build();
  }
}
