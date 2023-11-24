package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.network.packet.client.ClientPlayPacket;
import org.jetbrains.annotations.NotNull;

public class ClientEntityActionPacket extends ClientPlayPacket {

    public int playerId;
    public Action action;
    public int horseJumpBoost;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.playerId = reader.readVarInt();
        this.action = Action.values()[reader.readVarInt()];
        this.horseJumpBoost = reader.readVarInt();
    }

    public enum Action {
        START_SNEAKING,
        STOP_SNEAKING,
        LEAVE_BED,
        START_SPRINTING,
        STOP_SPRINTING,
        JUMP_WITH_HORSE,
        OPEN_HORSE_INVENTORY
    }

}
