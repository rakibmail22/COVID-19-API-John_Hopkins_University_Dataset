package bd.com.penguin.covidTrackerRest.service;

import bd.com.penguin.covidTrackerRest.document.DailyCovidInfo;
import bd.com.penguin.covidTrackerRest.event.DailyCovidInfoCreatedEvent;
import bd.com.penguin.covidTrackerRest.repository.DailyCovidRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collections;

/**
 * @author bashir
 * @since 26/3/20.
 */
@AllArgsConstructor
@Service
public class DailyCovidService {

    private final ApplicationEventPublisher publisher;

    private final DailyCovidRepository dailyCovidRepository;

    public Flux<DailyCovidInfo> all() {
        return dailyCovidRepository.findAll();
    }

    public Mono<DailyCovidInfo> findByDate(LocalDate date) {
        return dailyCovidRepository.findById(date);
    }

    public Flux<DailyCovidInfo> findByDateRange(LocalDate from, LocalDate to) {
        return dailyCovidRepository.findByDateRange(from, to);
    }

    public Flux<DailyCovidInfo> findByRegionAndDateRange(String region, LocalDate from, LocalDate to) {
        return dailyCovidRepository.findByDateRange(from, to)
                .map(d -> {
                    d.setCovidInfoMap(Collections.singletonMap(region, d.getCovidInfoMap().get(region)));
                    return d;
                });
    }

    public Mono<DailyCovidInfo> delete(LocalDate date) {
        return dailyCovidRepository.findById(date)
                .flatMap(covidInfo -> dailyCovidRepository.delete(covidInfo).thenReturn(covidInfo));
    }

    public Mono<DailyCovidInfo> create(DailyCovidInfo covidInfo) {
        return dailyCovidRepository.save(covidInfo)
                .doOnSuccess(info -> publisher.publishEvent(new DailyCovidInfoCreatedEvent(info)));
    }

}