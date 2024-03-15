package dev.nanite.nanitelibrary.forge.platform;

import dev.nanite.nanitelibrary.core.registry.NaniteRegistry;
import dev.nanite.nanitelibrary.forge.core.registry.ForgeRegistry;
import dev.nanite.nanitelibrary.platform.Weirdness;
import dev.nanite.nanitelibrary.platform.services.IPlatformHelper;
import net.minecraft.core.Registry;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.nio.file.Path;

public class ForgePlatformHelper implements IPlatformHelper {
    private static final ForgeWeirdness WEIRDNESS = new ForgeWeirdness();

    @Override
    public <T> NaniteRegistry<T> createRegistry(String modId, Registry<T> backingRegistry) {
        return new ForgeRegistry<>(modId, backingRegistry.key());
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
        return FMLLoader.getGamePath();
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }
}
