package dev.nanite.library.core.config;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigManager {
    private static final ConfigManager INSTANCE = new ConfigManager();

    public static ConfigManager get() {
        return INSTANCE;
    }

    private final Map<ConfigType, List<Config>> configs = new ConcurrentHashMap<>();
    private final Set<ConfigType> loadedConfigTypes = ConcurrentHashMap.newKeySet();

    public static void register(Config config) {
        var type = config.getConfigType();
        ConfigManager instance = get();
        synchronized (instance.configs) {
            if (instance.loadedConfigTypes.contains(type)) {
                throw new IllegalStateException("Cannot register " + type + " config after configs of that type have been loaded.");
            }
            instance.configs.computeIfAbsent(type, k ->
                    Collections.synchronizedList(new ArrayList<>())
            ).add(config);
        }
    }

    public synchronized void loadConfigs(ConfigType type) {
        if (loadedConfigTypes.contains(type)) {
            throw new IllegalStateException("Configs of type " + type + " have already been loaded.");
        }

        var configsByType = configs.get(type);
        if (configsByType == null) {
            return;
        }

        for (var config : configsByType) {
            config.load();
        }

        loadedConfigTypes.add(type);
    }

}
