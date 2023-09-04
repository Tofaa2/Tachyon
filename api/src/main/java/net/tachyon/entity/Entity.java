package net.tachyon.entity;

import net.kyori.adventure.text.Component;
import net.tachyon.Viewable;
import net.tachyon.collision.BoundingBox;
import net.tachyon.coordinate.Position;
import net.tachyon.coordinate.Vec;
import net.tachyon.entity.metadata.EntityMeta;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.potion.Potion;
import net.tachyon.potion.PotionEffect;
import net.tachyon.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Entity extends Viewable {

    @NotNull UUID getUuid();

    @NotNull World getWorld();

    @Nullable Entity getVehicle();

    @Nullable Entity getPassenger();

    @NotNull EntityMeta getEntityMeta();

    @NotNull BoundingBox getBoundingBox();

    void setPassenger(@Nullable Entity passenger);

    boolean hasPassenger();

    @NotNull Position getPosition();

    @NotNull Position getLastLocation();


    @NotNull Vec getVelocity();

    boolean hasVelocity();

    boolean hasNoGravity();

    double getGravityTerminalVelocity();

    double getGravityAcceleration();

    double getGravityDragPerTick();

    double getDistance(@NotNull Entity other);


    //@NotNull EntityType getType();
    boolean isOnFire();

    void setOnFire(boolean onFire);

    boolean isSneaking();

    void setSneaking(boolean sneaking);

    boolean isSprinting();

    void setSprinting(boolean sprinting);

    boolean isInvisible();

    void setInvisible(boolean invisible);

    @Nullable Component getCustomName();

    boolean isCustomNameVisible();

    void setCustomName(@Nullable Component customName);

    boolean isSilent();

    void setSilent(boolean silent);

    double getEyeHeight();


    boolean isOnGround();

    boolean isAutoViewable();

    int getEntityId();

    boolean isActive();

    boolean isRemoved();

    void remove();

    boolean hasPhysics();

    long getAliveTicks();

    void addEffect(@NotNull Potion potion);

    void removeEffect(@NotNull PotionEffect effect);

    void clearEffects();

}
