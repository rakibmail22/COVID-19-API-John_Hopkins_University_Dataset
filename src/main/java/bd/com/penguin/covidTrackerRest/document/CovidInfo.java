package bd.com.penguin.covidTrackerRest.document;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author bashir
 * @since 27/3/20.
 */
@Data
public class CovidInfo implements Serializable {

    @JsonProperty("FIPS")
    String fips;

    @JsonProperty("Admin2")
    String admin2;

    @JsonAlias("Province/State")
    @JsonProperty("Province_State")
    String provinceOrState;

    @JsonAlias("Country/Region")
    @JsonProperty("Country_Region")
    String countryOrRegion;

    @JsonAlias("Last Update")
    @JsonProperty("Last_Update")
    String lastUpdated;

    @JsonAlias("Latitude")
    @JsonProperty("Lat")
    String lat;

    @JsonAlias("Longitude")
    @JsonProperty("Long_")
    String longitude;

    @JsonProperty("Confirmed")
    int confirmed;

    @JsonProperty("Deaths")
    int deaths;

    @JsonProperty("Recovered")
    int recovered;

    @JsonProperty("Active")
    int active;

    @JsonProperty("Combined_Key")
    String combinedKey;
}
