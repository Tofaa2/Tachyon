package net.tachyon.command.arguments.minecraft.registry;

import net.tachyon.Tachyon;
import net.tachyon.entity.EntityType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an argument giving an {@link EntityType}.
 */
public class ArgumentEntityType extends ArgumentRegistry<EntityType> {

    public ArgumentEntityType(String id) {
        super(id);
    }

    @Override
    public EntityType getRegistry(@NotNull String value) {
        return Tachyon.getUnsafe().getEntityType(value);
    }

}
