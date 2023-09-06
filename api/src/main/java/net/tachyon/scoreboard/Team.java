package net.tachyon.scoreboard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.tachyon.MinecraftServer;
import net.tachyon.Tachyon;
import net.tachyon.entity.Player;
import net.tachyon.network.ConnectionManager;
import net.tachyon.network.packet.server.play.TeamsPacket;
import net.tachyon.network.packet.server.play.TeamsPacket.NameTagVisibility;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * This object represents a team on a scoreboard that has a common display theme and other properties.
 */
public class Team {

    private static final ConnectionManager CONNECTION_MANAGER = MinecraftServer.getConnectionManager();

    /**
     * A collection of all registered entities who are on the team.
     */
    private final Set<String> members;

    /**
     * The registry name of the team.
     */
    private final String teamName;
    /**
     * The display name of the team.
     */
    private Component teamDisplayName;
    /**
     * A BitMask.
     */
    private byte friendlyFlags;
    /**
     * The visibility of the team.
     */
    private NameTagVisibility nameTagVisibility;

    /**
     * Used to color the name of players on the team <br>
     * The color of a team defines how the names of the team members are visualized.
     */
    private NamedTextColor teamColor;

    /**
     * Shown before the names of the players who belong to this team.
     */
    private Component prefix;
    /**
     * Shown after the names of the player who belong to this team.
     */
    private Component suffix;

    /**
     * Default constructor to creates a team.
     *
     * @param teamName The registry name for the team
     */
    protected Team(@NotNull String teamName) {
        this.teamName = teamName;

        this.teamDisplayName = Component.text("");
        this.friendlyFlags = 0x00;
        this.nameTagVisibility = NameTagVisibility.ALWAYS;

        this.teamColor = NamedTextColor.WHITE;
        this.prefix = Component.text("");
        this.suffix = Component.text("");

        this.members = new CopyOnWriteArraySet<>();
    }

    /**
     * Adds a member to the {@link Team}.
     * <br>
     * This member can be a {@link Player} or an {@link net.tachyon.entity.LivingEntity}.
     *
     * @param member The member to be added
     */
    public void addMember(@NotNull String member) {
        // Adds a new member to the team
        this.members.add(member);

        // Initializes add player packet
        final TeamsPacket addPlayerPacket = new TeamsPacket();
        addPlayerPacket.teamName = this.teamName;
        addPlayerPacket.action = TeamsPacket.Action.ADD_PLAYERS_TEAM;
        addPlayerPacket.entities = members.toArray(new String[0]);

        // Sends to all online players the add player packet
        Tachyon.getServer().sendGroupedPacket(CONNECTION_MANAGER.getOnlinePlayers(), addPlayerPacket);
    }

    /**
     * Removes a member from the {@link Team}.
     *
     * @param member The member to be removed
     */
    public void removeMember(@NotNull String member) {
        // Initializes remove player packet
        final TeamsPacket removePlayerPacket = new TeamsPacket();
        removePlayerPacket.teamName = this.teamName;
        removePlayerPacket.action = TeamsPacket.Action.REMOVE_PLAYERS_TEAM;
        removePlayerPacket.entities = new String[]{member};

        // Sends to all online player teh remove player packet
        Tachyon.getServer().sendGroupedPacket(CONNECTION_MANAGER.getOnlinePlayers(), removePlayerPacket);

        // Removes the member from the team
        this.members.remove(member);
    }

    /**
     * Changes the display name of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed <b>server side</b>.
     *
     * @param teamDisplayName The new display name
     */
    public void setTeamDisplayName(Component teamDisplayName) {
        this.teamDisplayName = teamDisplayName;
    }

    /**
     * Changes the display name of the team and sends an update packet.
     *
     * @param teamDisplayName The new display name
     */
    public void updateTeamDisplayName(Component teamDisplayName) {
        this.setTeamDisplayName(teamDisplayName);
        sendUpdatePacket();
    }

    /**
     * Changes the {@link NameTagVisibility} of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param visibility The new tag visibility
     * @see #updateNameTagVisibility(NameTagVisibility)
     */
    public void setNameTagVisibility(@NotNull NameTagVisibility visibility) {
        this.nameTagVisibility = visibility;
    }

    /**
     * Changes the {@link NameTagVisibility} of the team and sends an update packet.
     *
     * @param nameTagVisibility The new tag visibility
     */
    public void updateNameTagVisibility(@NotNull NameTagVisibility nameTagVisibility) {
        this.setNameTagVisibility(nameTagVisibility);
        sendUpdatePacket();
    }

    /**
     * Changes the color of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param color The new team color
     * @see #updateTeamColor(NamedTextColor)
     */
    public void setTeamColor(@NotNull NamedTextColor color) {
        this.teamColor = color;
    }

