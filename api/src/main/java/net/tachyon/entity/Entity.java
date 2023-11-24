package net.tachyon.entity;

import io.netty.util.internal.UnstableApi;
import net.kyori.adventure.text.Component;
import net.tachyon.Viewable;
import net.tachyon.collision.BoundingBox;
import net.tachyon.coordinate.Position;
import net.tachyon.coordinate.Vec;
import net.tachyon.entity.metadata.EntityMeta;
import net.tachyon.event.EventHandler;
import net.tachyon.potion.Potion;
import net.tachyon.potion.PotionEffect;
import net.tachyon.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.UUID;

public interface Entity extends Viewable, EventHandler {
    @NotNull UUID getUuid();

    @UnknownNullability World getWorld();

    void setVelocity(@NotNull Vec velocity);

    @NotNull Metadata getMetadata();

    @NotNull EntityType getEntityType();

    void setView(float yaw, float pitch);

    void setView(Position position);

    void setWorld(@NotNull World world);

    void setWorld(@NotNull World world, Position position);

    double getDistanceSquared(@NotNull Entity entity);

    void setBoundingBox(double x, double y, double z);

    @Nullable Entity getVehicle();

    @Nullable Entity getPassenger();

    @NotNull EntityMeta getEntityMeta();

    @NotNull BoundingBox getBoundingBox();

    void setPassenger(@Nullable Entity passenger);

    boolean hasPassenger();

    @NotNull Position getPosition();

    @NotNull Position getLastLocation();
    @NotNull Vec getVelocityForPacket();

    @NotNull Vec getVelocity();

    boolean hasVelocity();

    boolean hasNoGravity();

    @UnstableApi
    /**
     * Internal use only. This will move the entity for the clients to the set location in the next tick.
     */
    void modifyPosition(Position position);

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
