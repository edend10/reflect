package mirror.weather.service.darksky;

import mirror.weather.model.darksky.DarkSkyDailyWeather;
import mirror.weather.model.darksky.DarkSkyHourlyWeather;
import mirror.weather.model.darksky.DarkSkyWeatherResponse;
import mirror.weather.model.response.DailyWeather;
import mirror.weather.model.response.HourlyWeather;
import mirror.weather.model.response.WeatherResponse;
import mirror.weather.service.WeatherService;
import mirror.weather.service.generic.WeatherResponseConverter;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class DarkSkyWeatherResponseConverterImpl implements WeatherResponseConverter<DarkSkyWeatherResponse> {

    private final String timezone;

    public DarkSkyWeatherResponseConverterImpl(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public WeatherResponse toWeatherResponse(DarkSkyWeatherResponse apiWeatherResponse) {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setCurrentWeather(toHourlyWeather(apiWeatherResponse.getCurrentWeather()));
        weatherResponse.setHourlyWeatherList(
                apiWeatherResponse.getHourlyWrapper().
                        getHourlyWeatherList()
                        .stream()
                        .map(this::toHourlyWeather)
                        .collect(Collectors.toList()));

        weatherResponse.setDailyWeatherList(
                apiWeatherResponse.getDailyWrapper().
                        getDailyWeatherList()
                        .stream()
                        .map(this::toDailyWeather)
                        .collect(Collectors.toList()));

        return weatherResponse;
    }

    private HourlyWeather toHourlyWeather(DarkSkyHourlyWeather darkSkyHourlyWeather) {
        HourlyWeather hourlyWeather = new HourlyWeather();
        hourlyWeather.setApparentTemperature(Math.round(darkSkyHourlyWeather.getApparentTemperature()));
        hourlyWeather.setIcon(darkSkyHourlyWeather.getIcon());
        hourlyWeather.setSummary(darkSkyHourlyWeather.getSummary());
        hourlyWeather.setTemperature(Math.round(darkSkyHourlyWeather.getTemperature()));
        hourlyWeather.setTime(darkSkyHourlyWeather.getTime());
        hourlyWeather.setHourAsString(toHourAsString(darkSkyHourlyWeather.getTime()));
        return hourlyWeather;
    }

    private DailyWeather toDailyWeather(DarkSkyDailyWeather darkSkyDailyWeather) {
        DailyWeather dailyWeather = new DailyWeather();
        dailyWeather.setTime(darkSkyDailyWeather.getTime());
        dailyWeather.setSummary(darkSkyDailyWeather.getSummary());
        dailyWeather.setIcon(darkSkyDailyWeather.getIcon());
        dailyWeather.setTemperatureMin(Math.round(darkSkyDailyWeather.getTemperatureMin()));
        dailyWeather.setTemperatureMax(Math.round(darkSkyDailyWeather.getTemperatureMax()));
        return dailyWeather;
    }

    private String toHourAsString(long timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(WeatherService.HOUR_FORMAT);
        ZonedDateTime zonedTime = ZonedDateTime.ofInstant(instant, ZoneId.of(timezone));

        return formatter.format(zonedTime);
    }
}
