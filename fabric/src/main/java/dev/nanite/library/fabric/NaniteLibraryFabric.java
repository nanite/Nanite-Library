package dev.nanite.library.fabric;

import dev.nanite.library.core.registry.reload.NaniteReloadListenerManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.nanite.library.NaniteLibrary;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class NaniteLibraryFabric implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NaniteLibraryFabric.class);

    @Override
    public void onInitialize() {
        NaniteLibrary.init();
        LOGGER.debug("Registering reload listeners");
        // TODO: Fix me
//        for (Map.Entry<ResourceLocation, PreparableReloadListener> entry : NaniteReloadListenerManager.INSTANCE.getListeners(NaniteReloadListenerManager.ReloadType.SERVER).entrySet()) {
//            ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new IdentifiableResourceReloadListener() {
//                @Override
//                public ResourceLocation getFabricId() {
//                    return entry.getKey();
//                }
//
//                @Override
//                public CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller profilerFiller, ProfilerFiller profilerFiller2, Executor executor, Executor executor2) {
//                    return entry.getValue().reload(preparationBarrier, resourceManager, profilerFiller, profilerFiller2, executor, executor2);
//                }
//            });
//        }
    }
}
