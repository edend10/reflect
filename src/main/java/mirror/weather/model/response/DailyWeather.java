package mirror.weather.model.response;

import java.util.Objects;

public class DailyWeather {
    private long time;
    private String dayAsString;
    private String summary;
    private String icon;
    private int temperatureMin;
    private int temperatureMax;

    public int getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(int temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(int temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public String getDayAsString() {
        return dayAsString;
    }

    public void setDayAsString(String dayAsString) {
        this.dayAsString = dayAsString;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, temperatureMin, temperatureMax, summary, icon, dayAsString);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!this.getClass().equals(obj.getClass())) return false;
        DailyWeather weatherObj = (DailyWeather) obj;
        return Objects.equals(this.time, weatherObj.time)
                && Objects.equals(this.temperatureMin, weatherObj.temperatureMin)
                && Objects.equals(this.temperatureMax, weatherObj.temperatureMax)
                && Objects.equals(this.summary, weatherObj.summary)
                && Objects.equals(this.icon, weatherObj.icon)
                && Objects.equals(this.dayAsString, weatherObj.dayAsString);
    }
}
