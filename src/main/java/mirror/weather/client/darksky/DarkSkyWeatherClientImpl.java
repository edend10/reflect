package mirror.weather.client.darksky;


import com.fasterxml.jackson.databind.ObjectMapper;
import mirror.weather.client.generic.WeatherClient;
import mirror.weather.model.darksky.DarkSkyWeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class DarkSkyWeatherClientImpl implements WeatherClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DarkSkyWeatherClientImpl.class);

    private static final String HOST = "https://api.darksky.net";
    private static final String ENDPOINT = "forecast";
    private static final String PARAMS = "exclude=flags,minutely&units=si";
    private static final String URL_PATTERN = "%s/%s/%s/%s?%s";

    private final RestTemplate restTemplate;
    private final String apiKey;

    public DarkSkyWeatherClientImpl(RestTemplate restTemplate, String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public DarkSkyWeatherResponse getForecastForLatLong(String latLong) {
        String url = buildUrl(latLong);
        ResponseEntity<DarkSkyWeatherResponse> jsonResponse = restTemplate.getForEntity(url, DarkSkyWeatherResponse.class);
        if (jsonResponse.getStatusCode() == HttpStatus.OK) {
            return jsonResponse.getBody();
        } else {
            LOGGER.warn("Weather forecast http call failed with status {}: {}",
                    jsonResponse.getStatusCode().value(), jsonResponse.getStatusCode().getReasonPhrase());

            return new DarkSkyWeatherResponse();
        }
    }

    private String buildUrl(String latLong) {
        return String.format(URL_PATTERN, HOST, ENDPOINT, apiKey, latLong, PARAMS);
    }
}
