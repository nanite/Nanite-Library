package dev.nanite.testneo;

import dev.nanite.nanitelibrary.core.NaniteCore;
import dev.nanite.nanitelibrary.core.config.NaniteConfigManager;
import dev.nanite.testneo.config.TestNeoForgeConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.fml.common.Mod;

@Mod("testneo")
public class TestNeoMod {
    private TestNeoForgeConfig CONFIG;
    public TestNeoMod() {
        System.out.println("TestForgeMod");

        NaniteCore.createRegistry(BuiltInRegistries.BLOCK, "testneo");

        CONFIG = new TestNeoForgeConfig();
        NaniteConfigManager.INSTANCE.registerConfig(CONFIG.getNaniteConfig());
        System.out.println(CONFIG.testString.getValue());
        System.out.println(CONFIG.intConfig.getValue());
        System.out.println(CONFIG.serverPlayerCount.getValue());
    }
}
