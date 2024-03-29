package net.tachyon.entity.damage;

import net.kyori.adventure.text.Component;
import net.tachyon.data.Data;
import net.tachyon.data.DataContainer;
import net.tachyon.entity.*;
import net.tachyon.sound.SoundEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a type of damage, required when calling {@link LivingEntity#damage(DamageType, float)}
 * and retrieved in {@link EntityDamageEvent}.
 * <p>
 * This class can be extended if you need to include custom fields and/or methods.
 * Be aware that this class implements {@link DataContainer}
 * so you can add your own data to an already existing damage type without any wrapper.
 */
public class DamageType implements DataContainer {

    public static final DamageType VOID = new DamageType("attack.outOfWorld");
    public static final DamageType GRAVITY = new DamageType("attack.fall");
    public static final DamageType ON_FIRE = new DamageType("attack.onFire");
    private final String identifier;
    private Data data;

    /**
     * Creates a new damage type.
     *
     * @param identifier the identifier of this damage type,
     *                   does not need to be unique
     */
    public DamageType(@NotNull String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets the identifier of this damage type.
     * <p>
     * It does not have to be unique to this object.o
     *
     * @return the damage type identifier
     */
    @NotNull
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Builds the death message linked to this damage type.
     * <p>
     * Used in {@link Player#kill()} to broadcast the proper message.
     *
     * @param killed the player who has been killed
     * @return the death message, null to do not send anything.
     */
    @Nullable
    public Component buildDeathMessage(@NotNull Player killed) {
        return Component.text("{@death." + identifier + "," + killed.getUsername() + "}");
    }

    /**
     * Convenient method to create an {@link EntityProjectileDamage}.
     *
     * @param shooter    the shooter
     * @param projectile the actual projectile
     * @return a new {@link EntityProjectileDamage}
     */
    @NotNull
    public static DamageType fromProjectile(@Nullable Entity shooter, @NotNull Entity projectile) {
        return new EntityProjectileDamage(shooter, projectile);
    }

    /**
     * Convenient method to create an {@link EntityDamage}.
     *
     * @param player the player damager
     * @return a new {@link EntityDamage}
     */
    @NotNull
    public static EntityDamage fromPlayer(@NotNull Player player) {
        return new EntityDamage(player);
    }

    /**
     * Convenient method to create an {@link EntityDamage}.
     *
     * @param entity the entity damager
     * @return a new {@link EntityDamage}
     */
    @NotNull
    public static EntityDamage fromEntity(@NotNull Entity entity) {
        return new EntityDamage(entity);
    }

    /**
     * Builds the text sent to a player in his death screen.
     *
     * @param killed the player who has been killed
     * @return the death screen text, null to do not send anything
     */
    @Nullable
    public String buildDeathScreenText(@NotNull Player killed) {
        return "death." + identifier;
    }

    /**
     * SoundEvent event to play when the given entity is hit by this damage. Possible to return null if no sound should be played
     *
     * @param entity the entity hit by this damage
     * @return the sound to play when the given entity is hurt by this damage type. Can be null if no sound should play
     */
    @Nullable
    public SoundEvent getSound(@NotNull LivingEntity entity) {
        if (entity instanceof Player p) {
            return getPlayerSound(p);
        }
        return getGenericSound(entity);
    }

    protected SoundEvent getGenericSound(@NotNull LivingEntity entity) {
        return switch (entity.getEntityType()) {
            case BAT -> SoundEvent.MOB_BAT_HURT;
            case CHICKEN -> SoundEvent.MOB_CHICKEN_HURT;
            case COW -> SoundEvent.MOB_COW_HURT;
            case RABBIT -> SoundEvent.MOB_RABBIT_HURT;
            case SKELETON -> SoundEvent.MOB_SKELETON_HURT;
            case WITHER_BOSS -> SoundEvent.MOB_WITHER_HURT;
            case WOLF -> SoundEvent.MOB_WOLF_HURT;
            case ZOMBIE -> SoundEvent.MOB_ZOMBIE_HURT;
            default -> SoundEvent.GAME_NEUTRAL_HURT;
        };
    }

    protected SoundEvent getPlayerSound(@NotNull Player player) {
        return SoundEvent.GAME_NEUTRAL_HURT;
    }

    @Override
    public Data getData() {
        return data;
    }

    @Override
    public void setData(Data data) {
        this.data = data;
    }
}
