package dev.nanite.library.core.config;

import java.util.*;

public class ConfigManager {
    private static final ConfigManager INSTANCE = new ConfigManager();

    public static ConfigManager get() {
        return INSTANCE;
    }

    private final Map<ConfigType, List<Config>> configs = new HashMap<>();
    private final Set<ConfigType> loadedConfigTypes = new HashSet<>();

    public static void registerClientConfig(Config config) {
        if (get().loadedConfigTypes.contains(ConfigType.CLIENT)) {
            throw new IllegalStateException("Cannot register client config after client configs have been loaded.");
        }

        get().configs.computeIfAbsent(ConfigType.CLIENT, (ignored) -> new ArrayList<>()).add(config);
    }

    public static void registerServerConfig(Config config) {
        if (get().loadedConfigTypes.contains(ConfigType.SERVER)) {
            throw new IllegalStateException("Cannot register server config after server configs have been loaded.");
        }

        get().configs.computeIfAbsent(ConfigType.SERVER, (ignored) -> new ArrayList<>()).add(config);
    }

    public static void registerCommonConfig(Config config) {
        if (get().loadedConfigTypes.contains(ConfigType.COMMON)) {
            throw new IllegalStateException("Cannot register common config after common configs have been loaded.");
        }

        get().configs.computeIfAbsent(ConfigType.COMMON, (ignored) -> new ArrayList<>()).add(config);
    }

    public void loadConfigs(ConfigType type) {
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

    public enum ConfigType {
        CLIENT,
        SERVER,
        COMMON
    }
}
