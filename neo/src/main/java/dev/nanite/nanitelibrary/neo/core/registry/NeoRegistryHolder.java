package dev.nanite.nanitelibrary.neo.core.registry;

import dev.nanite.nanitelibrary.core.registry.RegistryHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

public class NeoRegistryHolder<T> implements RegistryHolder<T> {
    RegistryObject<T> value;

    public NeoRegistryHolder(RegistryObject<T> value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value.get();
    }

    @Override
    public ResourceLocation identifier() {
        return value.getId();
    }
}
