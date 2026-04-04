package dev.nanite.library.neo.platform;

import dev.nanite.library.platform.Weirdness;
import net.minecraft.world.item.CreativeModeTab;

public class NeoWeirdness implements Weirdness {
    @Override
    public CreativeModeTab.Builder createItemGroupBuilder() {
        return CreativeModeTab.builder();
    }
}
