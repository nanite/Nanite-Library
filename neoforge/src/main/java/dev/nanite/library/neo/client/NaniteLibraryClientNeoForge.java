package dev.nanite.library.neo.client;

import dev.nanite.library.NaniteLibrary;
import dev.nanite.library.client.NaniteLibraryClient;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.HashMap;
import java.util.Map;

@Mod(value = NaniteLibrary.MOD_ID, dist = Dist.CLIENT)
public class NaniteLibraryClientNeoForge {
    private static final Map<Identifier, PreparableReloadListener> reloadListeners = new HashMap<>();
    private static boolean reloadListenersCollected = false;

    private final NaniteLibraryClient libraryClient;

    public NaniteLibraryClientNeoForge(IEventBus modEventBus) {
        libraryClient = new NaniteLibraryClient();

        modEventBus.addListener(this::onAddReloadListeners);
        NeoForge.EVENT_BUS.addListener((ClientPlayerNetworkEvent.LoggingOut event) -> libraryClient.onDisconnect());
    }

    // NeoForge freezes the client reload listeners after AddClientReloadListenersEvent, so late ones can't be applied
    public static synchronized void addReloadListeners(Map<Identifier, PreparableReloadListener> listeners) {
        if (reloadListenersCollected) {
            throw new IllegalStateException("Client reload listeners must be registered during mod construction on NeoForge: " + listeners.keySet());
        }

        reloadListeners.putAll(listeners);
    }

    private void onAddReloadListeners(AddClientReloadListenersEvent event) {
        synchronized (NaniteLibraryClientNeoForge.class) {
            reloadListenersCollected = true;
            reloadListeners.forEach(event::addListener);
        }
    }
}
