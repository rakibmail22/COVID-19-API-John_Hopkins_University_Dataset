package bd.com.penguin.covidTrackerRest.repository;

import bd.com.penguin.covidTrackerRest.document.DailyCovidInfo;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

/**
 * @author bashir
 * @since 25/3/20.
 */
public interface DailyCovidRepository extends ReactiveMongoRepository<DailyCovidInfo, LocalDate> {

    @Query("{'date' : {$gte: ?0, $lte: ?1}}")
    Flux<DailyCovidInfo> findByDateRange(LocalDate from, LocalDate to);
}
