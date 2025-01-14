package cl.tenpo.challenge.utils;

import cl.tenpo.challenge.adapters.dtos.CallHistoryResponse;
import cl.tenpo.challenge.adapters.repository.entities.CallHistoryEntity;
import cl.tenpo.challenge.domain.models.CallHistory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import reactor.core.publisher.Mono;

public class UtilsTests {
  private UtilsTests() {}

  public static Mono<CallHistoryResponse> buildCallHistoryResponse() {
    List<CallHistory> calls = new ArrayList<>();
    CallHistory callHistory =
        CallHistory.builder()
            .date(LocalDateTime.now().toString())
            .endpoint("/api/v1/percent")
            .response("Api Response")
            .request("Api Request")
            .build();
    calls.add(callHistory);
    return Mono.just(
        CallHistoryResponse.builder()
            .callHistories(calls)
            .currentPage(0)
            .pageSize(1)
            .totalCount(1)
            .totalPages(1)
            .build());
  }

  public static CallHistoryEntity buildCallHistoryEntity() {
    return CallHistoryEntity.builder()
        .date(LocalDateTime.now().toString())
        .endpoint("/api/v1/percent")
        .response("Api Response")
        .request("Api Request")
        .build();
  }

  public static Mono<PageImpl<CallHistoryEntity>> buildPageCallHistoryEntity() {
    List<CallHistoryEntity> callHistoryList = new ArrayList<>();
    callHistoryList.add(buildCallHistoryEntity());
    PageImpl<CallHistoryEntity> callHistoryEntityPage = new PageImpl<>(callHistoryList);
    return Mono.just(callHistoryEntityPage);
  }

  public static CallHistory buildCallHistory() {
    return CallHistory.builder()
        .date(LocalDateTime.now().toString())
        .endpoint("/api/v1/percent")
        .response("Api Response")
        .request("Api Request")
        .build();
  }
}
