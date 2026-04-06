package dev.nanite.library.core.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

/// TODO: Add more context
public interface ClientPacketContext {
    /// An easily accessible reference to the Minecraft client instance.
    Minecraft client();

    /// The player that received the packet
    LocalPlayer player();
}
