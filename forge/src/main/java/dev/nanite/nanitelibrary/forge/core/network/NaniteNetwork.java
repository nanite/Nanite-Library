package dev.nanite.nanitelibrary.forge.core.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;

public class NaniteNetwork {
    public static final SimpleChannel INSTANCE = ChannelBuilder.named(new ResourceLocation("nanitelibrary", "main"))
            .clientAcceptedVersions((a, b) -> true)
            .serverAcceptedVersions((a, b) -> true)
            .networkProtocolVersion(1)
            .simpleChannel();

    public static void initialize() {
        var fakePacket = new FakePacket(1, "test");

        // Convert the custom packet payload class to a forgelike packet
        INSTANCE.messageBuilder(FakePacket.class, 0, NetworkDirection.LOGIN_TO_SERVER)
                .decoder(FakePacket::new)
                .encoder((packet, friendlyByteBuf) -> {
                    fakePacket.write(friendlyByteBuf);
                })
                .consumerNetworkThread((packet, contextSupplier) -> {
                    // Do something with the packet
                    // Do something?
//                    fakePacket.a();
                });
    }

    private record FakePacket(
            int a,
            String b
    ) implements CustomPacketPayload {
        private FakePacket(FriendlyByteBuf buf) {
            this(buf.readInt(), buf.readUtf());
        }

        @Override
        public void write(FriendlyByteBuf friendlyByteBuf) {

        }

        @Override
        public ResourceLocation id() {
            return null;
        }
    }
}
