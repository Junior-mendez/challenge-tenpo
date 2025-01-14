package cl.tenpo.challenge.adapters.dtos;

import cl.tenpo.challenge.domain.models.CallHistory;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CallHistoryResponse {

  private List<CallHistory> callHistories;
  private int currentPage;
  private long totalCount;
  private int pageSize;
  private int totalPages;
}
