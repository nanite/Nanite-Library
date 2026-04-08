package dev.nanite.library.utils;

import de.marhali.json5.Json5;
import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Object;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.io.IOException;

public class ExtraStreamCodecs {
    public static final StreamCodec<ByteBuf, Json5Element> JSON5_ELEMENT = ByteBufCodecs.STRING_UTF8.map(
            (string) -> new Json5().parse(string),
            (json5Element) -> {
                try {
                    return new Json5().serialize(json5Element);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
    );

    // TODO: check the types.
    public static final StreamCodec<ByteBuf, Json5Object> JSON5_OBJECT = ByteBufCodecs.STRING_UTF8.map(
            (string) -> new Json5().parse(string).getAsJson5Object(),
            (json5Element) -> {
                try {
                    return new Json5().serialize(json5Element);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
    );
}
