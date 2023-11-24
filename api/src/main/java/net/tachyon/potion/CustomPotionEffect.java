package net.tachyon.potion;

import net.tachyon.Tachyon;
import net.tachyon.item.metadata.PotionMeta;
import net.tachyon.utils.clone.PublicCloneable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents a custom effect in {@link PotionMeta}.
 * <p>
 * This is an immutable class.
 */
public class CustomPotionEffect implements PublicCloneable<CustomPotionEffect> {

    private final byte id;
    private final byte amplifier;
    private final int duration;
    private final boolean ambient;
    private final boolean showParticles;

    public CustomPotionEffect(byte id, byte amplifier, int duration,
                              boolean ambient, boolean showParticles) {
        this.id = id;
        this.amplifier = amplifier;
        this.duration = duration;
        this.ambient = ambient;
        this.showParticles = showParticles;
    }

    public byte getId() {
        return id;
    }

    public byte getAmplifier() {
        return amplifier;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isAmbient() {
        return ambient;
    }

    public boolean showParticles() {
        return showParticles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomPotionEffect that = (CustomPotionEffect) o;
        return id == that.id && amplifier == that.amplifier && duration == that.duration && ambient == that.ambient && showParticles == that.showParticles;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amplifier, duration, ambient, showParticles);
    }

    @NotNull
    @Override
    public CustomPotionEffect clone() {
        try {
            return (CustomPotionEffect) super.clone();
        } catch (CloneNotSupportedException e) {
            Tachyon.getServer().getExceptionManager().handleException(e);
            throw new IllegalStateException("Weird thing happened");
        }
    }
}
