package com.weatherguard.producer.weatherstation;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    private ServiceRegistration<WeatherProducer> registration;
    private WeatherProducerImpl weatherProducer;

    @Override
    public void start(BundleContext context) throws Exception {
        weatherProducer = new WeatherProducerImpl();
        registration = context.registerService(WeatherProducer.class, weatherProducer, null);
        System.out.println("WeatherProducer service registered successfully!");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (registration != null) {
            registration.unregister();
        }
        if (weatherProducer != null) {
            weatherProducer.shutdown();
        }
        System.out.println("WeatherProducer service unregistered.");
    }
}