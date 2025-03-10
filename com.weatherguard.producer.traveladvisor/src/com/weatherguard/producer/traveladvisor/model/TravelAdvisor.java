package com.weatherguard.producer.traveladvisor.model;

import java.util.List;
import java.util.ArrayList;

public class TravelAdvisor {
    private String category;
    private List<String> destinations;
    private List<String> activities;
    private String risks;
    private String safetyInfo;
    
    public TravelAdvisor(String category, List<String> destinations, List<String> activities, String risks, String safetyInfo) {
        this.category = category;
        this.destinations = destinations;
        this.activities = activities;
        this.risks = risks;
        this.safetyInfo = safetyInfo;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public List<String> getDestinations() {
        return destinations;
    }
    
    public void setDestinations(List<String> destinations) {
        this.destinations = destinations;
    }
    
    public List<String> getActivities() {
        return activities;
    }
    
    public void setActivities(List<String> activities) {
        this.activities = activities;
    }
    
    public String getRisks() {
        return risks;
    }
    
    public void setRisks(String risks) {
        this.risks = risks;
    }
    
    public String getSafetyInfo() {
        return safetyInfo;
    }
    
    public void setSafetyInfo(String safetyInfo) {
        this.safetyInfo = safetyInfo;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Travel Advisory for ").append(category).append(" lovers:\n");
        sb.append("Recommended Destinations: ").append(String.join(", ", destinations)).append("\n");
        sb.append("Suggested Activities: ").append(String.join(", ", activities)).append("\n");
        sb.append("Potential Risks: ").append(risks);
        sb.append("\nSafety Advice: ").append(safetyInfo);
        return sb.toString();
    }
}