    /**
     * Changes the color of the team and sends an update packet.
     *
     * @param teamColor The new team color
     */
    public void updateTeamColor(@NotNull NamedTextColor teamColor) {
        this.setTeamColor(teamColor);
        sendUpdatePacket();
    }

    /**
     * Changes the prefix of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param prefix The new prefix
     */
    public void setPrefix(Component prefix) {
        this.prefix = prefix;
    }

    /**
     * Changes the prefix of the team and sends an update packet.
     *
     * @param prefix The new prefix
     */
    public void updatePrefix(Component prefix) {
        this.setPrefix(prefix);
        sendUpdatePacket();
    }

    /**
     * Changes the suffix of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param suffix The new suffix
     */
    public void setSuffix(Component suffix) {
        this.suffix = suffix;
    }

    /**
     * Changes the suffix of the team and sends an update packet.
     *
     * @param suffix The new suffix
     */
    public void updateSuffix(Component suffix) {
        this.setSuffix(suffix);
        sendUpdatePacket();
    }

    /**
     * Changes the friendly flags of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param flag The new friendly flag
     */
    public void setFriendlyFlags(byte flag) {
        this.friendlyFlags = flag;
    }

    /**
     * Changes the friendly flags of the team and sends an update packet.
     *
     * @param flag The new friendly flag
     */
    public void updateFriendlyFlags(byte flag) {
        this.setFriendlyFlags(flag);
        this.sendUpdatePacket();
    }

    /**
     * Gets the registry name of the team.
     *
     * @return the registry name
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Creates the creation packet to add a team.
     *
     * @return the packet to add the team
     */
    @NotNull
    public TeamsPacket createTeamsCreationPacket() {
        TeamsPacket teamsCreationPacket = new TeamsPacket();
        teamsCreationPacket.teamName = teamName;
        teamsCreationPacket.action = TeamsPacket.Action.CREATE_TEAM;
        teamsCreationPacket.teamDisplayName = this.teamDisplayName;
        teamsCreationPacket.friendlyFlags = this.friendlyFlags;
        teamsCreationPacket.nameTagVisibility = this.nameTagVisibility;
        teamsCreationPacket.teamColor = this.teamColor;
        teamsCreationPacket.teamPrefix = this.prefix;
        teamsCreationPacket.teamSuffix = this.suffix;
        teamsCreationPacket.entities = this.members.toArray(new String[0]);

        return teamsCreationPacket;
    }

    /**
     * Creates an destruction packet to remove the team.
     *
     * @return the packet to remove the team
     */
    @NotNull
    public TeamsPacket createTeamDestructionPacket() {
        TeamsPacket teamsPacket = new TeamsPacket();
        teamsPacket.teamName = teamName;
        teamsPacket.action = TeamsPacket.Action.REMOVE_TEAM;
        return teamsPacket;
    }

    /**
     * Obtains an unmodifiable {@link Set} of registered players who are on the team.
     *
     * @return an unmodifiable {@link Set} of registered players
     */
    @NotNull
    public Set<String> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    /**
     * Gets the display name of the team.
     *
     * @return the display name
     */
    public Component getTeamDisplayName() {
        return teamDisplayName;
    }

    /**
     * Gets the friendly flags of the team.
     *
     * @return the friendly flags
     */
    public byte getFriendlyFlags() {
        return friendlyFlags;
    }

    /**
     * Gets the tag visibility of the team.
     *
     * @return the tag visibility
     */
    @NotNull
    public NameTagVisibility getNameTagVisibility() {
        return nameTagVisibility;
    }

    /**
     * Gets the color of the team.
     *
     * @return the team color
     */
    @NotNull
    public NamedTextColor getTeamColor() {
        return teamColor;
    }

    /**
     * Gets the prefix of the team.
     *
     * @return the team prefix
     */
    public Component getPrefix() {
        return prefix;
    }

    /**
     * Gets the suffix of the team.
     *
     * @return the suffix team
     */
    public Component getSuffix() {
        return suffix;
    }

    /**
     * Sends an {@link TeamsPacket.Action#UPDATE_TEAM_INFO} packet.
     */
    public void sendUpdatePacket() {
        final TeamsPacket updatePacket = new TeamsPacket();
        updatePacket.teamName = this.teamName;
        updatePacket.action = TeamsPacket.Action.UPDATE_TEAM_INFO;
        updatePacket.teamDisplayName = this.teamDisplayName;
        updatePacket.friendlyFlags = this.friendlyFlags;
        updatePacket.nameTagVisibility = this.nameTagVisibility;
        updatePacket.teamColor = this.teamColor;
        updatePacket.teamPrefix = this.prefix;
        updatePacket.teamSuffix = this.suffix;

        Tachyon.getServer().sendGroupedPacket(MinecraftServer.getConnectionManager().getOnlinePlayers(), updatePacket);
    }
}
