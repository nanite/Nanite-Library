package dev.nanite.nanitelibrary.neo.core.registry;

import com.google.common.collect.ImmutableList;
import dev.nanite.nanitelibrary.core.registry.NaniteRegistry;
import dev.nanite.nanitelibrary.core.registry.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class NeoRegistry<T> implements NaniteRegistry<T> {
    private final String modId;
    private final List<RegistryHolder<T>> entries = new LinkedList<>();
    private final DeferredRegister<T> registry;

    public NeoRegistry(String modId, ResourceKey<? extends Registry<T>> backingRegistry) {
        this.modId = modId;
        registry = DeferredRegister.create(backingRegistry, modId);
    }

    /**
     * Pass the actual registry over to the mod
     */
    @Override
    public void initialize() {
        registry.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @Override
    public RegistryHolder<T> register(String id, Supplier<T> value) {
        NeoRegistryHolder<T> holder = new NeoRegistryHolder<>(new ResourceLocation(this.modId, id), registry.register(id, value));
        entries.add(holder);
        return holder;
    }

    @Override
    public ImmutableList<RegistryHolder<T>> entries() {
        return ImmutableList.copyOf(entries);
    }

    @Override
    public TagKey<T> createTagKey(String id) {
        return registry.createTagKey(id);
    }
}
