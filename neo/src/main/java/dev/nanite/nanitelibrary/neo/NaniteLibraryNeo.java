package dev.nanite.nanitelibrary.neo;

import dev.nanite.nanitelibrary.NaniteLibrary;
import dev.nanite.nanitelibrary.core.registry.reload.NaniteReloadListenerManager;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static dev.nanite.nanitelibrary.Constants.MOD_ID;

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

    @SubscribeEvent
    private void onAddReloadListener(AddReloadListenerEvent event){
        LOGGER.debug("Registering reload listeners");
        for (PreparableReloadListener listener : NaniteReloadListenerManager.INSTANCE.stream().toList()) {
            event.addListener(listener);
        }
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // @SubscribeEvent
    // public void onRegisterCommands(RegisterCommandsEvent event) {
    // }
}
