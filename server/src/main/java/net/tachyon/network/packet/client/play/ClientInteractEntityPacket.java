package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.network.packet.client.ClientPlayPacket;
import net.tachyon.utils.binary.TachyonBinaryReader;
import org.jetbrains.annotations.NotNull;

public class ClientInteractEntityPacket extends ClientPlayPacket {

    public int targetId;
    public Type type;
    public float x;
    public float y;
    public float z;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.targetId = reader.readVarInt();
        this.type = Type.values()[reader.readVarInt()];

        switch (type) {
            case INTERACT, ATTACK -> {
            }
            case INTERACT_AT -> {
                this.x = reader.readFloat();
                this.y = reader.readFloat();
                this.z = reader.readFloat();
            }
        }
    }

    public enum Type {
        INTERACT,
        ATTACK,
        INTERACT_AT
    }
}
