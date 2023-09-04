package net.tachyon.network.packet.server.play;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

/**
 * The packet creates or updates teams
 */
public class TeamsPacket implements ServerPacket {

    /**
     * The registry name of the team
     */
    public String teamName;
    /**
     * The action of the packet
     */
    public Action action;

    /**
     * The display name for the team
     */
    public Component teamDisplayName;
    /**
     * The friendly flags to
     */
    public byte friendlyFlags;
    /**
     * Visibility state for the name tag
     */
    public NameTagVisibility nameTagVisibility;
    /**
     * The color of the team
     */
    public NamedTextColor teamColor;
    /**
     * The prefix of the team
     */
    public Component teamPrefix;
    /**
     * The suffix of the team
     */
    public Component teamSuffix;
    /**
     * An array with all entities in the team
     */
    public String[] entities;

    /**
     * Writes data into the {@link BinaryWriter}
     *
     * @param writer The writer to writes
     */
    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeSizedString(this.teamName);
        writer.writeByte((byte) this.action.ordinal());

        switch (action) {
            case CREATE_TEAM, UPDATE_TEAM_INFO -> {
                writer.writeSizedString(LegacyComponentSerializer.legacySection().serialize(this.teamDisplayName));
                writer.writeSizedString(LegacyComponentSerializer.legacySection().serialize(this.teamPrefix));
                writer.writeSizedString(LegacyComponentSerializer.legacySection().serialize(this.teamSuffix));
                writer.writeByte(this.friendlyFlags);
                writer.writeSizedString(this.nameTagVisibility.getIdentifier());
                writer.writeByte((byte) this.teamColor.value());
            }
            case REMOVE_TEAM -> {
            }
        }

        if (action == Action.CREATE_TEAM || action == Action.ADD_PLAYERS_TEAM || action == Action.REMOVE_PLAYERS_TEAM) {
            if (entities == null || entities.length == 0) {
                writer.writeVarInt(0); // Empty
            } else {
                writer.writeStringArray(entities);
            }
        }

    }

    /**
     * Gets the identifier of the packet
     *
     * @return the identifier
     */
    @Override
    public int getId() {
        return ServerPacketIdentifier.TEAMS;
    }

    /**
     * An enumeration which representing all actions for the packet
     */
    public enum Action {
        /**
         * An action to create a new team
         */
        CREATE_TEAM,
        /**
         * An action to remove a team
         */
        REMOVE_TEAM,
        /**
         * An action to update the team information
         */
        UPDATE_TEAM_INFO,
        /**
         * An action to add player to the team
         */
        ADD_PLAYERS_TEAM,
        /**
         * An action to remove player from the team
         */
        REMOVE_PLAYERS_TEAM
    }

    /**
     * An enumeration which representing all visibility states for the name tags
     */
    public enum NameTagVisibility {
        /**
         * The name tag is visible
         */
        ALWAYS("always"),
        /**
         * Hides the name tag for other teams
         */
        HIDE_FOR_OTHER_TEAMS("hideForOtherTeams"),
        /**
         * Hides the name tag for the own team
         */
        HIDE_FOR_OWN_TEAM("hideForOwnTeam"),
        /**
         * The name tag is invisible
         */
        NEVER("never");

        /**
         * The identifier for the client
         */
        private final String identifier;

        /**
         * Default constructor
         *
         * @param identifier The client identifier
         */
        NameTagVisibility(String identifier) {
            this.identifier = identifier;
        }

        /**
         * Gets the client identifier
         *
         * @return the identifier
         */
        @NotNull
        public String getIdentifier() {
            return identifier;
        }
    }

}
