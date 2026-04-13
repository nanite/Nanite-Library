package dev.nanite.library.platform;

import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.ApiStatus;

public interface Weirdness {
    /// Use [dev.nanite.library.core.weirdness.CreativeModeTabBuilder] instead of this, as it is more powerful and works on all platforms.
    @ApiStatus.Internal
    CreativeModeTab.Builder createVanillaCreativeModeTabBuilder();
}
