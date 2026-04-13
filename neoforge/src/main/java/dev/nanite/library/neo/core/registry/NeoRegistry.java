package dev.nanite.library.neo.core.registry;

import com.google.common.collect.ImmutableList;
import dev.nanite.library.core.registry.NaniteRegistry;
import dev.nanite.library.core.registry.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class NeoRegistry<T> implements NaniteRegistry<T> {
    private final String modId;
    private final List<RegistryHolder<T, ? extends T>> entries = new LinkedList<>();
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
        registry.register(ModList.get().getModContainerById(this.modId)
                .map(ModContainer::getEventBus).orElseThrow());
    }

    @Override
    public <I extends T> RegistryHolder<T, I> register(String id, Supplier<I> value) {
        NeoRegistryHolder<T, I> holder = new NeoRegistryHolder<>(Identifier.fromNamespaceAndPath(this.modId, id), registry.register(id, value));
        return register(holder);
    }

    @Override
    public <I extends T> RegistryHolder<T, I> register(String id, ConsumeWithIdentifier<I> value) {
        var identifier = Identifier.fromNamespaceAndPath(this.modId, id);
        NeoRegistryHolder<T, I> holder = new NeoRegistryHolder<>(identifier, registry.register(id, value));
        return register(holder);
    }

    @Override
    public <I extends T> RegistryHolder<T, I> register(String id, ConsumeWithResourceKey<I, T> value) {
        var resourceKey = ResourceKey.create(registry.getRegistryKey(), Identifier.fromNamespaceAndPath(this.modId, id));
        NeoRegistryHolder<T, I> holder = new NeoRegistryHolder<>(resourceKey.identifier(), registry.register(id, () -> value.apply(resourceKey)));
        return register(holder);
    }

    private <I extends T> RegistryHolder<T, I> register(NeoRegistryHolder<T, I> holder) {
        entries.add(holder);
        return holder;
    }

    @Override
    public ImmutableList<RegistryHolder<T, ? extends T>> entries() {
        return ImmutableList.copyOf(entries);
    }

    @Override
    public TagKey<T> createTagKey(String id) {
        return registry.createTagKey(id);
    }
}
