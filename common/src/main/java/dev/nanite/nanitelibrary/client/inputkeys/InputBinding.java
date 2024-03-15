package dev.nanite.nanitelibrary.client.inputkeys;

import java.util.function.Predicate;

public interface InputBinding {
    Predicate<InputContext> ALWAYS = (b) -> true;

    int key();

    boolean condition(InputContext context);

    void onInput(InputContext context);

    boolean requiresHeldItem();

    boolean requiresSneaking();

    boolean requiresTargetBlock();

    boolean requiresTargetEntity();
}
