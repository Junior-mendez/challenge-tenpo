package cl.tenpo.challenge.adapters.web;

import cl.tenpo.challenge.adapters.repository.CallHistoryRepository;
import cl.tenpo.challenge.application.ports.input.CallHistoryInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/log")
@RequiredArgsConstructor
@RestController
public class CallHistoryController {

    private final CallHistoryInputPort callHistoryInputPort;

    @GetMapping
    public Mono<?> getLog(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return callHistoryInputPort.getCallHistoryPaged(page, size);
    }
}
