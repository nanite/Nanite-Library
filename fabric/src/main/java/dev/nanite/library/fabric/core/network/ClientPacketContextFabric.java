package dev.nanite.library.fabric.core.network;

import dev.nanite.library.core.network.ClientPacketContext;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class ClientPacketContextFabric implements ClientPacketContext {
    private final ClientPlayNetworking.Context context;

    public ClientPacketContextFabric(ClientPlayNetworking.Context context) {
        this.context = context;
    }

    @Override
    public Minecraft client() {
        return context.client();
    }

    @Override
    public LocalPlayer player() {
        return context.player();
    }
}
