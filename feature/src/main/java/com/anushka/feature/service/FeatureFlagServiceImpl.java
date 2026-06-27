package com.anushka.feature.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.anushka.feature.dto.CreateFeatureFlagRequest;
import com.anushka.feature.dto.RuleDto;
import com.anushka.feature.entity.FeatureFlag;
import com.anushka.feature.entity.Rule;
import com.anushka.feature.exception.ResourceNotFoundException;
import com.anushka.feature.repository.FeatureFlagRepository;

@Service
public class FeatureFlagServiceImpl implements FeatureFlagService {

    private final FeatureFlagRepository featureFlagRepository;

    public FeatureFlagServiceImpl(FeatureFlagRepository featureFlagRepository) {
        this.featureFlagRepository = featureFlagRepository;
    }

    @Override
    public FeatureFlag createFeatureFlag(CreateFeatureFlagRequest request) {

        if (featureFlagRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Feature flag already exists");
        }

        FeatureFlag featureFlag = new FeatureFlag();

        featureFlag.setName(request.getName());
        featureFlag.setDefaultState(request.isDefaultState());
        featureFlag.setRolloutPercentage(request.getRolloutPercentage());

        List<Rule> rules = new ArrayList<>();

        for (RuleDto dto : request.getRules()) {

            Rule rule = new Rule();

            rule.setAttribute(dto.getAttribute());
            rule.setOperator(dto.getOperator());
            rule.setValue(dto.getValue());
            rule.setEnabled(dto.isEnabled());

            // IMPORTANT
            rule.setFeatureFlag(featureFlag);

            rules.add(rule);
        }

        featureFlag.setRules(rules);

        return featureFlagRepository.save(featureFlag);
    }

    @Override
    public FeatureFlag getFeatureFlag(String name) {

        return featureFlagRepository.findByName(name)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Feature flag not found"));
    }
}