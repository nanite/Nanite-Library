package dev.nanite.nanitelibrary.core.config.types;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.nanite.nanitelibrary.core.config.NaniteConfigLifecycleContext;
import dev.nanite.nanitelibrary.core.config.NaniteConfigLifecycles;
import dev.nanite.nanitelibrary.core.config.NaniteConfigType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ConfigItemStack implements NaniteConfigType<ItemStack> {
    private static final ResourceLocation NAME = new ResourceLocation("nanitelibrary", "item_stack");
    @Override
    public ResourceLocation name() {
        return NAME;
    }

    @Override
    public List<NaniteConfigLifecycles> supportedLifecycles() {
        return List.of(
                NaniteConfigLifecycles.ON_GAME_AVAILABLE,
                NaniteConfigLifecycles.ON_SERVER_AVAILABLE
        );
    }

    @Override
    public ItemStack deserialize(String string, NaniteConfigLifecycleContext context) {
        // Parse the string as a compound tag
        try {
            CompoundTag compoundTag = TagParser.parseTag(string);
            return ItemStack.of(compoundTag);
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String serialize(ItemStack value, NaniteConfigLifecycleContext context) {
        return value.save(new CompoundTag()).toString();
    }
}
