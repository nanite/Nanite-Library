package dev.nanite.nanitelibrary.core.registry.reload;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
// TODO: Support Client vs Server reloading
public enum NaniteReloadListenerManager {

    INSTANCE;

    private final Map<ResourceLocation, PreparableReloadListener> serverListeners = new HashMap<>();
    private final Map<ResourceLocation, PreparableReloadListener> clientListeners = new HashMap<>();

    public void registerListener(ReloadType reloadType, ResourceLocation id, PreparableReloadListener reloadListener){
        if(reloadType == ReloadType.CLIENT){
            clientListeners.put(id, reloadListener);
        } else {
            serverListeners.put(id, reloadListener);
        }
    }

    public Map<ResourceLocation, PreparableReloadListener> getListeners(ReloadType reloadType){
        return reloadType == ReloadType.CLIENT ? clientListeners : serverListeners;
    }

    public Stream<PreparableReloadListener> stream(ReloadType reloadType){
        Map<ResourceLocation, PreparableReloadListener> listeners = reloadType == ReloadType.CLIENT ? clientListeners : serverListeners;
        return listeners.values().stream();
    }

    public enum ReloadType {
        CLIENT,
        SERVER;
    }
}
