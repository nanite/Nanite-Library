package dev.nanite.library.core.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

/// TODO: Add more context
public interface ServerPacketContext {
    /// An easily accessible reference to the Minecraft client instance.
    MinecraftServer server();

    /// The player that received the packet
    ServerPlayer player();
}
