package dev.nanite.library.fabric.client.platform;

import dev.nanite.library.client.platform.PlatformClient;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;

import java.util.Map;

public class PlatformClientFabric implements PlatformClient {
    @Override
    public void sendPacketToServer(CustomPacketPayload packet) {
        if (ClientPlayNetworking.canSend(packet.type())) {
            ClientPlayNetworking.send(packet);
        }
    }

    @Override
    public void registerResourcePackReloadListeners(Map<Identifier, PreparableReloadListener> listeners) {
        listeners.forEach((id, listener) ->
                ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloadListener(id, listener));
    }
}
