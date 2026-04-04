package dev.nanite.library.core.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {
    private static final ConfigManager INSTANCE = new ConfigManager();

    public static ConfigManager get() {
        return INSTANCE;
    }

    private final Map<ConfigType, List<Config>> configs = new HashMap<>();

    public static void registerClientConfig(Config config) {
        get().configs.computeIfAbsent(ConfigType.CLIENT, (ignored) -> new ArrayList<>()).add(config);
    }

    public static void registerServerConfig(Config config) {
        get().configs.computeIfAbsent(ConfigType.SERVER, (ignored) -> new ArrayList<>()).add(config);
    }

    public static void registerCommonConfig(Config config) {
        get().configs.computeIfAbsent(ConfigType.COMMON, (ignored) -> new ArrayList<>()).add(config);
    }

    public void loadConfigs(ConfigType type) {
        var configsByType = configs.get(type);
        if (configsByType != null) {

        }
    }

    public enum ConfigType {
        CLIENT,
        SERVER,
        COMMON
    }
}
