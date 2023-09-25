package dev.nanite.nanitelibrary.fabric.platform;

import dev.nanite.nanitelibrary.core.registry.NaniteRegistry;
import dev.nanite.nanitelibrary.fabric.core.registry.FabricRegistry;
import dev.nanite.nanitelibrary.platform.Weirdness;
import dev.nanite.nanitelibrary.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;

public class FabricPlatformHelper implements IPlatformHelper {
    private static final FabricWeirdness WEIRDNESS = new FabricWeirdness();

    @Override
    public <T> NaniteRegistry<T> createRegistry(String modId, Registry<T> backingRegistry) {
        return new FabricRegistry<T>(modId, backingRegistry);
    }

    @Override
    public Weirdness weirdness() {
        return WEIRDNESS;
    }

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
