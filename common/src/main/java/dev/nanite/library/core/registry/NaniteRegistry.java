package dev.nanite.library.core.registry;

import com.google.common.collect.ImmutableList;
import net.minecraft.tags.TagKey;

import java.util.function.Supplier;
import java.util.stream.Stream;

public interface NaniteRegistry<T> {
    void initialize();

    <I extends T> RegistryHolder<T, I> register(String id, Supplier<I> value);

    ImmutableList<RegistryHolder<T, ? extends T>> entries();

    default Stream<RegistryHolder<T, ? extends T>> stream() {
        return entries().stream();
    }

    TagKey<T> createTagKey(String id);
}
