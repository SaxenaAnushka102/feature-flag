package com.anushka.feature.service;

import com.anushka.feature.dto.EvaluateRequest;
import com.anushka.feature.dto.EvaluateResponse;

public interface EvaluationService {

    EvaluateResponse evaluate(String featureName,
                              EvaluateRequest request);
}