package dev.nanite.nanitelibrary.core.config;

import dev.nanite.nanitelibrary.core.configshit.NaniteConfigSpec;
import dev.nanite.nanitelibrary.core.configshit.NaniteConfigType;
import dev.nanite.nanitelibrary.core.configshit.types.ConfigItemStack;
import net.minecraft.world.item.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public enum NaniteConfigManager {
    INSTANCE;

//    private final HashMap<Class<?>, Supplier<NaniteConfigType<?>>> customTypes = new HashMap<>();
    private final List<NaniteConfig> providedConfigs = new ArrayList<>();

//    public void setup() {
//        // Register built-in types
//        this.registerCustomType(ItemStack.class, ConfigItemStack::new);
//    }
//
//    public void registerCustomType(Class<?> clazz, Supplier<NaniteConfigType<?>> type) {
//        this.customTypes.put(clazz, type);
//    }
//    public HashMap<Class<?>, Supplier<NaniteConfigType<?>>> getCustomTypes() {
//        return customTypes;
//    }

    public void registerConfig(NaniteConfig configSpec){
        configSpec.load();
        this.providedConfigs.add(configSpec);
    }

    public void reloadAll(){
        providedConfigs.forEach(NaniteConfig::fullReload);
    }
}
