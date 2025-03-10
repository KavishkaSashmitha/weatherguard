package com.weatherguard.producer.disasteralert.producer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.weatherguard.producer.disasteralert.model.DisasterAlert;
import com.weatherguard.producer.disasteralert.service.DisasterAlertService;

public class DisasterAlertProducer implements DisasterAlertService, BundleActivator {
    private ServiceRegistration<?> serviceRegistration;
    private Map<String, DisasterAlert> alertRegistry = new HashMap<>();
    private Random random = new Random();

    private String[] locations = {
        "Colombo", "Galle", "Kandy", "Jaffna", "Trincomalee", 
        "Anuradhapura", "Matara", "Negombo", "Ratnapura", "Badulla"
    };

    // Disaster types
    private String[] alertTypes = {
        "FLOOD", "LANDSLIDE", "TSUNAMI", "EARTHQUAKE", "CYCLONE", 
        "DROUGHT", "THUNDERSTORM", "FIRE", "VOLCANIC_ERUPTION"
    };

    // Severity levels
    private String[] severityLevels = {
        "LOW", "MEDIUM", "HIGH", "CRITICAL"
    };

    // Descriptions
    private String[] descriptions = {
        "Minor risk, stay alert.", 
        "Moderate risk, take precautions.", 
        "High risk, evacuate if necessary.", 
        "Critical risk, immediate action required."
    };

    @Override
    public void start(BundleContext context) throws Exception {
        // Register the service when the bundle starts
        serviceRegistration = context.registerService(
            DisasterAlertService.class.getName(), 
            this, 
            null
        );

        // Broadcast some random alerts
        for (int i = 0; i < 2; i++) {
            broadcastRandomAlert();
        }

        System.out.println("Disaster Alert Producer Bundle Started");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        // Unregister the service when the bundle stops
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
        }
        System.out.println("Disaster Alert Producer Bundle Stopped");
    }

    @Override
    public void broadcastAlert(String location, String alertType, String severityLevel, String description) {
        DisasterAlert alert = new DisasterAlert(location, alertType, severityLevel, description);
        alertRegistry.put(location, alert);
        System.out.printf("Broadcasted Alert: %s in %s (Severity: %s) - %s%n", alertType, location, severityLevel, description);
    }

    @Override
    public DisasterAlert getLatestAlert(String location) {
        return alertRegistry.get(location);
    }

    // Helper method to generate random alerts
    private void broadcastRandomAlert() {
        String location = locations[random.nextInt(locations.length)];
        String alertType = alertTypes[random.nextInt(alertTypes.length)];
        String severityLevel = severityLevels[random.nextInt(severityLevels.length)];
        String description = descriptions[random.nextInt(descriptions.length)];

        broadcastAlert(location, alertType, severityLevel, description);
    }
}