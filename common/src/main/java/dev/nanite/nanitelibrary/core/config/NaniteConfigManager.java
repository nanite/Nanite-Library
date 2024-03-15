package dev.nanite.nanitelibrary.core.config;

import dev.nanite.nanitelibrary.core.config.types.ConfigItemStack;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

/**
 * A config system that is backed by a JSON system with some sane defaults.
 */
public enum NaniteConfigManager {
    INSTANCE;

    private final HashMap<Class<?>, Supplier<NaniteConfigType<?>>> customTypes = new HashMap<>();
    private final List<NaniteConfigSpec> providedConfigs = new ArrayList<>();

    public void setup() {
        // Register built-in types
        this.registerCustomType(ItemStack.class, ConfigItemStack::new);
    }

    public void registerCustomType(Class<?> clazz, Supplier<NaniteConfigType<?>> type) {
        this.customTypes.put(clazz, type);
    }

    public HashMap<Class<?>, Supplier<NaniteConfigType<?>>> getCustomTypes() {
        return customTypes;
    }
}
