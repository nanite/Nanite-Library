package dev.nanite.nanitelibrary.neo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import dev.nanite.nanitelibrary.NaniteLibrary;
import static dev.nanite.nanitelibrary.Constants.MOD_ID;

@Mod(MOD_ID)
public class NaniteLibraryNeo {
    private static final Logger LOGGER = LoggerFactory.getLogger(NaniteLibraryNeo.class);
    
    public NaniteLibraryNeo() {
        NaniteLibrary.init();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // @SubscribeEvent
    // public void onRegisterCommands(RegisterCommandsEvent event) {
    // }
}
