package dev.nanite.library.core.config;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Object;

import java.util.Map;

public class ConfigValueGroup extends ConfigValue<ConfigValueGroup> implements IConfigParent {
    private final ConfigContainer container;

    public ConfigValueGroup(IConfigParent parent, String key) {
        super(parent, key, null);
        this.container = new ConfigContainer(this);
    }

    @Override
    public ConfigValueGroup comments(String... comments) {
        return (ConfigValueGroup) super.comments(comments);
    }

    @Override
    public ConfigContainer getContainer() {
        return container;
    }

    @Override
    public ConfigValueGroup deserialize(Json5Element element) {
        if (!(element instanceof Json5Object obj)) {
            throw new IllegalArgumentException("Expected a JSON object for ConfigValueGroup");
        }

        // Copy data from deserialized object into our container's data
        Json5Object data = container.getData();
        for (String key : obj.keySet()) {
            data.add(key, obj.get(key));
        }

        // Load all child values
        container.loadValues();

        return this;
    }

    @Override
    public Json5Element serialize() {
        // First, serialize all child values into the container's data
        for (Map.Entry<String, ConfigValue<?>> entry : container.getValues().entrySet()) {
            container.saveValue(entry.getKey(), entry.getValue().serialize());
        }
        
        // Return the populated data object (comments will be added by serializeWithComments())
        return container.getData();
    }

    @Override
    public ConfigValueGroup get() {
        return this;
    }
}
