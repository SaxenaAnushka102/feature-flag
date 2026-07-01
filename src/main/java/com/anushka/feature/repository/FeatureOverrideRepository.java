package com.anushka.feature.repository;

import com.anushka.feature.entity.FeatureOverride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeatureOverrideRepository extends JpaRepository<FeatureOverride, Long> {

    Optional<FeatureOverride> findByFeatureFlagIdAndUserId(Long featureFlagId,
                                                           String userId);
}