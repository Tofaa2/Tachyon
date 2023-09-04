package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public class ScoreboardObjectivePacket implements ServerPacket {

    /**
     * An unique name for the objective
     */
    public String objectiveName;
    /**
     * 0 = create the scoreboard <br>
     * 1 = to remove the scoreboard<br>
     * 2 = to update the display text
     */
    public byte mode;
    /**
     * The text to be displayed for the score
     */
    public String objectiveValue;
    /**
     * The type how the score is displayed
     */
    public Type type;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeSizedString(objectiveName);
        writer.writeByte(mode);

        if (mode == 0 || mode == 2) {
            writer.writeSizedString(objectiveValue);
            writer.writeSizedString(type.id);
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.SCOREBOARD_OBJECTIVE;
    }

    /**
     * This enumeration represents all available types for the scoreboard objective
     */
    public enum Type {
        INTEGER("integer"),
        HEARTS("hearts");

        private final String id;

        Type(String id) {
            this.id = id;
        }
    }
}
