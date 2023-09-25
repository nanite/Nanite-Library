package dev.nanite.nanitelibrary.core.creative;

import dev.nanite.nanitelibrary.core.registry.NaniteRegistry;
import dev.nanite.nanitelibrary.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Supplier;

/**
 * Basically a copy from the CreativeTab.Builder but with a few nice to haves
 * and direct access to the cross-platform builder instead of the vanilla one that requires
 * indexing and positioning.
 */
public class ItemGroupBuilder {
    private static final CreativeModeTab.DisplayItemsGenerator EMPTY_DISPLAY_GENERATOR =
            (itemDisplayParameters, output) -> {};

    private final Component title;
    private Supplier<ItemStack> icon;
    private LinkedList<Supplier<Item>> items;
    private CreativeModeTab.DisplayItemsGenerator displayGenerator = EMPTY_DISPLAY_GENERATOR;

    private boolean canScroll = true;
    private boolean showTitle = true;
    private boolean alignedRight = false;
    private String backgroundSuffix = "items.png";

    public ItemGroupBuilder(Component title) {
        this.title = title;
    }

    public ItemGroupBuilder(String translationKey) {
        this(Component.translatable(translationKey));
    }

    public ItemGroupBuilder icon(Supplier<ItemStack> item) {
        this.icon = item;
        return this;
    }

    public ItemGroupBuilder iconFromItem(Supplier<Item> item) {
        this.icon = () -> new ItemStack(item.get());
        return this;
    }

    public ItemGroupBuilder lazyItems(Collection<Supplier<Item>> items) {
        if (this.displayGenerator != EMPTY_DISPLAY_GENERATOR)
            throw new IllegalStateException("Cannot set both lazy and display generator");

        this.items = new LinkedList<>(items);
        return this;
    }

    public ItemGroupBuilder lazyFromRegistry(NaniteRegistry<Item> registry) {
        if (this.displayGenerator != EMPTY_DISPLAY_GENERATOR)
            throw new IllegalStateException("Cannot set both lazy and display generator");

        this.items = new LinkedList<>();
        this.items.addAll(registry.entries());
        return this;
    }

    public ItemGroupBuilder itemDisplay(CreativeModeTab.DisplayItemsGenerator generator) {
        if (this.items != null)
            throw new IllegalStateException("Cannot set both lazy and display generator");

        this.displayGenerator = generator;
        return this;
    }

    public ItemGroupBuilder noScroll() {
        this.canScroll = false;
        return this;
    }

    public ItemGroupBuilder noTitle() {
        this.showTitle = false;
        return this;
    }

    public ItemGroupBuilder rightAligned() {
        this.alignedRight = true;
        return this;
    }

    public ItemGroupBuilder backgroundSuffix(String suffix) {
        this.backgroundSuffix = suffix;
        return this;
    }

    public CreativeModeTab build() {
        CreativeModeTab.Builder tab = Services.PLATFORM.weirdness()
                .createItemGroupBuilder()
                .title(this.title)
                .icon(this.icon)
                .backgroundSuffix(this.backgroundSuffix);

        if (!this.canScroll) tab.noScrollBar();
        if (!this.showTitle) tab.hideTitle();
        if (this.alignedRight) tab.alignedRight();

        if (this.items != null) {
            tab.displayItems(((itemDisplayParameters, output) -> {
                for (Supplier<Item> item : this.items) {
                    output.accept(item.get());
                }
            }));
        } else {
            tab.displayItems(this.displayGenerator);
        }

        return tab.build();
    }
}
