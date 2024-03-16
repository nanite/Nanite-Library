package dev.nanite.testfabric;

import dev.nanite.nanitelibrary.core.NaniteCore;
import dev.nanite.nanitelibrary.core.config.NaniteConfigManager;
import dev.nanite.nanitelibrary.core.registry.NaniteRegistry;
import dev.nanite.testfabric.config.TestFabricConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

public class TestFabricMod implements ModInitializer {

    private TestFabricConfig CONFIG;

    public TestFabricMod() {
        System.out.println("TestFabricMod");
    }

    @Override
    public void onInitialize() {
        try {
            CONFIG = new TestFabricConfig();
            NaniteConfigManager.INSTANCE.registerConfig(CONFIG.getNaniteConfig());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TestFabricMod");
        NaniteRegistry<Block> testfabric = NaniteCore.createRegistry(BuiltInRegistries.BLOCK, "testfabric");
        System.out.println(testfabric);
        System.out.println(CONFIG.testString.getValue());
        System.out.println(CONFIG.intConfig.getValue());
    }
}
