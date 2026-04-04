package dev.nanite.library;

import dev.nanite.library.core.creative.NaniteCreativeTab;
import dev.nanite.library.core.registry.reload.NaniteReloadListenerManager;
import net.minecraft.resources.Identifier;

public class NaniteLibrary {
    public static void init() {
        NaniteCreativeTab.REGISTRY.initialize();
//        NaniteReloadListenerManager.INSTANCE.registerListener(NaniteReloadListenerManager.ReloadType.SERVER,
//                id("config-reloader"),
//                (preparationBarrier, resourceManager, profilerFiller, profilerFiller2, executor, executor2) -> {
////            NaniteConfigManager.INSTANCE.reloadAll();
//            return preparationBarrier.wait(null);
//        });
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Constants.MOD_ID, path);
    }
}
