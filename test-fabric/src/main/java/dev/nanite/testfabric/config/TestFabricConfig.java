package dev.nanite.testfabric.config;

import dev.nanite.nanitelibrary.core.config.NaniteConfig;
import dev.nanite.nanitelibrary.core.configshit.NaniteConfigSpec;
import dev.nanite.nanitelibrary.core.configshit.NaniteConfigValue;

public class TestFabricConfig {

    private final NaniteConfig naniteConfig;
    public final dev.nanite.nanitelibrary.core.config.NaniteConfigValue<String> testString;
    public final dev.nanite.nanitelibrary.core.config.NaniteConfigValue<Integer> intConfig;
    public final dev.nanite.nanitelibrary.core.config.NaniteConfigValue<Integer> serverPlayerCount;

    public TestFabricConfig() {
        naniteConfig = new NaniteConfig.Builder().name("nanite-test-config").build();
        testString = naniteConfig.newStringProperty("testString", "Default Value!", "A test string");
        intConfig = naniteConfig.newIntegerProperty("testInteger", 0, "A test integer");
        serverPlayerCount = naniteConfig.newIntegerProperty("server.players.maxCount", 20, "Test value for max amount of players on server");
    }

    public NaniteConfig getNaniteConfig() {
        return naniteConfig;
    }
}
