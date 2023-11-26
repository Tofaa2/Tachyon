package net.tachyon.command.arguments.minecraft.registry;

import net.tachyon.Tachyon;
import net.tachyon.block.Block;
import org.jetbrains.annotations.NotNull;

public class ArgumentBlockState extends ArgumentRegistry<Block> {

    public ArgumentBlockState(@NotNull String id) {
        super(id);
    }

    @Override
    public Block getRegistry(@NotNull String value) {
        return Tachyon.getUnsafe().getBlock(value);
    }

}
