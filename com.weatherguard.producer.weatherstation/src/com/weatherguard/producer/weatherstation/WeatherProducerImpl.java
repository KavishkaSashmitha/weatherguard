package com.weatherguard.producer.weatherstation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class WeatherProducerImpl implements WeatherProducer {
    private final Random random = new Random();
    private String currentCity = "Kandy"; // Default city

    // Available cities
    private static final String[] CITIES = {
        "Kandy", "Colombo", "Galle", "Sigiriya", "Nuwara Eliya", "Jaffna"
    };

    public WeatherProducerImpl() {
        // No scheduled updates
    }

    @Override
    public void setCity(String city) {
        for (String validCity : CITIES) {
            if (validCity.equalsIgnoreCase(city)) {
                this.currentCity = validCity;
                System.out.println("City set to: " + validCity);
                return;
            }
        }
        System.out.println("Invalid city: " + city + ". City remains as: " + currentCity);
    }

    @Override
    public String[] getAvailableCities() {
        return CITIES.clone();
    }

    @Override
    public String getAllCitiesWeatherData() {
        StringBuilder allData = new StringBuilder();
        
        allData.append("\n========== ALL CITIES WEATHER REPORT ==========\n");
        allData.append("Generated at: ")
               .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
               .append("\n\n");
        
        for (String city : CITIES) {
            // Generate random weather data for each city
            double temperature = 15.0 + random.nextDouble() * 20.0; // 15-35°C
            double rainfall = random.nextDouble() * 8.0; // 0-8mm
            double humidity = 30.0 + random.nextDouble() * 65.0; // 30-95%
            
            allData.append("--- ").append(city).append(" ---\n")
                   .append(" Temperature : ").append(String.format("%.1f°C", temperature)).append("\n")
                   .append(" Rainfall    : ").append(String.format("%.1f mm", rainfall)).append("\n")
                   .append(" Humidity    : ").append(String.format("%.1f%%", humidity)).append("\n\n");
        }
        
        allData.append("==============================================\n");
        
        return allData.toString();
    }

    private String generateWeatherData(String city) {
        double temperature = 15.0 + random.nextDouble() * 20.0; // 15-35°C
        double rainfall = random.nextDouble() * 8.0; // 0-8mm
        double humidity = 30.0 + random.nextDouble() * 65.0; // 30-95%

        return String.format(
            "\n=============== WEATHER REPORT ===============\n" +
            " Location : %s\n" +
            " Temperature : %.1f°C\n" +
            " Rainfall : %.1f mm\n" +
            " Humidity : %.1f%%\n" +
            " Timestamp : %s\n" +
            "============================================\n",
            city, temperature, rainfall, humidity,
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    @Override
    public String getWeatherData() {
        return generateWeatherData(currentCity);
    }

    public void shutdown() {
        System.out.println("Weather producer shutting down...");
    }
}
