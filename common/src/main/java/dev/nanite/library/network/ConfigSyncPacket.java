package dev.nanite.library.network;

import de.marhali.json5.Json5;
import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Object;
import dev.nanite.library.NaniteLibrary;
import dev.nanite.library.core.network.ClientPacketContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.io.IOException;

public record ConfigSyncPacket(Json5Object config) implements CustomPacketPayload {
    public static final Type<ConfigSyncPacket> TYPE = new Type<>(NaniteLibrary.id("config_sync"));

    public static final StreamCodec<ByteBuf, ConfigSyncPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            ConfigSyncPacket::serializeConfig,
            ConfigSyncPacket::deserializeConfig
    );

    private static String serializeConfig(ConfigSyncPacket packet) {
        try {
            return new Json5().serialize(packet.config());
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize config", e);
        }
    }

    private static ConfigSyncPacket deserializeConfig(String json) {
        Json5Element element = new Json5().parse(json);
        if (element instanceof Json5Object obj) {
            return new ConfigSyncPacket(obj);
        }

        throw new RuntimeException("Expected Json5Object, got " + element.getClass().getSimpleName());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(ClientPacketContext clientPacketContext) {
        // This happens on the client. We want to update the config.
    }
}
