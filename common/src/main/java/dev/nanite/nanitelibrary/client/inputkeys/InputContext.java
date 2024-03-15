package dev.nanite.nanitelibrary.client.inputkeys;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public record InputContext(
        Level world,
        Player player,
        ItemStack heldItem,
        ItemStack offHandItem,
        boolean sneaking,
        @Nullable BlockHitResult targetBlock,
        @Nullable Entity targetEntity
) {}
