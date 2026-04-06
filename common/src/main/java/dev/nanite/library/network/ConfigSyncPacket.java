package dev.nanite.library.network;

import de.marhali.json5.Json5Object;
import dev.nanite.library.NaniteLibrary;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ConfigSyncPacket(Json5Object config) implements CustomPacketPayload {
    public static final Type<ConfigSyncPacket> TYPE = new Type<>(NaniteLibrary.id("config_sync"));

    public static final StreamCodec<ByteBuf, ConfigSyncPacket> STREAM_CODEC = StreamCodec.composite(
            ConfigSyncPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
