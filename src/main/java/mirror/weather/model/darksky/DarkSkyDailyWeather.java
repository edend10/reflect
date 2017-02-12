package mirror.weather.model.darksky;

import java.util.Objects;

public class DarkSkyDailyWeather {
    private long time;
    private String summary;
    private String icon;
    private float temperatureMin;
    private float temperatureMax;

    public float getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(float temperatureMax) {
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

    public float getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(float temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, temperatureMin, temperatureMax, summary, icon);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!this.getClass().equals(obj.getClass())) return false;
        DarkSkyDailyWeather weatherObj = (DarkSkyDailyWeather) obj;
        return Objects.equals(this.time, weatherObj.time)
                && Objects.equals(this.temperatureMin, weatherObj.temperatureMin)
                && Objects.equals(this.temperatureMax, weatherObj.temperatureMax)
                && Objects.equals(this.summary, weatherObj.summary)
                && Objects.equals(this.icon, weatherObj.icon);
    }
}
