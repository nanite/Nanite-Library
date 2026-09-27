package dev.nanite.library.neo.core.network;

import dev.nanite.library.core.network.NetworkRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jspecify.annotations.Nullable;

import java.util.*;

/// Packets are collected here and handed to NeoForge in [#collectPackets(PayloadRegistrar)]
public class NetworkRegistryNeoForge implements NetworkRegistry {
    private final State state;
    private final String version;
    private final boolean optional;

    public NetworkRegistryNeoForge() {
        this(new State(), DEFAULT_VERSION, false);
    }

    private NetworkRegistryNeoForge(State state, String version, boolean optional) {
        this.state = state;
        this.version = version;
        this.optional = optional;
    }

    @Override
    public NetworkRegistry versioned(String version) {
        if (version.isBlank()) throw new IllegalArgumentException("Network version may not be empty");
        return new NetworkRegistryNeoForge(this.state, version.strip(), this.optional);
    }

    @Override
    public NetworkRegistry optional() {
        return new NetworkRegistryNeoForge(this.state, this.version, true);
    }

    @Override
    public <T extends CustomPacketPayload> void play2Server(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ServerHandler<T> handler) {
        this.add(new RegisteredPacket<>(type, streamCodec, null, handler, this.version, this.optional));
    }

    @Override
    public <T extends CustomPacketPayload> void play2Client(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> handler) {
        this.add(new RegisteredPacket<>(type, streamCodec, handler, null, this.version, this.optional));
    }

    @Override
    public <T extends CustomPacketPayload> void playBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> clientHandler, ServerHandler<T> serverHandler) {
        // NeoForge only allows a payload type to be registered once, so both handlers must go through playBidirectional
        this.add(new RegisteredPacket<>(type, streamCodec, clientHandler, serverHandler, this.version, this.optional));
    }

    private void add(RegisteredPacket<?> packet) {
        synchronized (state) {
            if (state.collected) throw new IllegalStateException("Cannot register packets after they have been collected!");
            state.packets.add(packet);
        }
    }

    public void collectPackets(PayloadRegistrar registrar) {
        synchronized (state) {
            if (state.collected) throw new IllegalStateException("Packets have already been collected!");

            state.collected = true;
            state.packets.forEach(packet -> registerPacket(registrar, packet));
        }
    }

    private static <T extends CustomPacketPayload> void registerPacket(PayloadRegistrar baseRegistrar, RegisteredPacket<T> packet) {
        PayloadRegistrar registrar = baseRegistrar.versioned(packet.version);
        if (packet.optional) {
            registrar = registrar.optional();
        }

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

    /// Shared between the root registry and every view created from it.
    private static final class State {
        private final List<RegisteredPacket<?>> packets = new ArrayList<>();
        private boolean collected = false;
    }

    /// Represents a registered packet, containing all necessary information for encoding, decoding, and handling the packet on both the client and server
    /// for when the packets are collected by neoforges registry event.
    public record RegisteredPacket<T extends CustomPacketPayload>(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, @Nullable ClientHandler<T> clientHandler, @Nullable ServerHandler<T> serverHandler, String version, boolean optional) {
    }
}
