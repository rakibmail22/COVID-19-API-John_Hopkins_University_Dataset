package bd.com.penguin.covidTrackerRest;

import bd.com.penguin.covidTrackerRest.document.DailyCovidInfo;
import bd.com.penguin.covidTrackerRest.service.DailyCovidService;
import bd.com.penguin.covidTrackerRest.utils.DailyCovidUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author bashir
 * @since 26/3/20.
 */
@DataMongoTest
@Import(DailyCovidService.class)
public class DailyCovidServiceTest {

    private final DailyCovidService dailyCovidService;

    public DailyCovidServiceTest(@Autowired DailyCovidService dailyCovidService) {
        this.dailyCovidService = dailyCovidService;
    }

    @Test
    public void save() {
        dailyCovidService.create(DailyCovidUtils.createDummy()).subscribe();
        Mono<DailyCovidInfo> dailyCovidInfoMono = dailyCovidService.findByDate(DailyCovidUtils.DUMMY_DATA_ID);

        StepVerifier
                .create(dailyCovidInfoMono)
                .expectNextMatches(dailyCovidInfo -> DailyCovidUtils.DUMMY_DATA_ID.equals(dailyCovidInfo.getDate()))
                .verifyComplete();

        dailyCovidService.delete(DailyCovidUtils.DUMMY_DATA_ID).block();
    }
}
