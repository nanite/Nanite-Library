package dev.nanite.testforge.config;

import dev.nanite.nanitelibrary.core.config.NaniteConfig;

public class TestForgeConfig {

    private final NaniteConfig naniteConfig;
    public final dev.nanite.nanitelibrary.core.config.NaniteConfigValue<String> testString;
    public final dev.nanite.nanitelibrary.core.config.NaniteConfigValue<Integer> intConfig;
    public final dev.nanite.nanitelibrary.core.config.NaniteConfigValue<Integer> serverPlayerCount;

    public TestForgeConfig() {
        naniteConfig = new NaniteConfig.Builder().name("nanite-test-config").build();
        testString = naniteConfig.newStringProperty("testString", "Default Value!", "Test String");
        intConfig = naniteConfig.newIntegerProperty("testInteger", 0, "Test Integer");
        serverPlayerCount = naniteConfig.newIntegerProperty("server.players.maxCount", 20, "A test integer for the amount of players on server.");
    }

    public NaniteConfig getNaniteConfig() {
        return naniteConfig;
    }
}
