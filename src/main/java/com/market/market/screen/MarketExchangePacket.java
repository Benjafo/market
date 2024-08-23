package com.market.market.screen;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;

public class MarketExchangePacket implements Packet {
    private final int[] slotIndices;

    public MarketExchangePacket(int[] slotIndices) {
        this.slotIndices = slotIndices;
    }

    public static void encode(MarketExchangePacket packet, PacketByteBuf buf) {
        buf.writeIntArray(packet.slotIndices);
    }

    public static MarketExchangePacket decode(PacketByteBuf buf) {
        return new MarketExchangePacket(buf.readIntArray());
    }

    public int[] getSlotIndices() {
        return slotIndices;
    }

    @Override
    public PacketType<? extends Packet> getPacketId() {
        return null;
    }

    @Override
    public void apply(PacketListener listener) {
        return;
    }
}