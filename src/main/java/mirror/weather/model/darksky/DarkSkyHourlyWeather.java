package mirror.weather.model.darksky;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DarkSkyHourlyWeather {
    private long time;
    private float temperature;
    private String summary;
    private float apparentTemperature;

    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(float apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
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

    @Override
    public int hashCode() {
        return Objects.hash(time, temperature, apparentTemperature, summary, icon);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!this.getClass().equals(obj.getClass())) return false;
        DarkSkyHourlyWeather weatherObj = (DarkSkyHourlyWeather) obj;
        return Objects.equals(this.time, weatherObj.time)
                && Objects.equals(this.temperature, weatherObj.temperature)
                && Objects.equals(this.apparentTemperature, weatherObj.apparentTemperature)
                && Objects.equals(this.summary, weatherObj.summary)
                && Objects.equals(this.icon, weatherObj.icon);
    }
}
