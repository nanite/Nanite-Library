package dev.nanite.library.fabric.core.network;

import dev.nanite.library.core.network.NetworkRegistry;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/// This class is loaded on both sides, so it must never reference client-only classes
/// Client handlers are queued here and bound by the client entrypoint via [#bindClientReceivers(Consumer)].
public class NetworkRegistryFabric implements NetworkRegistry {
    private final List<ClientReceiver<?>> pendingClientReceivers = new ArrayList<>();
    @Nullable
    private Consumer<ClientReceiver<?>> clientBinder;

    /// Fabric has no network version negotiation, so this is a no-op.
    @Override
    public NetworkRegistry versioned(String version) {
        return this;
    }

    /// Fabric never refuses a connection over missing packets, so every packet is already optional.
    @Override
    public NetworkRegistry optional() {
        return this;
    }

    @Override
    public <T extends CustomPacketPayload> void play2Server(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ServerHandler<T> handler) {
        PayloadTypeRegistry.serverboundPlay().register(type, streamCodec);
        this.registerServerReceiver(type, handler);
    }

    @Override
    public <T extends CustomPacketPayload> void play2Client(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> handler) {
        PayloadTypeRegistry.clientboundPlay().register(type, streamCodec);
        this.registerClientReceiver(type, handler);
    }

    @Override
    public <T extends CustomPacketPayload> void playBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super ByteBuf, T> streamCodec, ClientHandler<T> clientHandler, ServerHandler<T> serverHandler) {
        // Fabric keeps separate payload registries per direction, so registering the type in both is valid.
        this.play2Client(type, streamCodec, clientHandler);
        this.play2Server(type, streamCodec, serverHandler);
    }

    private <T extends CustomPacketPayload> void registerServerReceiver(CustomPacketPayload.Type<T> type, ServerHandler<T> handler) {
        ServerPlayNetworking.registerGlobalReceiver(type, (payload, context) -> handler.onHandle(payload, new ServerPacketContextFabric(context)));
    }

    private synchronized <T extends CustomPacketPayload> void registerClientReceiver(CustomPacketPayload.Type<T> type, ClientHandler<T> handler) {
        var receiver = new ClientReceiver<>(type, handler);
        if (this.clientBinder != null) {
            this.clientBinder.accept(receiver);
        } else {
            this.pendingClientReceivers.add(receiver);
        }
    }

    public synchronized void bindClientReceivers(Consumer<ClientReceiver<?>> binder) {
        if (this.clientBinder != null) {
            throw new IllegalStateException("Client receivers have already been bound");
        }

        this.clientBinder = binder;
        this.pendingClientReceivers.forEach(binder);
        this.pendingClientReceivers.clear();
    }

    public record ClientReceiver<T extends CustomPacketPayload>(CustomPacketPayload.Type<T> type, ClientHandler<T> handler) {
    }
}
