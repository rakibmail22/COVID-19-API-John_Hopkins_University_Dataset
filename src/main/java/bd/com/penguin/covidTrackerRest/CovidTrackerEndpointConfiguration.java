package bd.com.penguin.covidTrackerRest;

import bd.com.penguin.covidTrackerRest.handler.DailyCovidHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.PathContainer;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author bashir
 * @since 26/3/20.
 */
@Configuration
public class CovidTrackerEndpointConfiguration {

    @Bean
    public RouterFunction<ServerResponse> routes(DailyCovidHandler handler) {
        return route(i(GET("/covid-info/all")), handler::getAll)
                .andRoute(i(GET("/covid-info/{date}")), handler::getByDate)
                .andRoute(i(GET("/covid-info/{region}/{from}/{to}")), handler::getByRegionAndDateRange);
    }

    private static RequestPredicate i(RequestPredicate target) {
        return new CaseInsensitiveRequestPredicate(target);
    }
}

class CaseInsensitiveRequestPredicate implements RequestPredicate {

    private final RequestPredicate target;

    CaseInsensitiveRequestPredicate(RequestPredicate target) {
        this.target = target;
    }

    @Override
    public boolean test(ServerRequest request) {
        return this.target.test(new LowerCaseUriServerRequestWrapper(request));
    }

    @Override
    public String toString() {
        return this.target.toString();
    }
}


class LowerCaseUriServerRequestWrapper extends ServerRequestWrapper {

    LowerCaseUriServerRequestWrapper(ServerRequest delegate) {
        super(delegate);
    }

    @Override
    public URI uri() {
        return URI.create(super.uri().toString().toLowerCase());
    }

    @Override
    public String path() {
        return uri().getRawPath();
    }

    @Override
    public PathContainer pathContainer() {
        return PathContainer.parsePath(path());
    }
}
