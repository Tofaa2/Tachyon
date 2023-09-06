package net.tachyon.scoreboard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * An object which manages all the {@link Team}'s
 */
public interface TeamManager {


    boolean exists(@NotNull Team team);

    boolean exists(@NotNull String name);

    boolean deleteTeam(@NotNull String name);

    boolean deleteTeam(@NotNull Team team);

    @NotNull TeamBuilder createBuilder(@NotNull String name);

    @NotNull Team createTeam(@NotNull String name);

    @NotNull Team createTeam(@NotNull String name, @NotNull Component prefix, @NotNull  NamedTextColor teamColor, @NotNull Component suffix);

    @NotNull Team createTeam(String name, Component displayName, Component prefix, NamedTextColor teamColor, Component suffix);

    @Nullable Team getTeam(@NotNull String teamName);

    @NotNull List<String> getPlayers(@NotNull Team team);

    @NotNull List<String> getEntities(@NotNull Team team);

    @NotNull Set<Team> getTeams();

    void registerNewTeam(@NotNull Team team);
}
