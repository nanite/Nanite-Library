package dev.nanite.nanitelibrary.neo.platform;

import dev.nanite.nanitelibrary.core.registry.NaniteRegistry;
import dev.nanite.nanitelibrary.neo.core.registry.NeoRegistry;
import dev.nanite.nanitelibrary.platform.Weirdness;
import dev.nanite.nanitelibrary.platform.services.IPlatformHelper;
import net.minecraft.core.Registry;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

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
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }
}
