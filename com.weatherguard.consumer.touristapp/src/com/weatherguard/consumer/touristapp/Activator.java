package com.weatherguard.consumer.touristapp;

import com.weatherguard.producer.traveladvisor.model.TravelAdvisor;
import com.weatherguard.producer.traveladvisor.service.TravelAdvisoryService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import java.util.List;
import java.util.Scanner;

public class Activator implements BundleActivator {
    
    private ServiceTracker<TravelAdvisoryService, TravelAdvisoryService> serviceTracker;
    private Thread consumerThread;
    private volatile boolean running = false;
    
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Starting Tourist App Consumer...");
        
        
        serviceTracker = new ServiceTracker<TravelAdvisoryService, TravelAdvisoryService>(
            context, 
            TravelAdvisoryService.class, 
            null
        ) {
            @Override
            public TravelAdvisoryService addingService(ServiceReference<TravelAdvisoryService> reference) {
                TravelAdvisoryService service = super.addingService(reference);
                System.out.println("Travel Advisory Service detected by Tourist App Consumer.");
                return service;
            }
            
            @Override
            public void removedService(ServiceReference<TravelAdvisoryService> reference, TravelAdvisoryService service) {
                System.out.println("Travel Advisory Service removed. Tourist App waiting for service...");
                super.removedService(reference, service);
            }
        };
        
        serviceTracker.open();
        
        running = true;
        consumerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                interactWithUser();
            }
        });
        consumerThread.start();
        
        System.out.println("Tourist App Consumer started successfully!!!");
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Stopping Tourist App Consumer...");
        
        running = false;
        if (consumerThread != null) {
            consumerThread.interrupt();
            consumerThread.join(2000); 
        }
        
        if (serviceTracker != null) {
            serviceTracker.close();
            serviceTracker = null;
        }
        
        System.out.println("Tourist App Consumer stopped.");
    }
    
 
    private void interactWithUser() {
        Scanner scanner = new Scanner(System.in);
        
        while (running) {
            try {
               
                TravelAdvisoryService service = serviceTracker.getService();
                
                if (service != null) {
                   
                    List<String> categories = service.getAvailableCategories();
                    System.out.println("\n============= TOURIST APP =============");
                    System.out.println("Welcome to the Tourist App!!!");
                    System.out.println("Available travel categories:");
                    
                    for (int i = 0; i < categories.size(); i++) {
                        System.out.println((i + 1) + ". " + categories.get(i));
                    }
                    
                    System.out.println("\nEnter the number of category (or 'exit' to quit):");
                    String input = scanner.nextLine().trim();
                    
                    if ("exit".equalsIgnoreCase(input)) {
                        System.out.println("Thank you for using the Tourist App!");
                        break;
                    }
                    
                    String selectedCategory;
                    try {
                        int index = Integer.parseInt(input) - 1;
                        if (index >= 0 && index < categories.size()) {
                            selectedCategory = categories.get(index);
                        } else {
                            System.out.println("Invalid number. Please try again.");
                            Thread.sleep(1000);
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        selectedCategory = input.toLowerCase();
                    }
                    
                    TravelAdvisor advisor = service.getRecommendations(selectedCategory);
                    System.out.println("\n===== TRAVEL RECOMMENDATIONS AND SAFETY TIPS=====");
                    System.out.println(advisor.toString());
                    System.out.println("==================================\n");
                    
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                    
                } else {
                    System.out.println("Waiting for Travel Advisory Service...");
                    Thread.sleep(5000);
                }
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}