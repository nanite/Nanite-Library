package dev.nanite.library.fabric.platform;

import dev.nanite.library.core.registry.NaniteRegistry;
import dev.nanite.library.fabric.core.registry.FabricRegistry;
import dev.nanite.library.platform.Weirdness;
import dev.nanite.library.platform.Platform;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;

import java.nio.file.Path;

public class FabricPlatformHelper implements Platform {
    private final FabricWeirdness weirdness = new FabricWeirdness();

    @Override
    public <T> NaniteRegistry<T> createRegistry(String modId, Registry<T> backingRegistry) {
        return new FabricRegistry<T>(modId, backingRegistry);
    }

    @Override
    public Weirdness weirdness() {
        return weirdness;
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
    public Path gamePath() {
        return FabricLoader.getInstance().getGameDir();
    }

    @Override
    public Path configPath() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
