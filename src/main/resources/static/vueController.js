var stompClient;

var app = new Vue({
  el: '#app',
  data: {
    connected: false,
    weather: {},
    hourlyWeather: [],
    stompClient: stompClient
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
            stompClient.subscribe('/topic/weather', function (weatherResponse) {
                app.weather = JSON.parse(weatherResponse.body);
                app.hourlyWeather = app.weather.hourlyWeatherList.splice(1, 7);
            });
        });
    },
    disconnect: function() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        this.setConnected(false);
        console.log("WebSocket disconnected");
    }
  }
});

Vue.component("sleet", {
    props: ['icon-name'],
    template: '<canvas id="icon-name" width="64" height="64"></canvas>'
});

var icons = new Skycons({"color": "orange"});
icons.set("clear-day", Skycons.CLEAR_DAY);
icons.set("clear-night", Skycons.CLEAR_NIGHT);
icons.set("partly-cloudy-day", Skycons.PARTLY_CLOUDY_DAY);
icons.set("partly-cloudy-night", Skycons.PARTLY_CLOUDY_NIGHT);
icons.set("cloudy", Skycons.CLOUDY);
icons.set("rain", Skycons.RAIN);
icons.set("sleet", Skycons.SLEET);
icons.set("snow", Skycons.SNOW);
icons.set("wind", Skycons.WIND);
icons.set("fog", Skycons.FOG);
icons.play();

app.connect();