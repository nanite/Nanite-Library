package dev.nanite.library.neo.client.platform;

import dev.nanite.library.client.platform.PlatformClient;
import dev.nanite.library.neo.client.NaniteLibraryClientNeoForge;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;

import java.util.Map;

public class PlatformClientNeoForge implements PlatformClient {
    @Override
    public void registerResourcePackReloadListeners(Map<Identifier, PreparableReloadListener> listeners) {
        listeners.putAll(NaniteLibraryClientNeoForge.reloadListeners);
    }
}
