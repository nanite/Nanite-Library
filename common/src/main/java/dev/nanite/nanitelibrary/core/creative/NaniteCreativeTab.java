package dev.nanite.nanitelibrary.core.creative;

import dev.nanite.nanitelibrary.Constants;
import dev.nanite.nanitelibrary.core.NaniteCore;
import dev.nanite.nanitelibrary.core.registry.NaniteRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;

public enum NaniteCreativeTab {
    INSTANCE;

    public static final NaniteRegistry<CreativeModeTab> REGISTRY = NaniteCore.createRegistry(Constants.MOD_ID, BuiltInRegistries.CREATIVE_MODE_TAB);
//    private static final RegistryHolder<CreativeModeTab> DEFAULT = REGISTRY.register("default", () -> Services.PLATFORM.weirdness().createItemGroup()
//
//            .build());
//
//    private static final RegistryHolder<CreativeModeTab> CREATIVE = REGISTRY.register("creative", () -> Services.PLATFORM.weirdness().createItemGroup()
//
//            .build());
//
//    private final List<ItemLike> creativeItems = new ArrayList<>();
//    private final List<ItemLike> defaultItems = new ArrayList<>();
//
//    public void addCreativeItem(ItemLike item) {
//        creativeItems.add(item);
//    }
//
//    public void addDefaultItem(ItemLike item) {
//        defaultItems.add(item);
//    }
}
