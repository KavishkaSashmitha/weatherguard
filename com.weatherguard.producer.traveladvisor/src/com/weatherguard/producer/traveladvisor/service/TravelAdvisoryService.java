package com.weatherguard.producer.traveladvisor.service;

import com.weatherguard.producer.traveladvisor.model.TravelAdvisor;
import java.util.List;

public interface TravelAdvisoryService {
    
   
    TravelAdvisor getRecommendations(String category);
    
   
    List<String> getAvailableCategories();
}