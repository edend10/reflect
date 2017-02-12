package mirror.weather.service.generic;

import mirror.weather.model.generic.ApiWeatherResponse;
import mirror.weather.model.response.WeatherResponse;

public interface WeatherResponseConverter<T> {
    WeatherResponse toWeatherResponse(T apiWeatherResponse);
}
