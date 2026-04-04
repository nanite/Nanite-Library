package dev.nanite.standard.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * A thread-safe lazy initialization wrapper.
 * The value is computed only once when first accessed, and the result is cached for subsequent accesses.
 * The value can be invalidated to force recomputation on the next access.
 *
 * @param <T> the type of the value
 */
public class Lazy<T> implements Supplier<T> {
    private transient volatile boolean created = false;
    @Nullable
    private transient volatile T value;
    private final transient @NotNull Supplier<T> supplier;

    private Lazy(@NotNull Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public @Nullable T get() {
        if (!created) {
            synchronized (this) {
                if (!created) {
                    value = supplier.get();
                    created = true;
                }
            }
        }
        return value;
    }

    public void invalidate() {
        synchronized (this) {
            value = null;
            created = false;
        }
    }

    /**
     * Creates a new Lazy instance with the given supplier.
     *
     * @param supplier the supplier to compute the value, must not be null
     * @return a new Lazy instance
     *
     * @param <T> the type of the value
     */
    public static <T> @NotNull Lazy<T> of(@NotNull Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }

    @Override
    public String toString() {
        return "Lazy(value=" + (created ? value : "not created") + ")";
    }
}
