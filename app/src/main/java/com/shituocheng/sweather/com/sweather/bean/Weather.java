package com.shituocheng.sweather.com.sweather.bean;

/**
 * Created by shituocheng on 2017/2/23.
 */

public class Weather {

    private String city;

    private int weatherCode;

    private String weatherState;

    private String country;

    private int pressure;

    private float temperature;

    private String windType;

    private int feelingTemp;

    private Weather(Builder builder) {
        city = builder.city;
        weatherCode = builder.weatherCode;
        weatherState = builder.weatherState;
        country = builder.country;
        pressure = builder.pressure;
        temperature = builder.temperature;
        windType = builder.windType;
        feelingTemp = builder.feelingTemp;
    }


    public static final class Builder {
        private String city;
        private int weatherCode;
        private String weatherState;
        private String country;
        private int pressure;
        private float temperature;
        private String windType;
        private int feelingTemp;

        public Builder() {
        }

        public Builder city(String val) {
            city = val;
            return this;
        }

        public Builder weatherCode(int val) {
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

        public Builder pressure(int val) {
            pressure = val;
            return this;
        }

        public Builder temperature(float val) {
            temperature = val;
            return this;
        }

        public Builder windType(String val) {
            windType = val;
            return this;
        }

        public Builder feelingTemp(int val) {
            feelingTemp = val;
            return this;
        }

        public Weather build() {
            return new Weather(this);
        }
    }
}
