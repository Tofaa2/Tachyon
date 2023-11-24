package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.network.packet.client.ClientPlayPacket;
import org.jetbrains.annotations.NotNull;

public class ClientStatusPacket extends ClientPlayPacket {

    public Action action;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.action = Action.values()[reader.readVarInt()];
    }

    public enum Action {
        PERFORM_RESPAWN,
        REQUEST_STATS,
        TAKING_INVENTORY_ACHIEVEMENT
    }

}
