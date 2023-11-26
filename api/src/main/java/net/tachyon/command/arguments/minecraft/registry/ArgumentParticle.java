package net.tachyon.command.arguments.minecraft.registry;

import net.tachyon.Tachyon;
import net.tachyon.particle.Particle;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an argument giving a {@link Particle}.
 */
public class ArgumentParticle extends ArgumentRegistry<Particle> {

    public ArgumentParticle(String id) {
        super(id);
    }

    @Override
    public Particle getRegistry(@NotNull String value) {
        return Tachyon.getUnsafe().getParticle(value);
    }

}
