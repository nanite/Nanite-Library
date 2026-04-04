package dev.nanite.library.fabric.platform;

import dev.nanite.library.platform.Weirdness;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.world.item.CreativeModeTab;

public class FabricWeirdness implements Weirdness {

    @Override
    public CreativeModeTab.Builder createItemGroupBuilder() {
        return FabricItemGroup.builder();
    }
}
