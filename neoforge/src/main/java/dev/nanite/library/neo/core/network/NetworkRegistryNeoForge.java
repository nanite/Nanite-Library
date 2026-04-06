package dev.nanite.library.neo.core.network;

import dev.nanite.library.core.network.ExecutionTarget;
import dev.nanite.library.core.network.NetworkRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class NetworkRegistryNeoForge implements NetworkRegistry {
    private boolean collected = false;

    private final Set<RegisteredPacket<?>> play2ClientPackets = Collections.synchronizedSet(new HashSet<>());
    private final Set<RegisteredPacket<?>> play2ServerPackets = Collections.synchronizedSet(new HashSet<>());

    @Override
    public <T extends CustomPacketPayload> void play2Server(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ServerHandler<T> handler, ExecutionTarget target) {
        if (collected) throw new IllegalStateException("Cannot register packets after they have been collected!");
        play2ServerPackets.add(new RegisteredPacket<>(type, streamCodec, null, handler, target));
    }

    @Override
    public <T extends CustomPacketPayload> void play2Client(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> handler, ExecutionTarget target) {
        if (collected) throw new IllegalStateException("Cannot register packets after they have been collected!");
        play2ClientPackets.add(new RegisteredPacket<>(type, streamCodec, handler, null, target));
    }

    @Override
    public <T extends CustomPacketPayload> void playBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> clientHandler, ServerHandler<T> serverHandler, ExecutionTarget target) {
        // Hehe, fun times!
        this.play2Client(type, streamCodec, clientHandler, target);
        this.play2Server(type, streamCodec, serverHandler, target);
    }

    public void collectPackets(PayloadRegistrar registrar) {
        if (collected) throw new IllegalStateException("Packets have already been collected!");

        collected = true;
        play2ClientPackets.forEach(packet -> registerPacket(registrar, packet));
        play2ServerPackets.forEach(packet -> registerPacket(registrar, packet));
    }

    private <T extends CustomPacketPayload> void registerPacket(PayloadRegistrar registrar, RegisteredPacket<T> packet) {
        if (packet.clientHandler != null) {
            registrar.playToClient(packet.type, packet.streamCodec, (payload, context) -> {
                // Handle execution target here
                packet.clientHandler.onHandle(payload, new ClientPacketContextNeoForge(context));
            });
        }

        if (packet.serverHandler != null) {
            registrar.playToServer(packet.type, packet.streamCodec, (payload, context) -> {
                // Handle execution target here
                packet.serverHandler.onHandle(payload, new ServerPacketContextNeoForge(context));
            });
        }
    }

    /// Represents a registered packet, containing all necessary information for encoding, decoding, and handling the packet on both the client and server
    /// for when the packets are collected by neoforges registry event.
    public record RegisteredPacket<T extends CustomPacketPayload>(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, @Nullable ClientHandler<T> clientHandler, @Nullable ServerHandler<T> serverHandler, ExecutionTarget target) {
    }
}
