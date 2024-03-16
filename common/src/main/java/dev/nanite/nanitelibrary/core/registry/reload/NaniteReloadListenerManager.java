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

    private Map<ResourceLocation, PreparableReloadListener> listeners = new HashMap<>();

    public void registerListener(ResourceLocation id, PreparableReloadListener reloadListener){
        listeners.put(id, reloadListener);
    }

    public Map<ResourceLocation, PreparableReloadListener> getListeners(){
        return this.listeners;
    }

    public Stream<PreparableReloadListener> stream(){
        return this.listeners.values().stream();
    }
}
