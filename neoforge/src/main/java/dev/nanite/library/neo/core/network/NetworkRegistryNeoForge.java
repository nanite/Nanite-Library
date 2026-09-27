package dev.nanite.library.neo.core.network;

import dev.nanite.library.core.network.ExecutionTarget;
import dev.nanite.library.core.network.NetworkRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class NetworkRegistryNeoForge implements NetworkRegistry {
    private boolean collected = false;

    private final List<RegisteredPacket<?>> packets = Collections.synchronizedList(new ArrayList<>());

    @Override
    public <T extends CustomPacketPayload> void play2Server(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ServerHandler<T> handler, ExecutionTarget target) {
        this.add(new RegisteredPacket<>(type, streamCodec, null, handler, target));
    }

    @Override
    public <T extends CustomPacketPayload> void play2Client(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> handler, ExecutionTarget target) {
        this.add(new RegisteredPacket<>(type, streamCodec, handler, null, target));
    }

    @Override
    public <T extends CustomPacketPayload> void playBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> clientHandler, ServerHandler<T> serverHandler, ExecutionTarget target) {
        // NeoForge only allows a payload type to be registered once, so both handlers must go through playBidirectional
        this.add(new RegisteredPacket<>(type, streamCodec, clientHandler, serverHandler, target));
    }

    private void add(RegisteredPacket<?> packet) {
        if (collected) throw new IllegalStateException("Cannot register packets after they have been collected!");
        packets.add(packet);
    }

    public void collectPackets(PayloadRegistrar registrar) {
        if (collected) throw new IllegalStateException("Packets have already been collected!");

        collected = true;
        packets.forEach(packet -> registerPacket(registrar, packet));
    }

    private <T extends CustomPacketPayload> void registerPacket(PayloadRegistrar registrar, RegisteredPacket<T> packet) {
        // TODO: Handle execution target here
        IPayloadHandler<T> clientHandler = packet.clientHandler == null ? null : (payload, context) ->
                packet.clientHandler.onHandle(payload, new ClientPacketContextNeoForge(context));
        IPayloadHandler<T> serverHandler = packet.serverHandler == null ? null : (payload, context) ->
                packet.serverHandler.onHandle(payload, new ServerPacketContextNeoForge(context));

        if (clientHandler != null && serverHandler != null) {
            registrar.playBidirectional(packet.type, packet.streamCodec, serverHandler, clientHandler);
        } else if (clientHandler != null) {
            registrar.playToClient(packet.type, packet.streamCodec, clientHandler);
        } else if (serverHandler != null) {
            registrar.playToServer(packet.type, packet.streamCodec, serverHandler);
        }
    }

    /// Represents a registered packet, containing all necessary information for encoding, decoding, and handling the packet on both the client and server
    /// for when the packets are collected by neoforges registry event.
    public record RegisteredPacket<T extends CustomPacketPayload>(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, @Nullable ClientHandler<T> clientHandler, @Nullable ServerHandler<T> serverHandler, ExecutionTarget target) {
    }
}
