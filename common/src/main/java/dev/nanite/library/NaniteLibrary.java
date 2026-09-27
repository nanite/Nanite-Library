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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NaniteLibrary {
    private static final Logger LOGGER = LoggerFactory.getLogger(NaniteLibrary.class);

    public static final String MOD_ID = "nanitelibrary";

    public NaniteLibrary() {
        Platform.INSTANCE.network().optional().play2Client(ConfigSyncPacket.TYPE, ConfigSyncPacket.STREAM_CODEC, ConfigSyncPacket::handle);
    }

    /// Fired before worlds load, on both the dedicated and integrated server.
    public void onServerStarting(MinecraftServer server) {
        ConfigManager.get().loadConfigs(ConfigType.SERVER);
        ConfigManager.get().loadConfigs(ConfigType.COMMON);
    }

    public void onPlayerJoin(Player entity) {
        if (entity.level().isClientSide()) {
            return;
        }

        // Sync the 'common' configs back to the client from the server to ensure both sides have the same values.
        for (Config config : ConfigManager.get().getConfigsByType(ConfigType.COMMON)) {
            Platform.INSTANCE.sendPacketToPlayer((ServerPlayer) entity, new ConfigSyncPacket(config.fileName(), config.getContainer().getData().deepCopy()));
        }
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
