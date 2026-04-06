package dev.nanite.library.neo.client.platform;

import dev.nanite.library.client.platform.PlatformClient;
import dev.nanite.library.neo.client.NaniteLibraryClientNeoForge;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.Map;

public class PlatformClientNeoForge implements PlatformClient {
    @Override
    public void sendPacketToServer(CustomPacketPayload packet) {
        ClientPacketDistributor.sendToServer(packet);
    }

    @Override
    public void registerResourcePackReloadListeners(Map<Identifier, PreparableReloadListener> listeners) {
        listeners.putAll(NaniteLibraryClientNeoForge.reloadListeners);
    }
}
