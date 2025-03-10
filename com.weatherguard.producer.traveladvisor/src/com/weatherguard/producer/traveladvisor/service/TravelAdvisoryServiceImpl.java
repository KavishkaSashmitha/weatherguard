package com.weatherguard.producer.traveladvisor.service;

import com.weatherguard.producer.traveladvisor.model.TravelAdvisor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TravelAdvisoryServiceImpl implements TravelAdvisoryService {
    
    private final Map<String, TravelAdvisor> advisoryDatabase = new HashMap<>();
    private final List<String> categories = Arrays.asList("nature&wildlife", "adventure&outdoor", "historical&cultural", "religious&pilgrimage");
    public TravelAdvisoryServiceImpl() {
        initializeDatabase();
    }
    
        private void initializeDatabase() {
        
        List<String> natureDestinations = Arrays.asList("Yala", "Udawalawa", "Minneriya");
        List<String> natureActivities = Arrays.asList("Safari tours", "Bird watching", "Photography", "Camping");
        advisoryDatabase.put("nature&wildlife", new TravelAdvisor("nature&wildlife", natureDestinations, natureActivities,"Heavy rains causing flash floods.", 
                "Visit during the December-April for Yala and June-September for Minneriya."));
        
        
        List<String> adventureDestinations = Arrays.asList("Ella", "Knuckles Mountain Range", "Kitulgala");
        List<String> adventureActivities = Arrays.asList("Zip lining", "White water rafting", "Rock climbing", "Bungee jumping");
        advisoryDatabase.put("adventure&outdoor", new TravelAdvisor("adventure&outdoor", adventureDestinations, adventureActivities,"Flash floods and lanfdslides during monsoon seasons. Slippery trails during rainy periods",
                "Check weather forecasts before planning hikes. Wear proper hiking or rafting gear."));
        
        
        List<String> historicalDestinations = Arrays.asList("Sigiriya", "Polonnaruwa", "Anuradhapura");
        List<String> historicalActivities = Arrays.asList("Guided tours", "Museum visits", "Historical walks");
        advisoryDatabase.put("historical&cultural", new TravelAdvisor("historical&cultural", historicalDestinations, historicalActivities,"High temperature and heatstroke risks. Unpredictable thunderstorms during the monsoon seasons.",
                "Stay hydrated and wear sun protection. Avoid visiting during extreme weather conditions"));
        
        
        List<String> culturalDestinations = Arrays.asList("Adam's peak", "Dambulla", "Mihintale");
        List<String> culturalActivities = Arrays.asList("Climbing adam's peak", "Festival attendance", "Exploring sacred caves");
        advisoryDatabase.put("religious&pilgrimage", new TravelAdvisor("religious&pilgrimage", culturalDestinations, culturalActivities,"Heavy rain and fog affecting visibility.",
                "Start hikes early andf carry a flashlight for night pilrimages."));
    }
    
    @Override
    public TravelAdvisor getRecommendations(String category) {
        if (category == null || !categories.contains(category.toLowerCase())) {
            return createDefaultAdvisory();
        }
        return advisoryDatabase.get(category.toLowerCase());
    }
    
    @Override
    public List<String> getAvailableCategories() {
        return new ArrayList<>(categories);
    }
    
    private TravelAdvisor createDefaultAdvisory() {
        return new TravelAdvisor("general", 
            Arrays.asList("Weligama", "Hiriketiya", "Galle"),
            Arrays.asList("Whale watching", "Surfing", "Shopping"), "Cyclones during October-December. Tsunami risks",
            "Follow lifeguard instructions and avoid the sea during storms."
        );
    }
}
