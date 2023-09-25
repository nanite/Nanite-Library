package dev.nanite.nanitelibrary.fabric;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.nanite.nanitelibrary.NaniteLibrary;

public class NaniteLibraryFabric implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NaniteLibraryFabric.class);

    @Override
    public void onInitialize() {
        NaniteLibrary.init();
    }
}
