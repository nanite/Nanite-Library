package dev.nanite.library.core.config;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Object;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/// Expected use case.
/// ```java
/// public static final Config config = new Config("mymod");
/// public static final ConfigValue<Integer> someValue = config.intValue("someValue", 42);
///
/// public static final ConfigValueGroup someGroup = config.group("someGroup");
/// public static final ConfigValue<String> someGroupValue = someGroup.stringValue("someGroupValue", "Hello, world!");
///
/// public static void init() {
///     ConfigManager.registerClientConfig(config);
/// }
/// ```
public class Config implements IConfigParent {
    static Logger LOGGER = LoggerFactory.getLogger(Config.class);

    private final String modId;

    @Nullable
    private final String type;
    private final ConfigContainer container;

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

    // All IConfigParent methods (getValue, saveValue, intValue, stringValue, group)
    // are inherited as default methods from the interface

    public void load() {
        // TODO: Load from disk.
        // Load in a json5Object.
        var configData = new Json5Object();

        container.loadValues();
    }

    public void save() {
        // TODO: Write to disk.
    }
}
