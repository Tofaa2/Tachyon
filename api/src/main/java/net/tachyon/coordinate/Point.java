package net.tachyon.coordinate;

import net.tachyon.utils.MathUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a point in a 3d space. Implementations are immutable and sealed for api use.
 */
public sealed interface Point permits Position, Vec {

    @NotNull Vec ZERO = new Vec(0, 0, 0);

    double x();

    double y();

    double z();

    default double getX() {
        return x();
    }

    default double getY() {
        return y();
    }

    default double getZ() {
        return z();
    }

    default int blockX() {
        return (int) Math.floor(x());
    }

    default int blockY() {
        return (int) Math.floor(y());
    }

    default int blockZ() {
        return (int) Math.floor(z());
    }

    @NotNull Point withX(double x);

    @NotNull Point withY(double y);

    @NotNull Point withZ(double z);

    @NotNull Point add(double x, double y, double z);

    @NotNull Point add(@NotNull Point other);

    @NotNull Point subtract(double x, double y, double z);

    @NotNull Point subtract(@NotNull Point other);

    @NotNull Point multiply(double x, double y, double z);

    @NotNull Point multiply(@NotNull Point other);

    @NotNull Point divide(double x, double y, double z);

    @NotNull Point divide(@NotNull Point other);

    @NotNull Point multiply(double factor);

    @NotNull Point divide(double factor);

    @NotNull Point negate();

    @NotNull Point normalize();

    default double distanceSquared(double x, double y, double z) {
        return MathUtils.square(x() - x) + MathUtils.square(y() - y) + MathUtils.square(z() - z);
    }

    default double distanceSquared(@NotNull Point other) {
        return distanceSquared(other.x(), other.y(), other.z());
    }

    default double distance(double x, double y, double z) {
        return Math.sqrt(distanceSquared(x, y, z));
    }

    default double distance(@NotNull Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    default double lengthSquared() {
        return distanceSquared(0, 0, 0);
    }

    default double length() {
        return distance(0, 0, 0);
    }

    default Point getRelative(BlockFace face) {
        return switch(face) {
            case BOTTOM -> subtract(0, 1, 0);
            case TOP -> add(0, 1, 0);
            case NORTH -> subtract(0, 0, 1);
            case SOUTH -> add(0, 0, 1);
            case WEST -> subtract(1, 0, 0);
            case EAST -> add(1, 0, 0);
        };
    }

    default boolean inSameChunk(@NotNull Point other) {
        // TODO: Replace this with ChunkUtils#getChunkCoordinate(dx);
        final int chunkX1 = chunkCoord(x());
        final int chunkX2 = chunkCoord(other.x());
        final int chunkZ1 = chunkCoord(z());
        final int chunkZ2 = chunkCoord(other.z());
        return chunkX1 == chunkX2 && chunkZ1 == chunkZ2;
    }

    default boolean isZero() {
        return isSimilar(ZERO);
    }

    default boolean isSimilar(@NotNull Point other) {
        return Double.compare(x(), other.x()) == 0 &&
                Double.compare(y(), other.y()) == 0 &&
                Double.compare(z(), other.z()) == 0;
    }

    private static int chunkCoord(double dx) {
        final int coordinate = (int) Math.floor(dx);
        return Math.floorDiv(coordinate, 16);
    }

}
