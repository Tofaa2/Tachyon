package net.tachyon.potion;

import net.tachyon.entity.Entity;
import net.tachyon.entity.Player;
import net.tachyon.network.packet.server.play.EntityEffectPacket;
import net.tachyon.network.packet.server.play.RemoveEntityEffectPacket;
import org.jetbrains.annotations.NotNull;

public class Potion {

    private final PotionEffect effect;
    private final byte amplifier;
    private final int duration;
    private final byte flags;

    /**
     * Creates a new potion.
     *
     * @param effect    The type of potion.
     * @param amplifier The strength of the potion.
     * @param duration  The length of the potion in ticks.
     */
    public Potion(@NotNull PotionEffect effect, byte amplifier, int duration) {
        this(effect, amplifier, duration, true, true, false);
    }

    /**
     * Creates a new potion.
     *
     * @param effect    The type of potion.
     * @param amplifier The strength of the potion.
     * @param duration  The length of the potion in ticks.
     * @param particles If the potion has particles.
     */
    public Potion(@NotNull PotionEffect effect, byte amplifier, int duration, boolean particles) {
        this(effect, amplifier, duration, particles, true, false);
    }

    /**
     * Creates a new potion.
     *
     * @param effect    The type of potion.
     * @param amplifier The strength of the potion.
     * @param duration  The length of the potion in ticks.
     * @param particles If the potion has particles.
     * @param icon      If the potion has an icon.
     */
    public Potion(@NotNull PotionEffect effect, byte amplifier, int duration, boolean particles, boolean icon) {
        this(effect, amplifier, duration, particles, icon, false);
    }

    /**
     * Creates a new potion.
     *
     * @param effect    The type of potion.
     * @param amplifier The strength of the potion.
     * @param duration  The length of the potion in ticks.
     * @param particles If the potion has particles.
     * @param icon      If the potion has an icon.
     * @param ambient   If the potion came from a beacon.
     */
    public Potion(@NotNull PotionEffect effect, byte amplifier, int duration, boolean particles, boolean icon, boolean ambient) {
        this.effect = effect;
        this.amplifier = amplifier;
        this.duration = duration;
        byte flags = 0;
        if (ambient) {
            flags = (byte) (flags | 0x01);
        }
        if (particles) {
            flags = (byte) (flags | 0x02);
        }
        if (icon) {
            flags = (byte) (flags | 0x04);
        }
        this.flags = flags;
    }

    @NotNull
    public PotionEffect getEffect() {
        return effect;
    }

    public byte getAmplifier() {
        return amplifier;
    }

    public int getDuration() {
        return duration;
    }

    public byte getFlags() {
        return flags;
    }

    /**
     * Sends a packet that a potion effect has been applied to the entity.
     * <p>
     * Used internally by {@link Player#addEffect(Potion)}
     *
     * @param entity the entity to add the effect to
     */
    public void sendAddPacket(@NotNull Entity entity) {
        EntityEffectPacket entityEffectPacket = new EntityEffectPacket(entity.getEntityId(), this);
        entity.sendPacketToViewersAndSelf(entityEffectPacket);
    }

    /**
     * Sends a packet that a potion effect has been removed from the entity.
     * <p>
     * Used internally by {@link Player#removeEffect(PotionEffect)}
     *
     * @param entity the entity to remove the effect from
     */
    public void sendRemovePacket(@NotNull Entity entity) {
        RemoveEntityEffectPacket removeEntityEffectPacket = new RemoveEntityEffectPacket(entity.getEntityId(), effect);
        entity.sendPacketToViewersAndSelf(removeEntityEffectPacket);
    }
}
