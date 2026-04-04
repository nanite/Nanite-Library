package dev.nanite.library.neo.platform;

import dev.nanite.library.core.registry.NaniteRegistry;
import dev.nanite.library.neo.core.registry.NeoRegistry;
import dev.nanite.library.platform.Weirdness;
import dev.nanite.library.platform.services.IPlatformHelper;
import net.minecraft.core.Registry;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class NeoPlatformHelper implements IPlatformHelper {
    private static final NeoWeirdness WEIRDNESS = new NeoWeirdness();

    @Override
    public <T> NaniteRegistry<T> createRegistry(String modId, Registry<T> backingRegistry) {
        return new NeoRegistry<>(modId, backingRegistry.key());
    }

    @Override
    public Weirdness weirdness() {
        return WEIRDNESS;
    }

    @Override
    public String getPlatformName() {
        return "Neo";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public Path gamePath() {
        return FMLPaths.GAMEDIR.get();
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLEnvironment.isProduction();
    }
}
