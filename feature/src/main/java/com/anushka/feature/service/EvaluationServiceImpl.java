package com.anushka.feature.service;

import com.anushka.feature.dto.EvaluateRequest;
import com.anushka.feature.dto.EvaluateResponse;
import com.anushka.feature.entity.FeatureFlag;
import com.anushka.feature.entity.FeatureOverride;
import com.anushka.feature.exception.ResourceNotFoundException;
import com.anushka.feature.repository.FeatureFlagRepository;
import com.anushka.feature.repository.FeatureOverrideRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    private final FeatureFlagRepository featureFlagRepository;
    private final FeatureOverrideRepository featureOverrideRepository;

    public EvaluationServiceImpl(
            FeatureFlagRepository featureFlagRepository,
            FeatureOverrideRepository featureOverrideRepository) {

        this.featureFlagRepository = featureFlagRepository;
        this.featureOverrideRepository = featureOverrideRepository;
    }

    @Override
    public EvaluateResponse evaluate(String featureName,
                                     EvaluateRequest request) {

        Optional<FeatureFlag> featureFlagOptional = featureFlagRepository
                .findByName(featureName);

        if (!featureFlagOptional.isPresent()) {
            throw new ResourceNotFoundException("Feature not found");
        }

        FeatureFlag featureFlag = featureFlagOptional.get();

        // 1. Explicit user override
        Optional<FeatureOverride> override =
                featureOverrideRepository.findByFeatureFlagIdAndUserId(
                        featureFlag.getId(),
                        request.getUserId());

        if (override.isPresent()) {
            return new EvaluateResponse(
                    featureFlag.getName(),
                    override.get().isEnabled()
            );
        }

        // 2. Percentage rollout
        int bucket = Math.abs(request.getUserId().hashCode()) % 100;

        if (bucket < featureFlag.getRolloutPercentage()) {
            return new EvaluateResponse(
                    featureFlag.getName(),
                    true
            );
        }

        // 3. Rule evaluation (implement later)

        // 4. Default state
        return new EvaluateResponse(
                featureFlag.getName(),
                featureFlag.isDefaultState()
        );
    }
}