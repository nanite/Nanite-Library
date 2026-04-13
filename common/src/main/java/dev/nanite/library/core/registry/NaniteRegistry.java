package dev.nanite.library.core.registry;

import com.google.common.collect.ImmutableList;
import dev.nanite.library.platform.Platform;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface NaniteRegistry<T> {
    static <T> NaniteRegistry<T> create(String modId, Registry<T> registry) {
        return Platform.INSTANCE.createRegistry(modId, registry);
    }

    void initialize();

    <I extends T> RegistryHolder<T, I> register(String id, Supplier<I> value);

    <I extends T> RegistryHolder<T, I> register(String id, ConsumeWithIdentifier<I> value);

    <I extends T> RegistryHolder<T, I> register(String id, ConsumeWithResourceKey<I, T> value);

    ImmutableList<RegistryHolder<T, ? extends T>> entries();

    default Stream<RegistryHolder<T, ? extends T>> stream() {
        return entries().stream();
    }

    TagKey<T> createTagKey(String id);

    @FunctionalInterface
    interface ConsumeWithIdentifier<T> extends Function<Identifier, T> {}

    @FunctionalInterface
    interface ConsumeWithResourceKey<I extends T, T> extends Function<ResourceKey<T>, I> {}
}
