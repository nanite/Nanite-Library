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

    public static void registerClientConfig(Config config) {
        ConfigManager instance = get();
        synchronized (instance.configs) {
            if (instance.loadedConfigTypes.contains(ConfigType.CLIENT)) {
                throw new IllegalStateException("Cannot register client config after client configs have been loaded.");
            }
            instance.configs.computeIfAbsent(ConfigType.CLIENT, k -> 
                Collections.synchronizedList(new ArrayList<>())
            ).add(config);
        }
    }

    public static void registerServerConfig(Config config) {
        ConfigManager instance = get();
        synchronized (instance.configs) {
            if (instance.loadedConfigTypes.contains(ConfigType.SERVER)) {
                throw new IllegalStateException("Cannot register server config after server configs have been loaded.");
            }
            instance.configs.computeIfAbsent(ConfigType.SERVER, k -> 
                Collections.synchronizedList(new ArrayList<>())
            ).add(config);
        }
    }

    public static void registerCommonConfig(Config config) {
        ConfigManager instance = get();
        synchronized (instance.configs) {
            if (instance.loadedConfigTypes.contains(ConfigType.COMMON)) {
                throw new IllegalStateException("Cannot register common config after common configs have been loaded.");
            }
            instance.configs.computeIfAbsent(ConfigType.COMMON, k -> 
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
