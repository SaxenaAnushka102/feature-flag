package com.anushka.feature.service;

import com.anushka.feature.dto.CreateFeatureFlagRequest;
import com.anushka.feature.entity.FeatureFlag;

public interface FeatureFlagService {

    FeatureFlag createFeatureFlag(CreateFeatureFlagRequest request);

    FeatureFlag getFeatureFlag(String name);
}