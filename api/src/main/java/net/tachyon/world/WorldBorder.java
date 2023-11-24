package net.tachyon.world;

import net.tachyon.coordinate.Point;
import org.jetbrains.annotations.NotNull;

public interface WorldBorder {

    float getCenterX();

    void setCenterX(float centerX);

    float getCenterZ();

    void setCenterZ(float centerZ);

    int getWarningTime();

    void setWarningTime(int warningTime);

    int getWarningBlocks();

    void setWarningBlocks(int warningBlocks);


    @NotNull World getWorld();

    double getDiameter();

    void setDiameter(double diameter);

    void setDiameter(double diameter, long speed);

    boolean isInside(@NotNull Point point);

    CollisionAxis getCollisionAxis(@NotNull Point point);

    enum CollisionAxis {
        X,
        Z,
        BOTH,
        NONE
    }

}
