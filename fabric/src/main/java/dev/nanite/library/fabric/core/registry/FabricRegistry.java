package dev.nanite.library.fabric.core.registry;

import com.google.common.collect.ImmutableList;
import dev.nanite.library.core.registry.NaniteRegistry;
import dev.nanite.library.core.registry.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class FabricRegistry<T> implements NaniteRegistry<T> {
    private final String modId;
    private final Registry<T> backingRegistry;
    private final List<FabricRegistryHolder<T, ? extends T>> entries = new ArrayList<>();
    private boolean initialized = false;

    public FabricRegistry(String modId, Registry<T> backingRegistry) {
        this.modId = modId;
        this.backingRegistry = backingRegistry;
    }

    @Override
    public void initialize() {
        if (this.initialized) {
            throw new IllegalStateException("Registry " + this.backingRegistry.key().identifier() + " for " + this.modId + " has already been initialized");
        }

        this.initialized = true;
        for (FabricRegistryHolder<T, ? extends T> holder : this.entries) {
            holder.bind(this.backingRegistry);
        }
    }

    @Override
    public <I extends T> RegistryHolder<T, I> register(String id, Supplier<I> value) {
        FabricRegistryHolder<T, I> holder = new FabricRegistryHolder<>(Identifier.fromNamespaceAndPath(modId, id), value);
        return this.register(holder);
    }

    @Override
    public <I extends T> RegistryHolder<T, I> registerPassId(String id, Function<Identifier, I> value) {
        var identifier = Identifier.fromNamespaceAndPath(modId, id);
        FabricRegistryHolder<T, I> holder = new FabricRegistryHolder<>(identifier, () -> value.apply(identifier));
        return this.register(holder);
    }

    @Override
    public <I extends T> RegistryHolder<T, I> registerPassKey(String id, Function<ResourceKey<T>, I> value) {
        var resourceKey = ResourceKey.create(backingRegistry.key(), Identifier.fromNamespaceAndPath(modId, id));
        FabricRegistryHolder<T, I> holder = new FabricRegistryHolder<>(resourceKey.identifier(), () -> value.apply(resourceKey));
        return this.register(holder);
    }

    private <I extends T> RegistryHolder<T, I> register(FabricRegistryHolder<T, I> holder) {
        if (this.initialized) {
            throw new IllegalStateException("Cannot register " + holder.identifier() + " after the registry has been initialized. " +
                    "Make sure the class holding it is loaded before calling initialize()");
        }

        entries.add(holder);
        return holder;
    }

    @Override
    public ImmutableList<RegistryHolder<T, ? extends T>> entries() {
        return ImmutableList.<RegistryHolder<T, ? extends T>>builder().addAll(entries).build();
    }

    /**
     * Creates a tag key for the registry with the mod id automatically prepended.
     */
    @Override
    public TagKey<T> createTagKey(String id) {
        return TagKey.create(this.backingRegistry.key(), Identifier.fromNamespaceAndPath(modId, id));
    }
}
