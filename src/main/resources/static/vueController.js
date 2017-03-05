var stompClient;

Vue.component("weather-icon", {
    props: ['icon_size'],
    template: '<canvas class="weather-icons" :width="icon_size" :height="icon_size"></canvas>'
});

var WEATHER_NUM_OF_HOURS_TO_DISPLAY = 5;

var app = new Vue({
  el: '#app',
  data: {
    connected: false,
    weather: {},
    currentWeather: {},
    hourlyWeatherList: [],
    dailyWeatherList: [],
    bathroomState: "NA",
    stompClient: stompClient,
    idToSkyIcon: {
                    "clear-day": Skycons.CLEAR_DAY,
                    "clear-night": Skycons.CLEAR_NIGHT,
                    "partly-cloudy-day": Skycons.PARTLY_CLOUDY_DAY,
                    "partly-cloudy-night": Skycons.PARTLY_CLOUDY_NIGHT,
                    "cloudy": Skycons.CLOUDY,
                    "rain": Skycons.RAIN,
                    "sleet": Skycons.SLEET,
                    "snow": Skycons.SNOW,
                    "wind": Skycons.WIND,
                    "fog": Skycons.FOG
    }
  },
  methods: {
    setConnected: function(connected) {
        this.connected = connected;
    },
    connect: function() {
        var socket = new SockJS('/mirror-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            app.setConnected(true);
            console.log('WebSocket connected: ' + frame);
            stompClient.subscribe('/topic/weather/get', function (weatherResponse) {
                app.weather = JSON.parse(weatherResponse.body);
                app.currentWeather = app.weather.currentWeather;
                app.hourlyWeatherList = app.weather.hourlyWeatherList.splice(1, WEATHER_NUM_OF_HOURS_TO_DISPLAY);
                app.dailyWeatherList = app.weather.dailyWeatherList;
                setTimeout(function () {
                    app.resetWeatherIcons();
                }, 100);
            });
            stompClient.subscribe('/topic/weather/hidden', function (visibilityResponse) {
                visibility = JSON.parse(visibilityResponse.body);
                $("#weather-div").attr("hidden", visibility.hidden)
            });
            stompClient.subscribe('/topic/bathroom/get', function (bathroomResponse) {
                            app.bathroomState = JSON.parse(bathroomResponse.body).state;
                            setTimeout(function () {
                                app.resetWeatherIcons();
                            }, 100);
            });
            app.askForWeather();
            app.askForBathroomState();
        });
    },
    disconnect: function() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        this.setConnected(false);
        console.log("WebSocket disconnected");
    },
    resetWeatherIcons: function() {
        var currentIconId;
        $(".weather-icons").delay(10).each(function(idx) {
            currentIconId = $(this).attr("id");
            app.setIcon(currentIconId);
        });
    },
    setIcon: function(iconId) {
        var realId = iconId.substring(2).replace(/[0-9]/g, '');
        icons.set(iconId, this.idToSkyIcon[realId]);
    },
    askForWeather: function() {
        stompClient.send("/mirror/weather-message", {}, {})
    },
    askForBathroomState: function() {
       stompClient.send("/mirror/bathroom-message", {}, {})
    },
    bathroomSignalUrgent: function(event) {
        if (event) {
            $.get("/bathroom/signal-urgent", function(data) {

            });
        }
    }
  }
});

var icons = new Skycons({"color": "white"});
icons.play();
app.connect();