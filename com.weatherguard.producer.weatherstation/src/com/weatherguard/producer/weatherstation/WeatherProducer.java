package com.weatherguard.producer.weatherstation;

public interface WeatherProducer {
    String getWeatherData();
    void setCity(String city);
    String[] getAvailableCities();
    String getAllCitiesWeatherData();
}
