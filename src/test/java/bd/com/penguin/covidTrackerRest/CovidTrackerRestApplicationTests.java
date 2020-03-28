package bd.com.penguin.covidTrackerRest;

import bd.com.penguin.covidTrackerRest.document.DailyCovidInfo;
import bd.com.penguin.covidTrackerRest.repository.DailyCovidRepository;
import bd.com.penguin.covidTrackerRest.utils.DailyCovidUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Collections;

import static bd.com.penguin.covidTrackerRest.utils.DailyCovidUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
class CovidTrackerRestApplicationTests {

    @Autowired
    private DailyCovidRepository repository;

    @Test
    void contextLoads() {
    }

    @Test
    public void givenValue_whenFindAllByValue_thenFindAccount() {
        repository.save(DailyCovidUtils.createDummy()).block();
        Flux<DailyCovidInfo> covidInfoFlux = repository.findAllById(Collections.singletonList(DailyCovidUtils.DUMMY_DATA_ID));

        StepVerifier
                .create(covidInfoFlux)
                .assertNext(covidInfo -> {
                    assertEquals(DUMMY_DATA_CONFIRMED, covidInfo.getCovidInfoMap().get(DUMMY_REGION).getConfirmed());
                    assertEquals(DUMMY_DATA_DEATHS, covidInfo.getCovidInfoMap().get(DUMMY_REGION).getDeaths());
                    assertEquals(DUMMY_DATA_ACTIVE, covidInfo.getCovidInfoMap().get(DUMMY_REGION).getActive());
                    assertNotNull(covidInfo.getDate());
                })
                .expectComplete()
                .verify();
        repository.delete(DailyCovidUtils.createDummy()).block();
    }

}
