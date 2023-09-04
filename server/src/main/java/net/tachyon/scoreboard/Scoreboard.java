package net.tachyon.scoreboard;

import net.tachyon.Viewable;
import net.tachyon.entity.Player;
import net.tachyon.network.packet.server.play.DisplayScoreboardPacket;
import net.tachyon.network.packet.server.play.ScoreboardObjectivePacket;
import net.tachyon.network.packet.server.play.UpdateScorePacket;
import org.jetbrains.annotations.NotNull;

/**
 * This interface represents all scoreboard of Minecraft.
 */
public interface Scoreboard extends Viewable {

    /**
     * Creates a creation objective packet.
     *
     * @param value The value for the objective
     * @param type  The type for the objective
     * @return the creation objective packet
     */
    @NotNull
    default ScoreboardObjectivePacket getCreationObjectivePacket(String value, ScoreboardObjectivePacket.Type type) {
        final ScoreboardObjectivePacket packet = new ScoreboardObjectivePacket();
        packet.objectiveName = this.getObjectiveName();
        packet.mode = 0; // Create Scoreboard
        packet.objectiveValue = value;
        packet.type = type;

        return packet;
    }

    /**
     * Creates the destruction objective packet.
     *
     * @return the destruction objective packet
     */
    @NotNull
    default ScoreboardObjectivePacket getDestructionObjectivePacket() {
        final ScoreboardObjectivePacket packet = new ScoreboardObjectivePacket();
        packet.objectiveName = this.getObjectiveName();
        packet.mode = 1; // Destroy Scoreboard

        return packet;
    }

    /**
     * Creates the {@link DisplayScoreboardPacket}.
     *
     * @param position The position of the scoreboard
     * @return the created display scoreboard packet
     */
    @NotNull
    default DisplayScoreboardPacket getDisplayScoreboardPacket(byte position) {
        return new DisplayScoreboardPacket(position, this.getObjectiveName());
    }

    /**
     * Updates the score of a {@link Player}.
     *
     * @param player The player
     * @param score  The new score
     */
    default void updateScore(Player player, int score) {
        final UpdateScorePacket packet = new UpdateScorePacket();
        packet.scoreName = player.getUsername();
        packet.action = 0; // Create/Update score
        packet.objectiveName = this.getObjectiveName();
        packet.value = score;

        sendPacketsToViewers(packet);
    }

    /**
     * Gets the objective name of the scoreboard.
     *
     * @return the objective name
     */
    @NotNull
    String getObjectiveName();

}
