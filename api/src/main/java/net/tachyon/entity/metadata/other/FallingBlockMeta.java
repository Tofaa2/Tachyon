package net.tachyon.entity.metadata.other;

import net.tachyon.block.Block;
import net.tachyon.entity.metadata.EntityMeta;
import net.tachyon.entity.metadata.ObjectDataProvider;
import org.jetbrains.annotations.NotNull;

public interface FallingBlockMeta extends ObjectDataProvider, EntityMeta {

    @NotNull Block getBlock();

    void setBlock(@NotNull Block block);

}
