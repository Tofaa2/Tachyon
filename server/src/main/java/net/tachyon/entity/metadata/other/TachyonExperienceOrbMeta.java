package net.tachyon.entity.metadata.other;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonExperienceOrbMeta extends TachyonEntityMeta implements ExperienceOrbMeta {

    private int count = 1;

    public TachyonExperienceOrbMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public int getCount() {
        return count;
    }

    /**
     * Sets count of orbs.
     * This is possible only before spawn packet is sent.
     *
     * @param count count of orbs.
     */
    public void setCount(int count) {
        this.count = count;
    }
}
