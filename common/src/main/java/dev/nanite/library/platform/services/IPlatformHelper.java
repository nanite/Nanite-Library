package dev.nanite.library.platform.services;

import dev.nanite.library.core.registry.NaniteRegistry;
import dev.nanite.library.platform.Weirdness;
import net.minecraft.core.Registry;

import java.nio.file.Path;

public interface IPlatformHelper {
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
}
