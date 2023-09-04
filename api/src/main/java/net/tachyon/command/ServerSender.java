package net.tachyon.command;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.tachyon.permission.Permission;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Sender used in {@link CommandManager#executeServerCommand(String)}.
 * <p>
 * Be aware that {@link #sendMessage(Identity, Component, MessageType)} is empty on purpose because the purpose
 * of this sender is to process the data of {@link CommandContext#getReturnData()}.
 */
public class ServerSender implements CommandSender {

    private final Set<Permission> permissions = Collections.unmodifiableSet(new HashSet<>());

    @Override
    public void sendMessage(@NonNull Identity source, @NonNull Component message, @NonNull MessageType type) {
        // Empty on purpose
    }

    @NotNull
    @Override
    public Set<Permission> getAllPermissions() {
        return permissions;
    }
}
