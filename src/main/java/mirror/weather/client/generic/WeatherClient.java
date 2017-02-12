package mirror.weather.client.generic;

import mirror.weather.model.generic.ApiWeatherResponse;

public interface WeatherClient {
    public ApiWeatherResponse getForecastForLatLong(String latLong);
}
