package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.coordinate.Position;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public class SoundEffectPacket implements ServerPacket {

    public String soundName;
    public Position position;
    public float volume;
    public float pitch;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeSizedString(soundName);
        writer.writeInt((int) (position.getX() * 8));
        writer.writeInt((int) (position.getY() * 8));
        writer.writeInt((int) (position.getZ() * 8));
        writer.writeFloat(volume);
        // 63 is 100%
        writer.writeByte((byte) (pitch * 63));
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.SOUND_EFFECT;
    }
}
