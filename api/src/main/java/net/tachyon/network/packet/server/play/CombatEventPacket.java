package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.entity.Entity;
import net.tachyon.entity.Player;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

/**
 * Packet sent during combat to a {@link Player}.
 * Only death is supported for the moment (other events are ignored anyway as of 1.15.2)
 */
public record CombatEventPacket(@NotNull EventType type, int duration, int opponent, int playerId, @NotNull String deathMessage) implements ServerPacket {

    public static CombatEventPacket enter() {
        return new CombatEventPacket(EventType.ENTER_COMBAT, 0, -1, -1, "");
    }

    public static CombatEventPacket end(int durationInTicks, Entity opponent) {
        return new CombatEventPacket(EventType.END_COMBAT, durationInTicks, opponent != null ? opponent.getEntityId() : -1, -1, "");
    }

    public static CombatEventPacket death(Player player, Entity killer, String message) {
        return new CombatEventPacket(EventType.DEATH, 0, player.getEntityId(), killer != null ? killer.getEntityId() : -1, message);
    }

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(type.ordinal());
        switch (type) {
            case ENTER_COMBAT -> {
            }
            // nothing to add
            case END_COMBAT -> {
                writer.writeVarInt(duration);
                writer.writeInt(opponent);
            }
            case DEATH -> {
                writer.writeVarInt(playerId);
                writer.writeInt(opponent);
                writer.writeSizedString(deathMessage);
            }
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.COMBAT_EVENT;
    }

    public enum EventType {
        ENTER_COMBAT, END_COMBAT, // both ignored by Notchian client
        DEATH,
    }
}
