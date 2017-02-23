package mirror.weather.service;

import mirror.service.AbstractMirrorService;
import mirror.weather.client.generic.WeatherClient;
import mirror.weather.model.generic.ApiWeatherResponse;
import mirror.weather.model.response.WeatherResponse;
import mirror.weather.service.generic.WeatherResponseConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherService extends AbstractMirrorService {

    public final static String HOUR_FORMAT = "h a";
    public static final String DAY_FORMAT = "EEE";

    private final WeatherClient weatherClient;
    private final WeatherResponseConverter<ApiWeatherResponse> weatherResponseConverter;
    private final Logger LOGGER = LoggerFactory.getLogger(WeatherService.class);
    private final String latLong;

    public WeatherService(WeatherClient weatherClient, WeatherResponseConverter<ApiWeatherResponse> weatherResponseConverter, String latLong) {
        this.weatherClient = weatherClient;
        this.weatherResponseConverter = weatherResponseConverter;
        this.latLong = latLong;
    }

    public WeatherResponse getWeather() {
        LOGGER.info("Getting weather for latlong");
        ApiWeatherResponse apiWeatherResponse = weatherClient.getForecastForLatLong(latLong);
        WeatherResponse weatherResponse = weatherResponseConverter.toWeatherResponse(apiWeatherResponse);
        return weatherResponse;
    }
}
