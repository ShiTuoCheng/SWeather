package com.shituocheng.sweather.com.sweather.bean;

/**
 * Created by shituocheng on 2017/2/23.
 */

public class Weather {

    private String city;

    private String weatherCode;

    private String weatherState;

    private String country;

    private String pressure;

    private String temperature;

    private String windType;

    private String feelingTemp;

    private String weatherUpdate;

    private Weather(Builder builder) {
        setCity(builder.city);
        setWeatherCode(builder.weatherCode);
        setWeatherState(builder.weatherState);
        setCountry(builder.country);
        setPressure(builder.pressure);
        setTemperature(builder.temperature);
        setWindType(builder.windType);
        setFeelingTemp(builder.feelingTemp);
        setWeatherUpdate(builder.weatherUpdate);
    }

    public String getWeatherUpdate() {
        return weatherUpdate;
    }

    public void setWeatherUpdate(String weaatherUpdate) {
        this.weatherUpdate = weaatherUpdate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getWeatherState() {
        return weatherState;
    }

    public void setWeatherState(String weatherState) {
        this.weatherState = weatherState;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWindType() {
        return windType;
    }

    public void setWindType(String windType) {
        this.windType = windType;
    }

    public String getFeelingTemp() {
        return feelingTemp;
    }

    public void setFeelingTemp(String feelingTemp) {
        this.feelingTemp = feelingTemp;
    }


    public static final class Builder {
        private String city;
        private String weatherCode;
        private String weatherState;
        private String country;
        private String pressure;
        private String temperature;
        private String windType;
        private String feelingTemp;
        private String weatherUpdate;

        public Builder() {
        }

        public Builder city(String val) {
            city = val;
            return this;
        }

        public Builder weatherCode(String val) {
            weatherCode = val;
            return this;
        }

        public Builder weatherState(String val) {
            weatherState = val;
            return this;
        }

        public Builder country(String val) {
            country = val;
            return this;
        }

        public Builder pressure(String val) {
            pressure = val;
            return this;
        }

        public Builder temperature(String val) {
            temperature = val;
            return this;
        }

        public Builder windType(String val) {
            windType = val;
            return this;
        }

        public Builder feelingTemp(String val) {
            feelingTemp = val;
            return this;
        }

        public Builder weatherUpdate(String val) {
            weatherUpdate = val;
            return this;
        }

        public Weather build() {
            return new Weather(this);
        }
    }
}
