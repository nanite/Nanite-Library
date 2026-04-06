package dev.nanite.library.neo.core.network;

import dev.nanite.library.core.network.ClientPacketContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.client.network.handling.ClientPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPacketContextNeoForge implements ClientPacketContext {
    private final IPayloadContext payloadContext;

    public ClientPacketContextNeoForge(IPayloadContext payloadContext) {
        //noinspection UnstableApiUsage
        if (!(payloadContext instanceof ClientPayloadContext)) {
            throw new IllegalArgumentException("Expected a ClientPayloadContext, but got: " + payloadContext.getClass().getName());
        }

        this.payloadContext = payloadContext;
    }

    @Override
    public Minecraft client() {
        return Minecraft.getInstance();
    }

    @Override
    public LocalPlayer player() {
        return (LocalPlayer) payloadContext.player();
    }
}
