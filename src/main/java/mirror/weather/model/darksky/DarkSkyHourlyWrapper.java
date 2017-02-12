package mirror.weather.model.darksky;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DarkSkyHourlyWrapper {
    @JsonProperty(value = "data")
    private List<DarkSkyHourlyWeather> hourlyWeatherList;

    public List<DarkSkyHourlyWeather> getHourlyWeatherList() {
        return hourlyWeatherList;
    }

    public void setHourlyWeatherList(List<DarkSkyHourlyWeather> hourlyWeatherList) {
        this.hourlyWeatherList = hourlyWeatherList;
    }
}
