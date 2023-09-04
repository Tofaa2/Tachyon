package net.tachyon.entity.metadata;

import net.kyori.adventure.text.Component;

public interface EntityMeta {

    void setNotifyAboutChanges(boolean notifyAboutChanges);

    boolean isOnFire();

    void setOnFire(boolean value);

    boolean isSneaking();

    void setSneaking(boolean value);

    boolean isSprinting();

    void setSprinting(boolean value);

    boolean isEating();

    void setEating(boolean value);

    boolean isInvisible();

    void setInvisible(boolean value);

    short getAirTicks();

    void setAirTicks(short value);

    Component getCustomName();

    void setCustomName(Component value);

    boolean isCustomNameVisible();

    void setCustomNameVisible(boolean value);

    boolean isSilent();

    void setSilent(boolean value);

}
