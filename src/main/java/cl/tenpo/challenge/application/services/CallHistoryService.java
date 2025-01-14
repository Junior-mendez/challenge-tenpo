package cl.tenpo.challenge.application.services;

import cl.tenpo.challenge.adapters.dtos.CallHistoryResponse;
import cl.tenpo.challenge.adapters.repository.entities.CallHistoryEntity;
import cl.tenpo.challenge.application.ports.input.CallHistoryInputPort;
import cl.tenpo.challenge.application.ports.output.CallHistoryOutputPort;
import cl.tenpo.challenge.domain.models.CallHistory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CallHistoryService implements CallHistoryInputPort {

  private final CallHistoryOutputPort callHistoryOutputPort;

  @Override
  public Mono<CallHistoryResponse> getCallHistoryPaged(int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    return callHistoryOutputPort.getAllCallsPaged(pageRequest).flatMap(this::buildCallHistory);
  }

  private Mono<CallHistoryResponse> buildCallHistory(PageImpl<CallHistoryEntity> page) {
    List<CallHistory> calls = page.get().map(this::buildCallHistory).toList();
    return Mono.just(
        CallHistoryResponse.builder()
            .callHistories(calls)
            .totalCount(page.getTotalElements())
            .currentPage(page.getNumber())
            .pageSize(page.getNumberOfElements())
            .totalPages(page.getTotalPages())
            .build());
  }

  private CallHistory buildCallHistory(CallHistoryEntity callHistoryEntity) {
    return CallHistory.builder()
        .date(callHistoryEntity.getDate())
        .request(callHistoryEntity.getRequest())
        .response(callHistoryEntity.getResponse())
        .endpoint(callHistoryEntity.getEndpoint())
        .build();
  }
}
