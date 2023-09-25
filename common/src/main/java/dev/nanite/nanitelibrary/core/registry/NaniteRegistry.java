package dev.nanite.nanitelibrary.core.registry;

import com.google.common.collect.ImmutableList;
import net.minecraft.tags.TagKey;

import java.util.function.Supplier;
import java.util.stream.Stream;

public interface NaniteRegistry<T> {
    void initialize();

    RegistryHolder<T> register(String id, Supplier<T> value);

    ImmutableList<RegistryHolder<T>> entries();

    default Stream<RegistryHolder<T>> stream() {
        return entries().stream();
    }

    TagKey<T> createTagKey(String id);
}
