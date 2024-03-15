package dev.nanite.testneo;

import dev.nanite.nanitelibrary.core.NaniteCore;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.fml.common.Mod;

@Mod("testneo")
public class TestNeoMod {
    public TestNeoMod() {
        System.out.println("TestForgeMod");

        NaniteCore.createRegistry(BuiltInRegistries.BLOCK, "testneo");
    }
}
