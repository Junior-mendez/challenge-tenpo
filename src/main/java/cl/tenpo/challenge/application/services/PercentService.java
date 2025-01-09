package cl.tenpo.challenge.application.services;

import cl.tenpo.challenge.adapters.clients.dtos.PercentClientResponse;
import cl.tenpo.challenge.application.ports.input.PercentInputPort;
import cl.tenpo.challenge.application.ports.output.PercentOutputPort;
import cl.tenpo.challenge.adapters.repository.entities.CallHistory;
import cl.tenpo.challenge.adapters.repository.CallHistoryRepository;
import cl.tenpo.challenge.adapters.redis.PercentageRedis;
import cl.tenpo.challenge.adapters.redis.PercentRedisRepository;
import cl.tenpo.challenge.adapters.dtos.PercentRequest;
import cl.tenpo.challenge.adapters.dtos.PercentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PercentService implements PercentInputPort {

    private final PercentOutputPort percentOutputPort;

    private final PercentRedisRepository percentRedisRepository;

    private final CallHistoryRepository callHistoryRepository;

    @Override
    public Mono<PercentResponse> calculatePercent(PercentRequest percentRequest) {
        return this.percentRedisRepository.get()
                .flatMap(percentRedis -> {
                    LocalDateTime dateTime = LocalDateTime.parse(percentRedis.getDate());
                    return LocalDateTime.now().isAfter(dateTime.plusMinutes(30)) ?
                        this.percentOutputPort.getPercent()
                                .flatMap(percent->
                                        this.percentRedisRepository.save(PercentageRedis.builder()
                                                        .percent(percent.getPercent()).date(LocalDateTime.now().toString()).build())
                                                .flatMap(success->{
                                                    if(Boolean.FALSE.equals(success)){
                                                        Mono.error(new Exception());
                                                    }
                                                    return Mono.just(PercentageRedis.builder().percent(percent.getPercent()).date(LocalDateTime.now().toString()).build());
                                                })).switchIfEmpty(Mono.just(percentRedis)) :  Mono.just(percentRedis);

                })
                .switchIfEmpty(this.percentOutputPort.getPercent()
                        .switchIfEmpty(Mono.error(new Exception()))
                        .flatMap(percent->
                                this.percentRedisRepository.save(PercentageRedis.builder()
                                                .percent(percent.getPercent()).date(LocalDateTime.now().toString()).build())
                                        .flatMap(success->{
                                            if(Boolean.FALSE.equals(success)){
                                                Mono.error(new Exception());
                                            }
                                            return Mono.just(PercentageRedis.builder().percent(percent.getPercent()).date(LocalDateTime.now().toString()).build());

                                        })))
                .flatMap(percentRedis -> {
                    double number = (percentRequest.getFirstNumber()+percentRequest.getSecondNumber() )* (100+percentRedis.getPercent())/100;
                    return Mono.just(PercentResponse.builder().result(number).build());
                });
    }
}
