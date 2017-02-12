package mirror.weather.rest;

import mirror.weather.model.response.HourlyWeather;
import mirror.weather.model.response.WeatherResponse;
import mirror.weather.service.WeatherService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @RequestMapping(value = "/weather", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public WeatherResponse getWeather() {
        return weatherService.getWeather();
    }

    @MessageMapping("/connectSocket")
    @SendTo("/topic/weather")
    public WeatherResponse weather() {
        return weatherService.getWeather();
    }
}
