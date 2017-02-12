package mirror.weather.model.darksky;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DarkSkyDailyWrapper {
    @JsonProperty(value = "data")
    private List<DarkSkyDailyWeather> dailyWeatherList;

    public List<DarkSkyDailyWeather> getDailyWeatherList() {
        return dailyWeatherList;
    }

    public void setDailyWeatherList(List<DarkSkyDailyWeather> dailyWeatherList) {
        this.dailyWeatherList = dailyWeatherList;
    }
}
