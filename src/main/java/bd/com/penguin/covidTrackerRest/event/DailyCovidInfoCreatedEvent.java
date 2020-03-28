package bd.com.penguin.covidTrackerRest.event;

import bd.com.penguin.covidTrackerRest.document.DailyCovidInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author bashir
 * @since 26/3/20.
 */
public class DailyCovidInfoCreatedEvent extends ApplicationEvent {

    public DailyCovidInfoCreatedEvent(DailyCovidInfo source) {
        super(source);

        System.out.println("Successfully Saved {}" + source);
    }
}