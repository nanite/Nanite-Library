package dev.nanite.nanitelibrary;

import dev.nanite.nanitelibrary.core.config.NaniteConfigManager;
import dev.nanite.nanitelibrary.core.creative.NaniteCreativeTab;
import dev.nanite.nanitelibrary.core.registry.reload.NaniteReloadListenerManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class NaniteLibrary {
    public static void init() {
        NaniteCreativeTab.REGISTRY.initialize();
        NaniteReloadListenerManager.INSTANCE.registerListener(NaniteReloadListenerManager.ReloadType.SERVER,
                new ResourceLocation(Constants.MOD_ID, "config-reloader"),
                (preparationBarrier, resourceManager, profilerFiller, profilerFiller2, executor, executor2) -> {
            NaniteConfigManager.INSTANCE.reloadAll();
            return preparationBarrier.wait(null);
        });
    }
}
