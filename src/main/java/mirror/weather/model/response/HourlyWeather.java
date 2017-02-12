package mirror.weather.model.response;

import java.util.Objects;

public class HourlyWeather {
    private long time;
    private String hourAsString;
    private int  temperature;
    private String summary;
    private int apparentTemperature;
    private String icon;

    public String getHourAsString() {
        return hourAsString;
    }

    public void setHourAsString(String hourAsString) {
        this.hourAsString = hourAsString;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(int apparentTemperature) {
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
        HourlyWeather weatherObj = (HourlyWeather) obj;
        return Objects.equals(this.time, weatherObj.time)
                && Objects.equals(this.temperature, weatherObj.temperature)
                && Objects.equals(this.apparentTemperature, weatherObj.apparentTemperature)
                && Objects.equals(this.summary, weatherObj.summary)
                && Objects.equals(this.icon, weatherObj.icon);
    }
}
