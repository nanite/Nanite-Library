package dev.nanite.nanitelibrary.core.configshit;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public interface NaniteConfigType<T> {
    ResourceLocation name();

    List<NaniteConfigLifecycles> supportedLifecycles();

    T deserialize(String string, NaniteConfigLifecycleContext context);

    String serialize(T value, NaniteConfigLifecycleContext context);
}
