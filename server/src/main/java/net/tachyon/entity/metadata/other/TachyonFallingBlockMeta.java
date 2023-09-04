package net.tachyon.entity.metadata.other;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import net.tachyon.entity.metadata.ObjectDataProvider;
import net.tachyon.block.Block;
import org.jetbrains.annotations.NotNull;

public class TachyonFallingBlockMeta extends TachyonEntityMeta implements FallingBlockMeta {

    private Block block = Block.STONE;

    public TachyonFallingBlockMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @NotNull
    public Block getBlock() {
        return block;
    }

    /**
     * Sets which block to display.
     * This is possible only before spawn packet is sent.
     *
     * @param block which block to display.
     */
    public void setBlock(@NotNull Block block) {
        this.block = block;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public int getObjectData() {
        int id = this.block.getBlockId();
        int metadata = 0; // TODO ?
        return id | (metadata << 0x10);
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return false;
    }

}
