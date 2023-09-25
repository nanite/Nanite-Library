package dev.nanite.nanitelibrary.neo.platform;

import dev.nanite.nanitelibrary.platform.Weirdness;
import net.minecraft.world.item.CreativeModeTab;

public class NeoWeirdness implements Weirdness {
    @Override
    public CreativeModeTab.Builder createItemGroupBuilder() {
        return CreativeModeTab.builder();
    }
}
