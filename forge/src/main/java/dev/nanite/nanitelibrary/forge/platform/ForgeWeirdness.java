package dev.nanite.nanitelibrary.forge.platform;

import dev.nanite.nanitelibrary.platform.Weirdness;
import net.minecraft.world.item.CreativeModeTab;

public class ForgeWeirdness implements Weirdness {
    @Override
    public CreativeModeTab.Builder createItemGroupBuilder() {
        return CreativeModeTab.builder();
    }
}
