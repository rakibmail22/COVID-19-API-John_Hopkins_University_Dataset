package bd.com.penguin.covidTrackerRest.handler;

import bd.com.penguin.covidTrackerRest.document.DailyCovidInfo;
import bd.com.penguin.covidTrackerRest.repository.DailyCovidRepository;
import bd.com.penguin.covidTrackerRest.service.DailyCovidService;
import bd.com.penguin.covidTrackerRest.utils.DailyCovidUtils;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author bashir
 * @since 26/3/20.
 */
@RequiredArgsConstructor
@Component
public class DailyCovidHandler {

    private final DailyCovidService dailyCovidService;

    public Mono<ServerResponse> getByDate(ServerRequest r) {
        LocalDate date = LocalDate.parse(r.pathVariable("date"), DailyCovidUtils.dateFormatter);
        return defaultReadResponse(dailyCovidService.findByDate(date));
    }

    public Mono<ServerResponse> getByRegionAndDateRange(ServerRequest r) {
        LocalDate from = LocalDate.parse(r.pathVariable("from"), DailyCovidUtils.dateFormatter);
        LocalDate to = LocalDate.parse(r.pathVariable("to"), DailyCovidUtils.dateFormatter);
        String region = r.pathVariable("region").toUpperCase();

        return defaultReadResponse(dailyCovidService.findByRegionAndDateRange(region, from, to));
    }

    public Mono<ServerResponse> getAll(ServerRequest r) {
        return defaultReadResponse(dailyCovidService.all());
    }

    private Mono<ServerResponse> defaultReadResponse(Publisher<DailyCovidInfo> covidInfo) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(covidInfo, DailyCovidInfo.class);
    }

}
