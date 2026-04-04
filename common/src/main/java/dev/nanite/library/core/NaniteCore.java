package dev.nanite.library.core;

import dev.nanite.library.core.registry.NaniteRegistry;
import dev.nanite.library.platform.Services;
import net.minecraft.core.Registry;

public class NaniteCore {
    public static <T> NaniteRegistry<T> createRegistry(String modId, Registry<T> backingRegistry) {
        return Services.PLATFORM.createRegistry(modId, backingRegistry);
    }

    /**
     * Some other cross-platform libraries do it in this order so here is a shortcut for that
     */
    public static <T> NaniteRegistry<T> createRegistry(Registry<T> backingRegistry, String modId) {
        return Services.PLATFORM.createRegistry(modId, backingRegistry);
    }
}
