package dev.nanite.library.core.weirdness;

import dev.nanite.library.core.registry.NaniteRegistry;
import dev.nanite.library.core.registry.RegistryHolder;
import dev.nanite.library.platform.Platform;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Supplier;

/// Basically a copy from the CreativeTab.Builder but with a few nice to haves
/// and direct access to the cross-platform builder instead of the vanilla one that requires
/// indexing and positioning.
///
/// This is primarily needed for bypassing a bunch of the private / protected methods in vanilla
public class CreativeModeTabBuilder {
    private static final DisplayItemsGenerator EMPTY_DISPLAY_GENERATOR =
            (itemDisplayParameters, output) -> {};

    private final Component title;
    private Supplier<ItemStack> icon;
    private LinkedList<Supplier<Item>> items;
    private DisplayItemsGenerator displayGenerator = EMPTY_DISPLAY_GENERATOR;

    private boolean canScroll = true;
    private boolean showTitle = true;
    private boolean alignedRight = false;
    private Identifier backgroundTexture = CreativeModeTab.createTextureLocation("items");

    public CreativeModeTabBuilder(Component title) {
        this.title = title;
    }

    public CreativeModeTabBuilder(String translationKey) {
        this(Component.translatable(translationKey));
    }

    public CreativeModeTabBuilder icon(Supplier<ItemStack> item) {
        this.icon = item;
        return this;
    }

    public CreativeModeTabBuilder iconFromItem(Supplier<Item> item) {
        this.icon = () -> new ItemStack(item.get());
        return this;
    }

    public CreativeModeTabBuilder populateFromItems(Collection<Supplier<Item>> items) {
        if (this.displayGenerator != EMPTY_DISPLAY_GENERATOR)
            throw new IllegalStateException("Cannot set both lazy and display generator");

        this.items = new LinkedList<>(items);
        return this;
    }

    public CreativeModeTabBuilder populateFromRegistry(NaniteRegistry<Item> registry) {
        if (this.displayGenerator != EMPTY_DISPLAY_GENERATOR)
            throw new IllegalStateException("Cannot set both lazy and display generator");

        this.items = new LinkedList<>();
        for (RegistryHolder<Item, ? extends Item> entry : registry.entries()) {
            this.items.add(entry::get);
        }
        return this;
    }

    public CreativeModeTabBuilder itemDisplay(DisplayItemsGenerator generator) {
        if (this.items != null)
            throw new IllegalStateException("Cannot set both lazy and display generator");

        this.displayGenerator = generator;
        return this;
    }

    public CreativeModeTabBuilder noScroll() {
        this.canScroll = false;
        return this;
    }

    public CreativeModeTabBuilder noTitle() {
        this.showTitle = false;
        return this;
    }

    public CreativeModeTabBuilder rightAligned() {
        this.alignedRight = true;
        return this;
    }

    public CreativeModeTabBuilder backgroundTexture(Identifier identifier) {
        this.backgroundTexture = identifier;
        return this;
    }

    public CreativeModeTab build() {
        CreativeModeTab.Builder tab = Platform.INSTANCE.weirdness()
                .createVanillaCreativeModeTabBuilder()
                .title(this.title)
                .icon(this.icon)
                .backgroundTexture(this.backgroundTexture);

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
            // We should be fine to dirty cast this back as they have the same signature
            tab.displayItems((CreativeModeTab.DisplayItemsGenerator) this.displayGenerator);
        }

        return tab.build();
    }

    // Taken directly from vanilla
    public interface Output {
        void accept(final ItemStack stack, final CreativeModeTab.TabVisibility tabVisibility);

        default void accept(final ItemStack stack) {
            this.accept(stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        default void accept(final ItemLike item, final CreativeModeTab.TabVisibility tabVisibility) {
            this.accept(new ItemStack(item), tabVisibility);
        }

        default void accept(final ItemLike item) {
            this.accept(new ItemStack(item), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        default void acceptAll(final Collection<ItemStack> stacks, final CreativeModeTab.TabVisibility tabVisibility) {
            stacks.forEach((stack) -> this.accept(stack, tabVisibility));
        }

        default void acceptAll(final Collection<ItemStack> stacks) {
            this.acceptAll(stacks, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    @FunctionalInterface
    public interface DisplayItemsGenerator {
        void accept(CreativeModeTab.ItemDisplayParameters parameters, Output output);
    }
}
