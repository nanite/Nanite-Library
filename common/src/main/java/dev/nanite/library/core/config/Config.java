package dev.nanite.library.core.config;

import de.marhali.json5.Json5;
import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Object;
import de.marhali.json5.exception.Json5Exception;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/// Config class representing a single config file.
public class Config implements IConfigParent {
    public static Logger LOGGER = LoggerFactory.getLogger(Config.class);

    private final String modId;

    @Nullable
    private final String type;
    private final ConfigContainer container;

    @Nullable
    private Path configPath;

    public Config(String modId, @Nullable String type) {
        this.modId = modId;
        this.type = type;
        this.container = new ConfigContainer(this);
    }

    public Config(String modId) {
        this(modId, null);
    }

    @Override
    public ConfigContainer getContainer() {
        return container;
    }

    public String getModId() {
        return modId;
    }

    @Nullable
    public String getType() {
        return type;
    }

    public void setConfigPath(Path configPath) {
        this.configPath = configPath;
    }

    public Path getConfigPath() {
        if (configPath == null) {
            // Default to config/<modId>.json5 or config/<modId>-<type>.json5
            String filename = type != null ? modId + "-" + type + ".json5" : modId + ".json5";
            configPath = Path.of("config", filename);
        }
        return configPath;
    }

    public void load() {
        Path path = getConfigPath();

        // Create config directory if it doesn't exist
        try {
            Path configDir = path.getParent();
            if (configDir != null && !Files.exists(configDir)) {
                Files.createDirectories(configDir);
                LOGGER.info("Created config directory: {}", configDir);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to create config directory: {}", e.getMessage());
        }

        // Load from disk if file exists
        if (Files.exists(path)) {
            try {
                String content = Files.readString(path);
                Json5Element element = new Json5().parse(content);
                
                if (element instanceof Json5Object loadedData) {
                    // Copy loaded data into container
                    for (String key : loadedData.keySet()) {
                        container.getData().add(key, loadedData.get(key));
                    }
                    LOGGER.info("Loaded config from: {}", path);
                } else {
                    LOGGER.error("Config file {} does not contain a JSON object", path);
                }
            } catch (IOException e) {
                LOGGER.error("Failed to read config file {}: {}", path, e.getMessage());
            } catch (Json5Exception e) {
                LOGGER.error("Failed to parse config file {}: {}", path, e.getMessage());
            }
        } else {
            LOGGER.info("Config file does not exist, using defaults: {}", path);
        }

        // Load all registered values
        container.loadValues();

        // Save to ensure file exists with all defaults
        save();
    }

    public void save() {
        Path path = getConfigPath();

        try {
            // Ensure parent directory exists
            Path configDir = path.getParent();
            if (configDir != null && !Files.exists(configDir)) {
                Files.createDirectories(configDir);
            }

            // TODO: Something is wrong here.
            // Serialize all values into container data
            for (Map.Entry<String, ConfigValue<?>> entry : container.getValues().entrySet()) {
                String key = entry.getKey();
                ConfigValue<?> value = entry.getValue();
                container.saveValue(key, value.serializeWithComments());
            }

            // Write to file with pretty formatting
            String json5String = new Json5().serialize(container.getData());
            Files.writeString(path, json5String);

            LOGGER.debug("Saved config to: {}", path);
        } catch (IOException e) {
            LOGGER.error("Failed to write config file {}: {}", path, e.getMessage());
        }
    }
}
