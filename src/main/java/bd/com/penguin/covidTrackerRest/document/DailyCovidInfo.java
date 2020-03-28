package bd.com.penguin.covidTrackerRest.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

/**
 * @author bashir
 * @since 25/3/20.
 */
@AllArgsConstructor
@NoArgsConstructor
@Document
@Data
public class DailyCovidInfo implements Serializable {

    @Id
    private LocalDate date;

    private Map<String, CovidInfo> covidInfoMap;
}
