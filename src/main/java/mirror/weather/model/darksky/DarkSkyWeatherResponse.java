package mirror.weather.model.darksky;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import mirror.weather.model.generic.ApiWeatherResponse;
import mirror.weather.model.response.WeatherResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DarkSkyWeatherResponse implements ApiWeatherResponse {

    @JsonProperty(value = "currently")
    private DarkSkyHourlyWeather currentWeather;

    @JsonProperty(value = "hourly")
    private DarkSkyHourlyWrapper hourlyWrapper;

    @JsonProperty(value = "daily")
    private DarkSkyDailyWrapper dailyWrapper;

    public DarkSkyDailyWrapper getDailyWrapper() {
        return dailyWrapper;
    }

    public void setDailyWrapper(DarkSkyDailyWrapper dailyWrapper) {
        this.dailyWrapper = dailyWrapper;
    }

    public DarkSkyHourlyWrapper getHourlyWrapper() {
        return hourlyWrapper;
    }

    public void setHourlyWrapper(DarkSkyHourlyWrapper hourlyWrapper) {
        this.hourlyWrapper = hourlyWrapper;
    }


    public DarkSkyHourlyWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(DarkSkyHourlyWeather currentWeather) {
        this.currentWeather = currentWeather;
    }
}
