package dev.nanite.nanitelibrary.client.inputkeys;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class InputBindings {
    Set<Integer> registeredInputs = new HashSet<>();
    HashMap<Integer, InputBinding> bindings = new HashMap<>();

    public void setup() {
//        InputConstants.setupKeyboardCallbacks();
//        InputConstants.setupMouseCallbacks();
    }

    /**
     * TODO: Remove the need for a player as it won't be in scope
     */
    public void tick(Player player, int key) {
        if (registeredInputs.isEmpty()) {
            return;
        }

        // Look something up

    }
}
