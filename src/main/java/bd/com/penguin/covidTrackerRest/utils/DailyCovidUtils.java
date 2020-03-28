package bd.com.penguin.covidTrackerRest.utils;

import bd.com.penguin.covidTrackerRest.document.CovidInfo;
import bd.com.penguin.covidTrackerRest.document.DailyCovidInfo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

import static java.util.Collections.*;

/**
 * @author bashir
 * @since 25/3/20.
 */
public class DailyCovidUtils {

    public static final LocalDate DUMMY_DATA_ID = LocalDate.of(3000, 01, 01);
    public static final int DUMMY_DATA_CONFIRMED = 111111111;
    public static final int DUMMY_DATA_ACTIVE = 2222222;
    public static final int DUMMY_DATA_DEATHS = 1;
    public static final String DUMMY_REGION = "REGION";


    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATE_FORMATTER_MM_DD_YYYY = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    public static DailyCovidInfo createDummy() {
        CovidInfo covidInfo = new CovidInfo();

        covidInfo.setConfirmed(DUMMY_DATA_CONFIRMED);
        covidInfo.setActive(DUMMY_DATA_ACTIVE);
        covidInfo.setDeaths(DUMMY_DATA_DEATHS);

        return new DailyCovidInfo(DUMMY_DATA_ID, singletonMap(DUMMY_REGION, covidInfo));
    }
}
