package dev.nanite.library.fabric.core.network;

import dev.nanite.library.core.network.ExecutionTarget;
import dev.nanite.library.core.network.NetworkRegistry;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class NetworkRegistryFabric implements NetworkRegistry {
    @Override
    public <T extends CustomPacketPayload> void play2Server(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ServerHandler<T> handler, ExecutionTarget target) {
        PayloadTypeRegistry.serverboundPlay().register(type, streamCodec);
        ServerPlayNetworking.registerGlobalReceiver(type, ((payload, context) -> {
            var wrappedContext = new ServerPacketContextFabric(context);
            // Packet thread execution targets are ignored on fabric.
            handler.onHandle(payload, wrappedContext);
        }));
    }

    @Override
    public <T extends CustomPacketPayload> void play2Client(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> handler, ExecutionTarget target) {
        PayloadTypeRegistry.clientboundPlay().register(type, streamCodec);
        ClientPlayNetworking.registerGlobalReceiver(type, ((payload, context) -> {
            var wrappedContext = new ClientPacketContextFabric(context);
            handler.onHandle(payload, wrappedContext);
        }));
    }

    @Override
    public <T extends CustomPacketPayload> void playBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> clientHandler, ServerHandler<T> serverHandler, ExecutionTarget target) {
        // Hacky but it's 100% valid from what I can tell.
        this.play2Client(type, streamCodec, clientHandler, target);
        this.play2Server(type, streamCodec, serverHandler, target);
    }
}
