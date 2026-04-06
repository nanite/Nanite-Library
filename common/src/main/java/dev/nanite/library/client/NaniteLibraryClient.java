package dev.nanite.library.client;

import dev.nanite.library.core.config.ConfigManager;
import dev.nanite.library.core.config.ConfigType;

public class NaniteLibraryClient {
    public NaniteLibraryClient() {
        // TODO: We might need to mixin to do this sooner!
        ConfigManager.get().loadConfigs(ConfigType.CLIENT);
    }
}
