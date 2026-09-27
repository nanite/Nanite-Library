package dev.nanite.library.fabric.client;

import dev.nanite.library.client.NaniteLibraryClient;
import dev.nanite.library.fabric.client.network.ClientNetworkingFabric;
import dev.nanite.library.fabric.core.network.NetworkRegistryFabric;
import dev.nanite.library.platform.Platform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class NaniteLibraryClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientNetworkingFabric.bind((NetworkRegistryFabric) Platform.INSTANCE.network());

        var libraryClient = new NaniteLibraryClient();
        ClientPlayConnectionEvents.DISCONNECT.register((listener, client) -> libraryClient.onDisconnect());
    }
}
