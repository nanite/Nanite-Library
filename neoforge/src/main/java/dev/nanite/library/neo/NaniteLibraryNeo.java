package dev.nanite.library.neo;

import dev.nanite.library.NaniteLibrary;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
//import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dev.nanite.library.NaniteLibrary.MOD_ID;

@Mod(MOD_ID)
public class NaniteLibraryNeo {
    private static final Logger LOGGER = LoggerFactory.getLogger(NaniteLibraryNeo.class);

    public NaniteLibraryNeo(IEventBus eventBus) {
        NaniteLibrary.init();

        eventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

//    @SubscribeEvent
//    private void onAddReloadListener(AddReloadListenerEvent event){
//        LOGGER.debug("Registering reload listeners");
//        for (PreparableReloadListener listener : NaniteReloadListenerManager.INSTANCE.stream(NaniteReloadListenerManager.ReloadType.SERVER).toList()) {
//            event.addListener(listener);
//        }
//    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // @SubscribeEvent
    // public void onRegisterCommands(RegisterCommandsEvent event) {
    // }
}
