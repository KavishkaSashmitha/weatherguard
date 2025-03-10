package com.weatherguard.consumer.emergency;

import com.weatherguard.producer.weatherstation.WeatherProducer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.Scanner;

public class WeatherConsumer implements BundleActivator {
    private ServiceReference<WeatherProducer> serviceReference;
    private WeatherProducer weatherProducer;
    private boolean running = true;

    @Override
    public void start(BundleContext context) {
        System.out.println("Starting Weather Consumer...");
        
        try {
            serviceReference = context.getServiceReference(WeatherProducer.class);
            if (serviceReference == null) {
                System.out.println("WeatherProducer service not available.");
                return;
            }

            weatherProducer = context.getService(serviceReference);
            if (weatherProducer == null) {
                System.out.println("Could not get WeatherProducer service.");
                return;
            }

            // Display all cities weather data immediately
            System.out.println(weatherProducer.getAllCitiesWeatherData());
            
            // Start menu
            startConsole();
            
        } catch (Exception e) {
            System.out.println("Error starting Weather Consumer: " + e.getMessage());
        }
    }

    private void startConsole() {
        // Start the console in the main thread
        Scanner scanner = new Scanner(System.in);
        while (running) {
            try {
                displayMenu(scanner);
            } catch (Exception e) {
                System.out.println("Error in menu: " + e.getMessage());
            }
        }
        scanner.close();
    }
    
    private void displayMenu(Scanner scanner) {
        System.out.println("\n===== WEATHER GUARD SYSTEM =====");
        System.out.println("Options:");
        System.out.println("1. View all cities weather data");
        System.out.println("2. Select a specific city");
        System.out.println("3. Exit");
        System.out.println("\nEnter your choice (1-3):");
        
        String input = scanner.nextLine().trim();
        
        switch (input) {
            case "1":
                // Show all cities weather data
                System.out.println(weatherProducer.getAllCitiesWeatherData());
                analyzeAllCitiesWeather(weatherProducer.getAllCitiesWeatherData());
                break;
                
            case "2":
                selectCity(scanner);
                break;
                
            case "3":
                running = false;
                System.out.println("Exiting...");
                break;
                
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 3");
        }
    }
    
    private void selectCity(Scanner scanner) {
        System.out.println("\nAvailable cities:");
        
        // Show city list
        String[] cities = weatherProducer.getAvailableCities();
        for (int i = 0; i < cities.length; i++) {
            System.out.println((i + 1) + ". " + cities[i]);
        }
        
        System.out.println("\nEnter city number:");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice >= 1 && choice <= cities.length) {
                String selectedCity = cities[choice - 1];
                System.out.println("You selected: " + selectedCity);
                weatherProducer.setCity(selectedCity);
                
                // Show weather report for selected city
                String weatherData = weatherProducer.getWeatherData();
                System.out.println(weatherData);
                analyzeWeather(weatherData);
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and " + cities.length);
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number");
        }
    }

    private void analyzeAllCitiesWeather(String allWeatherData) {
        if (allWeatherData == null || allWeatherData.isEmpty()) {
            return;
        }

        String[] cityReports = allWeatherData.split("---");
        
        // Skip the first element (it's the header)
        for (int i = 1; i < cityReports.length; i++) {
            String cityData = cityReports[i];
            String[] lines = cityData.split("\n");
            
            // Extract city name (first line)
            String cityName = lines[0].trim();
            
            // Extract temperature and rainfall
            double temperature = 0;
            double rainfall = 0;
            
            for (String line : lines) {
                if (line.trim().startsWith("Temperature")) {
                    temperature = Double.parseDouble(line.split(":")[1].trim().replace("°C", ""));
                } else if (line.trim().startsWith("Rainfall")) {
                    rainfall = Double.parseDouble(line.split(":")[1].trim().replace("mm", ""));
                }
            }
            
            // Check for alert conditions
            if (temperature > 32) {
                System.out.println("\n[ALERT] Extreme Heat in " + cityName + "! Temperature: " + temperature + "°C");
                System.out.println("Stay hydrated and avoid outdoor activities.");
            }
            
            if (rainfall > 4.0) {
                System.out.println("\n[ALERT] Heavy Rainfall in " + cityName + "! Rainfall: " + rainfall + "mm");
                System.out.println("Risk of flooding, take precautions.");
            }
        }
    }

    private void analyzeWeather(String weatherData) {
        if (weatherData == null || weatherData.isEmpty()) {
            return;
        }

        try {
            // Extract data from the weather report
            String[] lines = weatherData.split("\n");
            
            String location = "";
            double temperature = 0;
            double rainfall = 0;
            
            for (String line : lines) {
                if (line.trim().startsWith("Location")) {
                    location = line.split(":")[1].trim();
                } else if (line.trim().startsWith("Temperature")) {
                    temperature = Double.parseDouble(line.split(":")[1].trim().replace("°C", ""));
                } else if (line.trim().startsWith("Rainfall")) {
                    rainfall = Double.parseDouble(line.split(":")[1].trim().replace("mm", ""));
                }
            }

            // Check for alert conditions
            if (temperature > 32) {
                System.out.println("\n[ALERT] Extreme Heat in " + location + "! Temperature: " + temperature + "°C");
                System.out.println("Stay hydrated and avoid outdoor activities.");
            }
            
            if (rainfall > 4.0) {
                System.out.println("\n[ALERT] Heavy Rainfall in " + location + "! Rainfall: " + rainfall + "mm");
                System.out.println("Risk of flooding, take precautions.");
            }
            else {
            	System.out.println("NO EMERGANCY ");
            }
            
        } catch (Exception e) {
            System.out.println("Error analyzing weather data: " + e.getMessage());
        }
    }

    @Override
    public void stop(BundleContext context) {
        running = false;
        
        if (serviceReference != null) {
            context.ungetService(serviceReference);
        }
        
        System.out.println("Weather Consumer stopped.");
    }
}
