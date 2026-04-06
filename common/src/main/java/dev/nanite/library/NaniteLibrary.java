package dev.nanite.library;

import dev.nanite.library.core.config.ConfigManager;
import dev.nanite.library.core.config.ConfigType;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;

public class NaniteLibrary {
    public static final String MOD_ID = "nanitelibrary";

    public NaniteLibrary() {
        // TODO: We might need to mixin to do this sooner!
        ConfigManager.get().loadConfigs(ConfigType.COMMON);
    }

    public void onServerStarting(MinecraftServer server) {
        ConfigManager.get().loadConfigs(ConfigType.SERVER);
    }

    public void onPlayerJoin(Player entity) {
        // Sync the 'common' configs back to the client from the server to ensure both sides have the same values.
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
