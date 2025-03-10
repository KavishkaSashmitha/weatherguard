package com.weatherguard.producer.traveladvisor;

import com.weatherguard.producer.traveladvisor.service.TravelAdvisoryService;
import com.weatherguard.producer.traveladvisor.service.TravelAdvisoryServiceImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    
    private ServiceRegistration<TravelAdvisoryService> serviceRegistration;
    
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Starting Travel Advisory Producer service...");
        
        TravelAdvisoryService service = new TravelAdvisoryServiceImpl();
        serviceRegistration = context.registerService(
            TravelAdvisoryService.class, 
            service, 
            null
        );
        
        System.out.println("Travel Advisory Producer service registered successfully!!!");
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Stopping Travel Advisory Producer service...");
        
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
            serviceRegistration = null;
        }
        
        System.out.println("Travel Advisory Producer service stopped.");
    }
}