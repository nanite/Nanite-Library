package dev.nanite.nanitelibrary.forge.core.registry;

import com.google.common.collect.ImmutableList;
import dev.nanite.nanitelibrary.core.registry.NaniteRegistry;
import dev.nanite.nanitelibrary.core.registry.RegistryHolder;
import dev.nanite.nanitelibrary.forge.core.registry.ForgeRegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class ForgeRegistry<T> implements NaniteRegistry<T> {
    List<RegistryHolder<T>> entries;
    DeferredRegister<T> registry;

    public ForgeRegistry(String modId, ResourceKey<? extends Registry<T>> backingRegistry) {
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
        ForgeRegistryHolder<T> holder = new ForgeRegistryHolder<>(registry.register(id, value));
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
