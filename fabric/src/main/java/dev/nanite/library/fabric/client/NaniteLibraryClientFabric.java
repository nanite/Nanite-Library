package dev.nanite.library.fabric.client;

import dev.nanite.library.client.NaniteLibraryClient;
import net.fabricmc.api.ClientModInitializer;

public class NaniteLibraryClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        var libraryClient = new NaniteLibraryClient();
    }
}
