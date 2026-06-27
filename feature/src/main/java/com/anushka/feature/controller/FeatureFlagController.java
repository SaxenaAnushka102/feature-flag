package com.anushka.feature.controller;

import com.anushka.feature.dto.CreateFeatureFlagRequest;
import com.anushka.feature.dto.EvaluateRequest;
import com.anushka.feature.dto.EvaluateResponse;
import com.anushka.feature.entity.FeatureFlag;
import com.anushka.feature.service.EvaluationService;
import com.anushka.feature.service.FeatureFlagService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/feature-flags")
public class FeatureFlagController {

    private final FeatureFlagService featureFlagService;
    private final EvaluationService evaluationService;

    public FeatureFlagController(
            FeatureFlagService featureFlagService,
            EvaluationService evaluationService) {

        this.featureFlagService = featureFlagService;
        this.evaluationService = evaluationService;
    }

    @PostMapping
    public ResponseEntity<FeatureFlag> createFeatureFlag(
            @Valid @RequestBody CreateFeatureFlagRequest request) {

        FeatureFlag featureFlag = featureFlagService.createFeatureFlag(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(featureFlag);
    }

    @GetMapping("/{name}")
    public ResponseEntity<FeatureFlag> getFeatureFlag(
            @PathVariable String name) {

        return ResponseEntity.ok(
                featureFlagService.getFeatureFlag(name)
        );
    }

    @PostMapping("/{name}/evaluate")
    public ResponseEntity<EvaluateResponse> evaluateFeature(
            @PathVariable String name,
            @Valid @RequestBody EvaluateRequest request) {

        return ResponseEntity.ok(
                evaluationService.evaluate(name, request)
        );
    }
}