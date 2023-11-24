package net.tachyon.event.entity;

import net.tachyon.entity.Entity;
import net.tachyon.event.EntityEvent;
import net.tachyon.potion.Potion;
import org.jetbrains.annotations.NotNull;

public class EntityPotionAddEvent extends EntityEvent {

    private final Potion potion;

    public EntityPotionAddEvent(@NotNull Entity entity, @NotNull Potion potion) {
        super(entity);
        this.potion = potion;
    }

    /**
     * Returns the potion that was added.
     *
     * @return the added potion.
     */
    @NotNull
    public Potion getPotion() {
        return potion;
    }
}
