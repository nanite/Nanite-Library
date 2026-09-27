package dev.nanite.library.fabric.client.network;

import dev.nanite.library.fabric.core.network.NetworkRegistryFabric;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class ClientNetworkingFabric {
    public static void bind(NetworkRegistryFabric registry) {
        registry.bindClientReceivers(ClientNetworkingFabric::register);
    }

    private static <T extends CustomPacketPayload> void register(NetworkRegistryFabric.ClientReceiver<T> receiver) {
        ClientPlayNetworking.registerGlobalReceiver(receiver.type(), (payload, context) ->
                receiver.handler().onHandle(payload, new ClientPacketContextFabric(context)));
    }
}
