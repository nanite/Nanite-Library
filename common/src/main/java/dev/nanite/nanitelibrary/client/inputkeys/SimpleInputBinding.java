package dev.nanite.nanitelibrary.client.inputkeys;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class SimpleInputBinding implements InputBinding {
    private final int key;
    private final Consumer<InputContext> onInput;
    private final Predicate<InputContext> condition;

    public SimpleInputBinding(int key, Consumer<InputContext> onInput) {
        this.key = key;
        this.onInput = onInput;
        this.condition = ALWAYS;
    }

    public SimpleInputBinding(int key, Consumer<InputContext> onInput, Predicate<InputContext> condition) {
        this.key = key;
        this.onInput = onInput;
        this.condition = condition;
    }

    @Override
    public int key() {
        return this.key;
    }

    @Override
    public boolean condition(InputContext context) {
        return condition.test(context);
    }

    @Override
    public void onInput(InputContext context) {
        onInput.accept(context);
    }

    @Override
    public boolean requiresHeldItem() {
        return false;
    }

    @Override
    public boolean requiresSneaking() {
        return false;
    }

    @Override
    public boolean requiresTargetBlock() {
        return false;
    }

    @Override
    public boolean requiresTargetEntity() {
        return false;
    }
}
