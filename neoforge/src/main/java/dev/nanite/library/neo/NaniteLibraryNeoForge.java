package dev.nanite.library.neo;

import dev.nanite.library.NaniteLibrary;
import dev.nanite.library.neo.core.network.NetworkRegistryNeoForge;
import dev.nanite.library.core.network.NetworkRegistry;
import dev.nanite.library.platform.Platform;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
//import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static dev.nanite.library.NaniteLibrary.MOD_ID;

@Mod(MOD_ID)
public class NaniteLibraryNeoForge {
    public static Map<Identifier, PreparableReloadListener> reloadListeners = Collections.synchronizedMap(new HashMap<>());

    private static final Logger LOGGER = LoggerFactory.getLogger(NaniteLibraryNeoForge.class);

    private final NaniteLibrary library;

    public NaniteLibraryNeoForge(IEventBus modEventBus) {
        library = new NaniteLibrary();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onNetworkRegister);

        NeoForge.EVENT_BUS.addListener(this::onAddServerReloadListener);
        NeoForge.EVENT_BUS.addListener(this::onServerStarting);
        NeoForge.EVENT_BUS.addListener(this::onPlayerJoin);

    }

    private void onNetworkRegister(RegisterPayloadHandlersEvent event) {
        // Every packet sets its own version and optionality, so the registrar's defaults here are only a base.
        PayloadRegistrar registrar = event.registrar(NetworkRegistry.DEFAULT_VERSION);
        // This is safe! This will always be the correct type on neoforge.
        ((NetworkRegistryNeoForge) Platform.INSTANCE.network()).collectPackets(registrar);
    }

    private void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        library.onPlayerJoin(event.getEntity());
    }

    // Register other mods listeners into the NeoForge event.
    private void onAddServerReloadListener(AddServerReloadListenersEvent event) {
        reloadListeners.forEach(event::addListener);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // ServerStartingEvent fires after worlds load, so use about-to-start to match Fabric's SERVER_STARTING
    private void onServerStarting(ServerAboutToStartEvent event) {
        library.onServerStarting(event.getServer());
    }
}
