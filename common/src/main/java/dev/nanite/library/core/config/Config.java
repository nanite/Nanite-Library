package dev.nanite.library.core.config;

import de.marhali.json5.Json5;
import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Object;
import de.marhali.json5.exception.Json5Exception;
import dev.nanite.library.platform.Platform;
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

    private final ConfigType configType;

    /// Meta name is used to allow for multiple modid configs such as 'machines', 'world', etc.
    @Nullable
    private final String metaName;
    private final ConfigContainer container;

    private final String fileName;

    private Config(String modId, @Nullable String metaName, ConfigType configType) {
        this.modId = modId;
        this.metaName = metaName;
        this.container = new ConfigContainer(this);
        this.configType = configType;
        this.fileName = this.computeFileName();
    }

    private Config(String modId, ConfigType configType) {
        this(modId, null, configType);
    }

    public static Config clientConfig(String modId) {
        return new Config(modId, ConfigType.CLIENT);
    }

    public static Config serverConfig(String modId) {
        return new Config(modId, ConfigType.SERVER);
    }

    public static Config commonConfig(String modId) {
        return new Config(modId, ConfigType.COMMON);
    }

    @Override
    public ConfigContainer getContainer() {
        return container;
    }

    public String getModId() {
        return modId;
    }

    @Nullable
    public String getMetaName() {
        return metaName;
    }

    public ConfigType getConfigType() {
        return configType;
    }

    private String computeFileName() {
        StringBuilder fileNameBuilder = new StringBuilder(modId);

        if (metaName != null && !metaName.isEmpty()) {
            fileNameBuilder.append("_").append(metaName);
        }

        return fileNameBuilder
                .append("-")
                .append(this.configType.toString().toLowerCase())
                .append(".json5")
                .toString();
    }

    public String fileName() {
        return fileName;
    }

    public Path path() {
        return Platform.INSTANCE.configPath().resolve(fileName);
    }

    public void load() {
        Path path = this.path();

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
        var exists = Files.exists(path);
        if (exists) {
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

    public void loadFromJsonPayload(Json5Object object) {
        // Copy loaded data into container
        for (String key : object.keySet()) {
            container.getData().add(key, object.get(key));
        }

        container.loadValues();
    }

    public void save() {
        Path path = this.path();

        try {
            // Ensure parent directory exists
            Path configDir = path.getParent();
            if (configDir != null && !Files.exists(configDir)) {
                Files.createDirectories(configDir);
            }

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
