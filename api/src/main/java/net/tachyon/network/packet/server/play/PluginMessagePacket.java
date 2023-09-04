package net.tachyon.network.packet.server.play;

import net.tachyon.Tachyon;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record PluginMessagePacket(String channel, byte[] data) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeSizedString(channel);
        writer.writeBytes(data);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.PLUGIN_MESSAGE;
    }

    /**
     * Gets the current server brand name packet.
     * <p>
     * Sent to all players when the name changes.
     *
     * @return the current brand name packet
     */
    @NotNull
    public static PluginMessagePacket getBrandPacket(BinaryWriter writer) {
        final String brandName = Tachyon.getServer().getBrandName();
        writer.writeSizedString(brandName);
        return new PluginMessagePacket("MC|Brand", writer.toByteArray());
    }
}
