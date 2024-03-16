package dev.nanite.testforge;

import dev.nanite.nanitelibrary.core.config.NaniteConfigManager;
import dev.nanite.testforge.config.TestForgeConfig;
import net.minecraftforge.fml.common.Mod;

@Mod("testforge")
public class TestForgeMod {
    private TestForgeConfig CONFIG;
    public TestForgeMod() {
        System.out.println("TestForgeMod");

        CONFIG = new TestForgeConfig();
        NaniteConfigManager.INSTANCE.registerConfig(CONFIG.getNaniteConfig());
        System.out.println(CONFIG.testString.getValue());
        System.out.println(CONFIG.intConfig.getValue());
        System.out.println(CONFIG.serverPlayerCount.getValue());
    }
}
