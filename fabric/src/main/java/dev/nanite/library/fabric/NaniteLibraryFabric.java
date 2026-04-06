package dev.nanite.library.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.nanite.library.NaniteLibrary;

public class NaniteLibraryFabric implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NaniteLibraryFabric.class);

    @Override
    public void onInitialize() {
        var library = new NaniteLibrary();

        ServerPlayerEvents.JOIN.register(library::onPlayerJoin);
    }
}
