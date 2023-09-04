package net.tachyon.world;

import org.jetbrains.annotations.NotNull;

public interface SharedWorld extends World {

    @NotNull World getSharedInstance();

}
