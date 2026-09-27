package dev.nanite.library.utils;

import de.marhali.json5.Json5;
import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Object;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.io.IOException;

public class ExtraStreamCodecs {
    /// Compact output without comments
    private static final Json5 NETWORK_JSON5 = Json5.builder(options -> options.allowNaN().allowInfinity().build());

    /// Only allow up to 256 KiB of JSON5 data to be sent in a single packet, to avoid abuse.
    private static final int MAX_LENGTH = 262_144;

    public static final StreamCodec<ByteBuf, Json5Element> JSON5_ELEMENT = ByteBufCodecs.stringUtf8(MAX_LENGTH).map(
            NETWORK_JSON5::parse,
            ExtraStreamCodecs::serialize
    );

    public static final StreamCodec<ByteBuf, Json5Object> JSON5_OBJECT = ByteBufCodecs.stringUtf8(MAX_LENGTH).map(
            string -> {
                if (!(NETWORK_JSON5.parse(string) instanceof Json5Object object)) {
                    throw new DecoderException("Expected a JSON5 object");
                }

                return object;
            },
            ExtraStreamCodecs::serialize
    );

    private static String serialize(Json5Element element) {
        try {
            return NETWORK_JSON5.serialize(element);
        } catch (IOException e) {
            throw new EncoderException("Failed to serialize JSON5", e);
        }
    }
}
