package com.weatherguard.consumer.hotelmanagement;

import com.weatherguard.producer.weatherstation.WeatherProducer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {
    private ServiceReference<WeatherProducer> serviceReference;
    private WeatherProducer weatherProducer;

    @Override
    public void start(BundleContext context) throws Exception {
        // Get the WeatherProducer service reference
        serviceReference = context.getServiceReference(WeatherProducer.class);
        
        if (serviceReference != null) {
            weatherProducer = context.getService(serviceReference);
            System.out.println("Hotel Management System started. Fetching weather data...");
            
            // Simulate using weather data in hotel operations
            runHotelOperations();
        } else {
            System.out.println("WeatherProducer service not available!");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (serviceReference != null) {
            context.ungetService(serviceReference);
        }
        System.out.println("Hotel Management System stopped.");
    }

    private void runHotelOperations() {
        // Get weather data for the default city
        String weatherData = weatherProducer.getWeatherData();
        System.out.println("Received Weather Data: " + weatherData);

        // Suggest activities based on weather conditions
        suggestHotelActivities(weatherData);
    }

    private void suggestHotelActivities(String weatherData) {
        if (weatherData.contains("Rainfall") && weatherData.contains("0.0 mm")) {
            System.out.println("Weather is clear. Suggest outdoor activities like poolside dining and sightseeing tours.");
        } else {
            System.out.println("Rain detected. Suggest indoor activities like spa services and indoor dining.");
        }
    }
}
