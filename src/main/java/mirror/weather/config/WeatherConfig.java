package mirror.weather.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import mirror.weather.client.darksky.DarkSkyWeatherClientImpl;
import mirror.weather.client.generic.WeatherClient;
import mirror.weather.model.generic.ApiWeatherResponse;
import mirror.weather.rest.WeatherStompController;
import mirror.weather.service.WeatherService;
import mirror.weather.service.darksky.DarkSkyWeatherResponseConverterImpl;
import mirror.weather.service.generic.WeatherResponseConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import mirror.weather.rest.WeatherController;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration
@PropertySource("classpath:reflect.properties")
@PropertySource("classpath:reflect-key.properties")
public class WeatherConfig {
    @Value("${modules.timezone}")
    private String timezone;

    @Value("${modules.weather.latlong}")
    private String latLong;

    @Value("${weather.apikey}")
    private String weatherApiKey;

    @Bean
    public WeatherController weatherController(WeatherService weatherService) {
        return new WeatherController(weatherService);
    }

    @Bean
    public WeatherService weatherService(WeatherClient weatherClient,
                                         WeatherResponseConverter weatherResponseConverter) {
        return new WeatherService(weatherClient, weatherResponseConverter, latLong);
    }

    @Bean
    public WeatherClient weatherClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        return new DarkSkyWeatherClientImpl(restTemplate, objectMapper, weatherApiKey);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public WeatherResponseConverter weatherResponseConverter() {
        return new DarkSkyWeatherResponseConverterImpl(timezone);
    }
}
