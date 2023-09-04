package net.tachyon.block;

import net.tachyon.coordinate.Point;
import net.tachyon.data.Data;
import net.tachyon.world.World;

@FunctionalInterface
public interface UpdateConsumer {
    void update(World instance, Point blockPosition, Data data);
}
