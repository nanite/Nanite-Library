package dev.nanite.library.core.config;

import com.google.common.collect.ImmutableList;

import java.util.*;

public class ConfigManager {
    private static final ConfigManager INSTANCE = new ConfigManager();

    public static ConfigManager get() {
        return INSTANCE;
    }

    private final Map<ConfigType, List<Config>> configs = new EnumMap<>(ConfigType.class);

    /// Common configs load as soon as they're registered, so their values are available during mod loading.
    private final Set<ConfigType> loadedConfigTypes = EnumSet.of(ConfigType.COMMON);

    /// Registers a config. If configs of its type have already been loaded, it's loaded immediately.
    public static void register(Config config) {
        get().registerConfig(config);
    }

    private synchronized void registerConfig(Config config) {
        var type = config.getConfigType();
        configs.computeIfAbsent(type, k -> new ArrayList<>()).add(config);

        if (loadedConfigTypes.contains(type)) {
            config.load();
        }
    }

    public synchronized void loadConfigs(ConfigType type) {
        for (var config : configs.getOrDefault(type, List.of())) {
            config.load();
        }

        loadedConfigTypes.add(type);
    }

    /// Drops values synced from a remote server and reloads the affected configs from disk.
    public synchronized void restoreSyncedConfigs() {
        for (var config : configs.getOrDefault(ConfigType.COMMON, List.of())) {
            config.restoreFromDisk();
        }
    }

    public synchronized ImmutableList<Config> getConfigsByType(ConfigType type) {
        return ImmutableList.copyOf(configs.getOrDefault(type, List.of()));
    }
}
