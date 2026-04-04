package dev.nanite.library.fabric.core.registry;

import com.google.common.collect.ImmutableList;
import dev.nanite.library.core.registry.NaniteRegistry;
import dev.nanite.library.core.registry.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FabricRegistry<T> implements NaniteRegistry<T> {
    private final String modId;
    private final Registry<T> backingRegistry;
    private final List<RegistryHolder<T, ? extends T>> entries = new ArrayList<>();

    public FabricRegistry(String modId, Registry<T> backingRegistry) {
        this.modId = modId;
        this.backingRegistry = backingRegistry;
    }

    /**
     * Nothing happens here as fabric isn't stupid.
     */
    @Override
    public void initialize() {
        // No-op
    }

    @Override
    public <I extends T> RegistryHolder<T, I> register(String id, Supplier<I> value) {
        FabricRegistryHolder<T, I> holder = new FabricRegistryHolder<>(Identifier.fromNamespaceAndPath(modId, id), value.get());
        Registry.register(backingRegistry, holder.identifier(), holder.get());
        entries.add(holder);
        return holder;
    }

    @Override
    public ImmutableList<RegistryHolder<T, ? extends T>> entries() {
        return ImmutableList.copyOf(entries);
    }

    /**
     * Creates a tag key for the registry with the mod id automatically prepended.
     */
    @Override
    public TagKey<T> createTagKey(String id) {
        return TagKey.create(this.backingRegistry.key(), Identifier.fromNamespaceAndPath(modId, id));
    }
}
