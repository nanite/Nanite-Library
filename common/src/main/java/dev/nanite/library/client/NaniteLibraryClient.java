package dev.nanite.library.client;

import dev.nanite.library.core.config.ConfigManager;
import dev.nanite.library.core.config.ConfigType;
import net.minecraft.client.Minecraft;

public class NaniteLibraryClient {
    public NaniteLibraryClient() {
        // TODO: We might need to mixin to do this sooner!
        ConfigManager.get().loadConfigs(ConfigType.CLIENT);
    }

    /// Restores common configs that were overridden by a remote server.
    public void onDisconnect() {
        Minecraft.getInstance().execute(() -> ConfigManager.get().restoreSyncedConfigs());
    }
}
