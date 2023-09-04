package net.tachyon.entity.metadata.monster;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonEndermanMeta extends MonsterMeta implements EndermanMeta {

    public TachyonEndermanMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public short getCarriedBlockID() {
        return super.metadata.getIndex((byte) 15, (short) 0);
    }

    public void setCarriedBlockID(short value) {
        super.metadata.setIndex((byte) 15, Metadata.Short(value));
    }

    public byte getCarriedBlockData() {
        return super.metadata.getIndex((byte) 16, (byte) 0);
    }

    public void setCarriedBlockData(byte value) {
        super.metadata.setIndex((byte) 16, Metadata.Byte(value));
    }

    public boolean isScreaming() {
        return super.metadata.getIndex((byte) 18, false);
    }

    public void setScreaming(boolean value) {
        super.metadata.setIndex((byte) 18, Metadata.Boolean(value));
    }

}
