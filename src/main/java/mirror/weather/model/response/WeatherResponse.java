package mirror.weather.model.response;

import java.util.List;

public class WeatherResponse {
    private HourlyWeather currentWeather;
    private List<HourlyWeather> hourlyWeatherList;
    private List<DailyWeather> dailyWeatherList;

    public HourlyWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(HourlyWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public List<HourlyWeather> getHourlyWeatherList() {
        return hourlyWeatherList;
    }

    public void setHourlyWeatherList(List<HourlyWeather> hourlyWeatherList) {
        this.hourlyWeatherList = hourlyWeatherList;
    }

    public List<DailyWeather> getDailyWeatherList() {
        return dailyWeatherList;
    }

    public void setDailyWeatherList(List<DailyWeather> dailyWeatherList) {
        this.dailyWeatherList = dailyWeatherList;
    }
}
