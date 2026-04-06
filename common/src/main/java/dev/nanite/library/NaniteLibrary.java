package dev.nanite.library;

import dev.nanite.library.core.config.Config;
import dev.nanite.library.core.config.ConfigManager;
import dev.nanite.library.core.config.ConfigType;
import dev.nanite.library.network.ConfigSyncPacket;
import dev.nanite.library.platform.Platform;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
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
        if (entity.level().isClientSide()) {
            return;
        }

        // Sync the 'common' configs back to the client from the server to ensure both sides have the same values.
        for (Config config : ConfigManager.get().getConfigsByType(ConfigType.COMMON)) {
            // TODO: Pipe in how this will send.
            Platform.INSTANCE.sendPacketToPlayer((ServerPlayer) entity, new ConfigSyncPacket(config));
        }
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
