package bd.com.penguin.covidTrackerRest.service;

import bd.com.penguin.covidTrackerRest.document.CovidInfo;
import bd.com.penguin.covidTrackerRest.document.DailyCovidInfo;
import bd.com.penguin.covidTrackerRest.repository.DailyCovidRepository;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bd.com.penguin.covidTrackerRest.utils.DailyCovidUtils.DATE_FORMATTER_MM_DD_YYYY;
import static java.time.LocalDate.parse;
import static java.util.Optional.*;

/**
 * @author bashir
 * @since 26/3/20.
 */
@Component
public class CsvImportService {

    private static final Pattern FILE_NAME_PATTERN = Pattern.compile("^(\\d\\d-\\d\\d-\\d\\d\\d\\d)\\.csv$");

    private final DailyCovidRepository dailyCovidRepository;

    @Value("${csv.file.dir}")
    private String fileDir;

    public CsvImportService(DailyCovidRepository dailyCovidRepository) {
        this.dailyCovidRepository = dailyCovidRepository;
    }

    @Scheduled(fixedRate = 1800000)
    public void loadCsv() {
        File dir = new File(fileDir);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".csv"));
        ofNullable(files)
                .ifPresent(fList -> Arrays.stream(fList).forEach(this::mapFile));
    }

    private void mapFile(File file) {
        getFileDateFromName(file)
                .map(fileDate -> parseDailyCovidInfo(fileDate, file))
                .ifPresent(d -> {
                    dailyCovidRepository.findById(d.getDate())
                            .switchIfEmpty(dailyCovidRepository.insert(d)).subscribe();
                });
    }

    private DailyCovidInfo parseDailyCovidInfo(LocalDate fileDataDate, File file) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();

        Map<String, CovidInfo> regionCovidInfoMap = new HashMap<>();
        try {
            mapper.readerFor(CovidInfo.class).with(schema)
                    .readValues(file)
                    .forEachRemaining(o -> parseAndPutInMap(regionCovidInfoMap, o));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return new DailyCovidInfo(fileDataDate, regionCovidInfoMap);
    }

    private Optional<LocalDate> getFileDateFromName(File file) {
        Matcher matcher = FILE_NAME_PATTERN.matcher(file.getName());
        return matcher.matches() ? of(parse(matcher.group(1), DATE_FORMATTER_MM_DD_YYYY)) : empty();
    }

    private void parseAndPutInMap(Map<String, CovidInfo> map, Object covidInfo) {
        CovidInfo i = (CovidInfo) covidInfo;
        String key = join(i.getCountryOrRegion().trim(), i.getProvinceOrState().trim()).trim()
                .replaceAll("[.,\\s]", "")
                .toUpperCase();

        map.put(key, i);
    }

    private String join(String... args) {
        Objects.requireNonNull(args);

        StringBuilder sb = new StringBuilder();

        for (String arg : args) {
            sb.append(arg);
            sb.append("_");
        }

        return sb.substring(0, sb.length() - 2);
    }
}