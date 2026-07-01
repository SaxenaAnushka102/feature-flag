package com.anushka.feature.cache;

import com.anushka.feature.entity.FeatureFlag;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FeatureFlagCache {

    private final Map<String, FeatureFlag> cache = new ConcurrentHashMap<>();

    public FeatureFlag get(String name) {
        return cache.get(name);
    }

    public void put(FeatureFlag flag) {
        cache.put(flag.getName(), flag);
    }

    public void evict(String name) {
        cache.remove(name);
    }

    public void warmUp(List<FeatureFlag> flags) {
        flags.forEach(f -> cache.put(f.getName(), f));
    }
}
