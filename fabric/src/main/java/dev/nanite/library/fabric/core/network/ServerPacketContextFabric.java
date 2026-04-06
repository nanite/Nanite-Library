package dev.nanite.library.fabric.core.network;

import dev.nanite.library.core.network.ServerPacketContext;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class ServerPacketContextFabric implements ServerPacketContext {
    private final ServerPlayNetworking.Context context;

    public ServerPacketContextFabric(ServerPlayNetworking.Context context) {
        this.context = context;
    }

    @Override
    public MinecraftServer server() {
        return context.server();
    }

    @Override
    public ServerPlayer player() {
        return context.player();
    }
}
