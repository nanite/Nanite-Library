package dev.nanite.library.client.platform;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;

import java.util.Map;
import java.util.ServiceLoader;

public interface PlatformClient {
    PlatformClient INSTANCE = ServiceLoader.load(PlatformClient.class)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No implementation of PlatformClient found!"));

    void sendPacketToServer(CustomPacketPayload packet);

    void registerResourcePackReloadListeners(Map<Identifier, PreparableReloadListener> listeners);
}
