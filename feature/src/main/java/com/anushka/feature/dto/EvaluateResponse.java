package com.anushka.feature.dto;

public class EvaluateResponse {

    private String featureName;

    private boolean enabled;

    public EvaluateResponse() {
    }

    public EvaluateResponse(String featureName, boolean enabled) {
        this.featureName = featureName;
        this.enabled = enabled;
    }

    // Getters & Setters
}