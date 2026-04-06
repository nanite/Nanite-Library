package dev.nanite.library.neo.core.network;

import dev.nanite.library.core.network.ServerPacketContext;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;

public class ServerPacketContextNeoForge implements ServerPacketContext {
    private final IPayloadContext payloadContext;

    public ServerPacketContextNeoForge(IPayloadContext payloadContext) {
        //noinspection UnstableApiUsage
        if (!(payloadContext instanceof ServerPayloadContext)) {
            throw new IllegalArgumentException("Expected a ServerPayloadContext, but got: " + payloadContext.getClass().getName());
        }

        this.payloadContext = payloadContext;
    }

    @Override
    public MinecraftServer server() {
        // TODO: Validate this one.
        return this.player().level().getServer();
    }

    @Override
    public ServerPlayer player() {
        return (ServerPlayer) this.payloadContext.player();
    }
}
