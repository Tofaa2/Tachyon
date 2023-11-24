package net.tachyon.entity;

public interface ExperienceOrb extends Entity {

    /**
     * Gets the experience count.
     *
     * @return the experience count
     */
    short getExperienceCount();

    /**
     * Changes the experience count.
     *
     * @param experienceCount the new experience count
     */
    void setExperienceCount(short experienceCount);

}
