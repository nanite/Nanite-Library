package dev.nanite.library.client.platform;

import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;

import java.util.Map;
import java.util.ServiceLoader;

public interface PlatformClient {
    PlatformClient INSTANCE = ServiceLoader.load(PlatformClient.class)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No implementation of PlatformClient found!"));

    void registerResourcePackReloadListeners(Map<Identifier, PreparableReloadListener> listeners);
}
