package cl.tenpo.challenge.adapters.web;

import cl.tenpo.challenge.adapters.dtos.CallHistoryResponse;
import cl.tenpo.challenge.application.ports.input.CallHistoryInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/log")
@RequiredArgsConstructor
@RestController
@Tag(name = "Calls History", description = "Endpoints to retrieves call history")
public class CallHistoryController {

  private final CallHistoryInputPort callHistoryInputPort;

  @Operation(
      summary = "Retrieves all call history",
      description = "Retrieves call history list with pagination")
  @ApiResponse(responseCode = "200", description = "Calls retrieved successfully")
  @GetMapping
  public Mono<CallHistoryResponse> getCallHistory(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    return callHistoryInputPort.getCallHistoryPaged(page, size);
  }
}
