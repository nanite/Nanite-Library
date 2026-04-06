package dev.nanite.library.fabric.platform;

import dev.nanite.library.platform.Weirdness;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.world.item.CreativeModeTab;

public class FabricWeirdness implements Weirdness {
    @Override
    public CreativeModeTab.Builder createItemGroupBuilder() {
        return FabricCreativeModeTab.builder();
    }
}
