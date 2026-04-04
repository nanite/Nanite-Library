package dev.nanite.library.utils;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/// A reference to either a direct registry entry or a tag.
///
/// This class provides a type-safe way to represent registry references in strings,
/// supporting both direct entries ("minecraft:stone") and tags ("#minecraft:logs").
///
/// Examples:
/// - "minecraft:stone" - Direct registry entry (stored as Identifier)
/// - "#minecraft:logs" - Tag reference (stored as TagKey)
/// - "mymod:custom_item" - Direct registry entry from a mod
/// - "#mymod:special_items" - Tag reference from a mod
///
/// @param <T> The registry type (e.g., Item, Block, Fluid)
public class RegistryReference<T> {
    private final Either<Identifier, TagKey<T>> value;

    private RegistryReference(Either<Identifier, TagKey<T>> value) {
        this.value = value;
    }

    /// Creates a direct registry entry reference.
    public static <T> RegistryReference<T> of(Identifier identifier) {
        return new RegistryReference<>(Either.left(identifier));
    }

    /// Creates a tag reference.
    public static <T> RegistryReference<T> tag(TagKey<T> tagKey) {
        return new RegistryReference<>(Either.right(tagKey));
    }

    /// Parses a registry reference from a string.
    /// Strings starting with '#' are treated as tags, otherwise as direct registry entries.
    ///
    /// @param str The string to parse (e.g., "minecraft:stone" or "#minecraft:logs")
    /// @param registryKey The registry key needed to create TagKey instances
    public static <T> RegistryReference<T> parse(String str, ResourceKey<? extends Registry<T>> registryKey) {
        if (str.startsWith("#")) {
            Identifier tagId = Identifier.parse(str.substring(1));
            return tag(TagKey.create(registryKey, tagId));
        }
        return of(Identifier.parse(str));
    }

    public @Nullable Identifier entry() {
        return value.left().orElse(null);
    }

    public @Nullable TagKey<T> tag() {
        return value.right().orElse(null);
    }

    public boolean isTag() {
        return value.right().isPresent();
    }

    public boolean isEntry() {
        return value.left().isPresent();
    }

    public void ifEntry(Consumer<Identifier> entryConsumer) {
        value.ifLeft(entryConsumer);
    }

    public void ifTag(Consumer<TagKey<T>> tagConsumer) {
        value.ifRight(tagConsumer);
    }

    /// Maps this reference using the provided functions.
    public <R> R map(Function<Identifier, R> entryMapper, Function<TagKey<T>, R> tagMapper) {
        return value.map(entryMapper, tagMapper);
    }

    /// Returns the identifier for this reference, whether it's a direct entry or tag location.
    public Identifier identifier() {
        return value.map(id -> id, TagKey::location);
    }

    @Override
    public String toString() {
        return value.map(
                Identifier::toString,
            tag -> "#" + tag.location()
        );
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistryReference<?> that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
