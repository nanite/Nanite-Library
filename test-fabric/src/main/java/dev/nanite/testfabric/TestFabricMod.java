package dev.nanite.testfabric;

import dev.nanite.nanitelibrary.core.NaniteCore;
import dev.nanite.nanitelibrary.core.registry.NaniteRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

public class TestFabricMod implements ModInitializer {
    public TestFabricMod() {
        System.out.println("TestForgeMod");
    }

    @Override
    public void onInitialize() {
        System.out.println("TestFabricMod");
        NaniteRegistry<Block> testfabric = NaniteCore.createRegistry(BuiltInRegistries.BLOCK, "testfabric");
        System.out.println(testfabric);
    }
}
