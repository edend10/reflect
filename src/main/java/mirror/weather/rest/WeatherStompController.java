package mirror.weather.rest;

import mirror.weather.model.response.HourlyWeather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeatherStompController {
    //@MessageMapping("/connectSocket")
    //@SendTo("/topic/weather")
    public HourlyWeather weather(String message) {
        HourlyWeather weather = new HourlyWeather();
        weather.setTime(1212121);
        weather.setIcon("nice");
        weather.setTemperature(1212);
        weather.setApparentTemperature(232);
        weather.setSummary("hellooo");

        return weather;
    }

    @Autowired
    private SimpMessagingTemplate webSocket;

    @RequestMapping(value = "/gooo", method = RequestMethod.GET)
    @ResponseBody
    public String getWeather() {
        webSocket.convertAndSend("/topic/weather", "blaaa");
        return "ok!";
    }

    @RequestMapping(value = "/gooo2", method = RequestMethod.GET)
    @ResponseBody
    public String getWeather2() {
        webSocket.convertAndSend("/mirror/connectSocket", "blaaa2");
        return "ok!";
    }

    private class Temp {
        String summary;
        Temp() {}
        Temp(String summary) {
            this.summary = summary;
        }
        public String getTime() { return this.summary; };
        public void setTime(String time) { this.summary= time; }
    }
}
