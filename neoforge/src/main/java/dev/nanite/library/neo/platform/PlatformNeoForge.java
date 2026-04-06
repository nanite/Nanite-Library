package dev.nanite.library.neo.platform;

import dev.nanite.library.core.network.NetworkRegistry;
import dev.nanite.library.core.registry.NaniteRegistry;
import dev.nanite.library.neo.NaniteLibraryNeoForge;
import dev.nanite.library.neo.core.network.NetworkRegistryNeoForge;
import dev.nanite.library.neo.core.registry.NeoRegistry;
import dev.nanite.library.platform.Weirdness;
import dev.nanite.library.platform.Platform;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.network.PacketDistributor;

import java.nio.file.Path;
import java.util.Map;

public class PlatformNeoForge implements Platform {
    private final NeoWeirdness weirdness = new NeoWeirdness();
    private final NetworkRegistry networkRegistry = new NetworkRegistryNeoForge();

    @Override
    public <T> NaniteRegistry<T> createRegistry(String modId, Registry<T> backingRegistry) {
        return new NeoRegistry<>(modId, backingRegistry.key());
    }

    @Override
    public Weirdness weirdness() {
        return weirdness;
    }

    @Override
    public String getPlatformName() {
        return "Neo";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public Path gamePath() {
        return FMLPaths.GAMEDIR.get();
    }

    @Override
    public Path configPath() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public Path modsPath() {
        return FMLPaths.MODSDIR.get();
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLEnvironment.isProduction();
    }

    @Override
    public void registerDataPackReloadListener(Map<Identifier, PreparableReloadListener> listeners) {
        NaniteLibraryNeoForge.reloadListeners.putAll(listeners);
    }

    @Override
    public NetworkRegistry network() {
        return networkRegistry;
    }

    @Override
    public void sendPacketToPlayer(ServerPlayer player, CustomPacketPayload packet) {
        PacketDistributor.sendToPlayer(player, packet);
    }

    @Override
    public void sendPacketToAllPlayers(MinecraftServer server, CustomPacketPayload packet) {

    }
}
