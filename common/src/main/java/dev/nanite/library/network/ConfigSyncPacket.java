package dev.nanite.library.network;

import de.marhali.json5.Json5Object;
import dev.nanite.library.NaniteLibrary;
import dev.nanite.library.core.config.Config;
import dev.nanite.library.core.config.ConfigManager;
import dev.nanite.library.core.config.ConfigType;
import dev.nanite.library.core.network.ClientPacketContext;
import dev.nanite.library.utils.ExtraStreamCodecs;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public record ConfigSyncPacket(String fileName, Json5Object config) implements CustomPacketPayload {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigSyncPacket.class);

    public static final Type<ConfigSyncPacket> TYPE = new Type<>(NaniteLibrary.id("config_sync"));

    public static final StreamCodec<ByteBuf, ConfigSyncPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ConfigSyncPacket::fileName,
            ExtraStreamCodecs.JSON5_OBJECT, ConfigSyncPacket::config,
            ConfigSyncPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(ClientPacketContext clientPacketContext) {
        Optional<Config> selectedConfig = ConfigManager.get().getConfigsByType(ConfigType.COMMON).stream()
                .filter(e -> e.fileName().equals(fileName))
                .findFirst();

        if (selectedConfig.isEmpty()) {
            LOGGER.warn("Received config sync packet for unknown config file: {}", fileName);
            return;
        }

        selectedConfig.get().loadFromJsonPayload(config);
    }
}
