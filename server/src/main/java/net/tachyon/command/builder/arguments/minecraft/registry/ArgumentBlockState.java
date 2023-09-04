package net.tachyon.command.builder.arguments.minecraft.registry;

import net.tachyon.block.Block;
import net.tachyon.registry.Registries;
import org.jetbrains.annotations.NotNull;

public class ArgumentBlockState extends ArgumentRegistry<Block> {

    public ArgumentBlockState(@NotNull String id) {
        super(id);
    }

    @Override
    public Block getRegistry(@NotNull String value) {
        return Registries.getBlock(value);
    }

}
