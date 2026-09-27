package dev.nanite.standard.utils;

import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

/**
 * A thread-safe lazy initialization wrapper.
 * The value is computed only once when first accessed, and the result is cached for subsequent accesses.
 * The value can be invalidated to force recomputation on the next access.
 *
 * @param <T> the type of the value
 */
public class Lazy<T> implements Supplier<T> {
    private transient volatile @Nullable Holder<T> holder;
    private final transient Supplier<T> supplier;

    private Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T get() {
        Holder<T> current = holder;
        if (current == null) {
            synchronized (this) {
                current = holder;
                if (current == null) {
                    current = new Holder<>(supplier.get());
                    holder = current;
                }
            }
        }
        return current.value;
    }

    public void invalidate() {
        holder = null;
    }

    /**
     * Creates a new Lazy instance with the given supplier.
     *
     * @param supplier the supplier to compute the value, must not be null
     * @return a new Lazy instance
     *
     * @param <T> the type of the value
     */
    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }

    @Override
    public String toString() {
        Holder<T> current = holder;
        return "Lazy(value=" + (current != null ? current.value : "not created") + ")";
    }

    private record Holder<T>(T value) {
    }
}
