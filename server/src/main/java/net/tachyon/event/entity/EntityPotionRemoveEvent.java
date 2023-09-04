package net.tachyon.event.entity;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.event.EntityEvent;
import net.tachyon.potion.Potion;
import org.jetbrains.annotations.NotNull;

public class EntityPotionRemoveEvent extends EntityEvent {

    private final Potion potion;

    public EntityPotionRemoveEvent(@NotNull TachyonEntity entity, @NotNull Potion potion) {
        super(entity);
        this.potion = potion;
    }

    /**
     * Returns the potion that was removed.
     *
     * @return the removed potion.
     */
    @NotNull
    public Potion getPotion() {
        return potion;
    }
}
