package net.tachyon.entity.metadata.other;

import net.tachyon.coordinate.Vec;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.LivingEntityMeta;
import org.jetbrains.annotations.NotNull;

public interface ArmorStandMeta extends LivingEntityMeta {


    boolean isSmall();

    void setSmall(boolean value) ;

    boolean isHasGravity() ;

    void setHasGravity(boolean value) ;

    boolean isHasArms() ;

    void setHasArms(boolean value) ;

    boolean isHasNoBasePlate();

    void setHasNoBasePlate(boolean value) ;

    boolean isMarker() ;

    void setMarker(boolean value);

    @NotNull
    Vec getHeadRotation() ;

    void setHeadRotation(@NotNull Vec value);
    @NotNull
    Vec getBodyRotation() ;

    void setBodyRotation(@NotNull Vec value);

    @NotNull
    Vec getLeftArmRotation() ;
    void setLeftArmRotation(@NotNull Vec value) ;
    @NotNull
    Vec getRightArmRotation();
    void setRightArmRotation(@NotNull Vec value);

    @NotNull
    Vec getLeftLegRotation() ;

    void setLeftLegRotation(@NotNull Vec value) ;

    @NotNull
    Vec getRightLegRotation() ;

    void setRightLegRotation(@NotNull Vec value) ;

}
