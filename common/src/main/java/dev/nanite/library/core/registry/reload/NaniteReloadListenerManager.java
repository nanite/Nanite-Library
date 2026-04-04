package dev.nanite.library.core.registry.reload;

import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
// TODO: Support Client vs Server reloading
public enum NaniteReloadListenerManager {

    INSTANCE;

    private final Map<Identifier, PreparableReloadListener> serverListeners = new HashMap<>();
    private final Map<Identifier, PreparableReloadListener> clientListeners = new HashMap<>();

    public void registerListener(ReloadType reloadType, Identifier id, PreparableReloadListener reloadListener){
        if(reloadType == ReloadType.CLIENT){
            clientListeners.put(id, reloadListener);
        } else {
            serverListeners.put(id, reloadListener);
        }
    }

    public Map<Identifier, PreparableReloadListener> getListeners(ReloadType reloadType){
        return reloadType == ReloadType.CLIENT ? clientListeners : serverListeners;
    }

    public Stream<PreparableReloadListener> stream(ReloadType reloadType){
        Map<Identifier, PreparableReloadListener> listeners = reloadType == ReloadType.CLIENT ? clientListeners : serverListeners;
        return listeners.values().stream();
    }

    public enum ReloadType {
        CLIENT,
        SERVER;
    }
}
