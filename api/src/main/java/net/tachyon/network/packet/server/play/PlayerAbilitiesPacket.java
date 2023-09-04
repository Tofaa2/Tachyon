package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record PlayerAbilitiesPacket(boolean invulnerable, boolean flying, boolean allowFlying, boolean instantBreak, float flyingSpeed, float fov) implements ServerPacket {


    @Override
    public void write(@NotNull BinaryWriter writer) {
        byte flags = 0;
        if (invulnerable)
            flags += 1;
        if (flying)
            flags += 2;
        if (allowFlying)
            flags += 4;
        if (instantBreak)
            flags += 8;

        writer.writeByte(flags);
        writer.writeFloat(flyingSpeed);
        writer.writeFloat(fov);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.PLAYER_ABILITIES;
    }
}
