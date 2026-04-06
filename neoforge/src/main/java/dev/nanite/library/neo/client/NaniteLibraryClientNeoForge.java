package dev.nanite.library.neo.client;

import dev.nanite.library.NaniteLibrary;
import dev.nanite.library.client.NaniteLibraryClient;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Mod(value = NaniteLibrary.MOD_ID, dist = Dist.CLIENT)
public class NaniteLibraryClientNeoForge {
    public static final Map<Identifier, PreparableReloadListener> reloadListeners = Collections.synchronizedMap(new HashMap<>());

    private final NaniteLibraryClient libraryClient;

    public NaniteLibraryClientNeoForge() {
        libraryClient = new NaniteLibraryClient();

        NeoForge.EVENT_BUS.addListener(this::addReloadListeners);
    }

    private void addReloadListeners(AddClientReloadListenersEvent event) {
        reloadListeners.forEach(event::addListener);
    }
}
