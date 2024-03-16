package dev.nanite.nanitelibrary.core.configshit;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record NaniteConfigLifecycleContext(
        @Nullable Level level,
        @Nullable MinecraftServer server
) {}
