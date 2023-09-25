package dev.nanite.nanitelibrary.fabric.platform;

import dev.nanite.nanitelibrary.platform.Weirdness;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.world.item.CreativeModeTab;

public class FabricWeirdness implements Weirdness {

    @Override
    public CreativeModeTab.Builder createItemGroupBuilder() {
        return FabricItemGroup.builder();
    }
}
