package dev.nanite.library.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/// Network packet registry used to register custom packets for various directions.
///
/// Handlers always run on the main thread of the receiving side.
/// Decoding happens beforehand on the network thread, so any heavy parsing belongs in the `StreamCodec`.
public interface NetworkRegistry {
    /// The version packets are registered with unless [#versioned(String)] is used.
    String DEFAULT_VERSION = "1";

    NetworkRegistry versioned(String version);

    NetworkRegistry optional();

    /// Registers a custom packet to be sent from the client to the server.
    <T extends CustomPacketPayload> void play2Server(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ServerHandler<T> handler);

    /// @deprecated The execution target is ignored, use [#play2Server(CustomPacketPayload.Type, StreamCodec, ServerHandler)].
    @Deprecated(forRemoval = true)
    @SuppressWarnings("removal")
    default <T extends CustomPacketPayload> void play2Server(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ServerHandler<T> handler, ExecutionTarget target) {
        play2Server(type, streamCodec, handler);
    }

    /// Registers a custom packet to be sent from the server to the client.
    <T extends CustomPacketPayload> void play2Client(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> handler);

    /// @deprecated The execution target is ignored, use [#play2Client(CustomPacketPayload.Type, StreamCodec, ClientHandler)].
    @Deprecated(forRemoval = true)
    @SuppressWarnings("removal")
    default <T extends CustomPacketPayload> void play2Client(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> handler, ExecutionTarget target) {
        play2Client(type, streamCodec, handler);
    }

    /// Registers a custom packet to be sent in both directions.
    <T extends CustomPacketPayload> void playBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> clientHandler, ServerHandler<T> serverHandler);

    /// @deprecated The execution target is ignored, use [#playBidirectional(CustomPacketPayload.Type, StreamCodec, ClientHandler, ServerHandler)].
    @Deprecated(forRemoval = true)
    @SuppressWarnings("removal")
    default <T extends CustomPacketPayload> void playBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> clientHandler, ServerHandler<T> serverHandler, ExecutionTarget target) {
        playBidirectional(type, streamCodec, clientHandler, serverHandler);
    }

    @FunctionalInterface
    interface ServerHandler<T> {
        void onHandle(T payload, ServerPacketContext context);
    }

    @FunctionalInterface
    interface ClientHandler<T> {
        void onHandle(T payload, ClientPacketContext context);
    }
}
