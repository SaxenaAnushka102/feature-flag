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
