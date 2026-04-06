package dev.nanite.library.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/// Network packet registry used to register custom packets for various directions.
public interface NetworkRegistry {
    /// Registers a custom packet to be sent from the client to the server.
    /// This will, by default, execute the handler on the main thread, but you can specify a different execution target if needed.
    default <T extends CustomPacketPayload> void play2Server(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ServerHandler<T> handler) {
        play2Server(type, streamCodec, handler, ExecutionTarget.MAIN);
    }

    <T extends CustomPacketPayload> void play2Server(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ServerHandler<T> handler, ExecutionTarget target);

    /// Registers a custom packet to be sent from the server to the client.
    /// This will, by default, execute the handler on the main thread, but you can specify a different execution target if needed.
    default <T extends CustomPacketPayload> void play2Client(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> handler) {
        play2Client(type, streamCodec, handler, ExecutionTarget.MAIN);
    }

    <T extends CustomPacketPayload> void play2Client(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> handler, ExecutionTarget target);

    /// Registers a custom packet to be sent in both directions.
    /// This will, by default, execute the handler on the main thread, but you can specify a different execution target if needed.
    default <T extends CustomPacketPayload> void playBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> clientHandler, ServerHandler<T> serverHandler) {
        playBidirectional(type, streamCodec, clientHandler, serverHandler, ExecutionTarget.MAIN);
    }

    <T extends CustomPacketPayload> void playBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> clientHandler, ServerHandler<T> serverHandler, ExecutionTarget target);

    @FunctionalInterface
    interface ServerHandler<T> {
        void onHandle(T payload, ServerPacketContext context);
    }

    @FunctionalInterface
    interface ClientHandler<T> {
        void onHandle(T payload, ClientPacketContext context);
    }
}
