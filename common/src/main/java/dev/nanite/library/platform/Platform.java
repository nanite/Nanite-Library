package dev.nanite.library.platform;

import dev.nanite.library.core.network.NetworkRegistry;
import dev.nanite.library.core.registry.NaniteRegistry;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.entity.player.Player;

import java.nio.file.Path;
import java.util.Map;
import java.util.ServiceLoader;

public interface Platform {
    Platform INSTANCE = ServiceLoader.load(Platform.class)
            .findFirst()
            .orElseThrow(() -> new NullPointerException("Failed to load Platform service"));

    <T> NaniteRegistry<T> createRegistry(String modId, Registry<T> backingRegistry);

    Weirdness weirdness();

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    Path gamePath();

    Path configPath();

    Path modsPath();

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }

    void registerDataPackReloadListener(Map<Identifier, PreparableReloadListener> listeners);

    /// Provides access to the network registry which can be used to register packets
    NetworkRegistry network();

    void sendPacketToPlayer(ServerPlayer player, CustomPacketPayload packet);

    void sendPacketToAllPlayers(MinecraftServer server, CustomPacketPayload packet);
}
