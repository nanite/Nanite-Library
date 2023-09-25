package dev.nanite.nanitelibrary.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.nanite.nanitelibrary.Constants;
import dev.nanite.nanitelibrary.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public class Services {
    private static final Logger LOGGER = LoggerFactory.getLogger(Services.class);

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));

        LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
