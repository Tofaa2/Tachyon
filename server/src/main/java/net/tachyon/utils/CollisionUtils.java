package net.tachyon.utils;

import net.tachyon.collision.BoundingBox;
import net.tachyon.coordinate.Point;
import net.tachyon.coordinate.Position;
import net.tachyon.coordinate.Vec;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.instance.TachyonChunk;
import net.tachyon.instance.Instance;
import net.tachyon.instance.InstanceWorldBorder;
import net.tachyon.block.Block;
import net.tachyon.utils.chunk.ChunkUtils;
import org.jetbrains.annotations.NotNull;

public class CollisionUtils {

    private static final Vec Y_AXIS = new Vec(0, 1, 0);
    private static final Vec X_AXIS = new Vec(1, 0, 0);
    private static final Vec Z_AXIS = new Vec(0, 0, 1);

    /**
     * Moves an entity with physics applied (ie checking against blocks)
     *
     * @param entity        the entity to move
     * @param deltaPosition
     * @param positionOut   the Position object in which the new position will be saved
     * @param velocityOut   the Vector object in which the new velocity will be saved
     * @return whether this entity is on the ground
     */
    public static boolean handlePhysics(@NotNull TachyonEntity entity,
                                        @NotNull Vec deltaPosition,
                                        @NotNull Position positionOut,
                                        @NotNull Vec velocityOut) {
        // TODO handle collisions with nearby entities (should it be done here?)
        final Instance instance = entity.getInstance();
        final Position currentPosition = entity.getPosition();
        final BoundingBox boundingBox = entity.getBoundingBox();

        Vec intermediaryPosition = Vec.ZERO;
        final boolean yCollision = stepAxis(instance, currentPosition.toVector(), Y_AXIS, deltaPosition.getY(),
                intermediaryPosition,
                deltaPosition.getY() > 0 ? boundingBox.getTopFace() : boundingBox.getBottomFace()
        );

        final boolean xCollision = stepAxis(instance, intermediaryPosition, X_AXIS, deltaPosition.getX(),
                intermediaryPosition,
                deltaPosition.getX() < 0 ? boundingBox.getLeftFace() : boundingBox.getRightFace()
        );

        final boolean zCollision = stepAxis(instance, intermediaryPosition, Z_AXIS, deltaPosition.getZ(),
                intermediaryPosition,
                deltaPosition.getZ() > 0 ? boundingBox.getBackFace() : boundingBox.getFrontFace()
        );
        positionOut = positionOut.withX(intermediaryPosition.getX())
                        .withY(intermediaryPosition.getY())
                        .withZ(intermediaryPosition.getZ());
        velocityOut = deltaPosition;
        if (xCollision) {
            velocityOut = velocityOut.withX(0f);
        }
        if (yCollision) {
            velocityOut = velocityOut.withY(0f);
        }
        if (zCollision) {
            velocityOut = velocityOut.withZ(0f);
        }

        return yCollision && deltaPosition.getY() < 0;
    }

    /**
     * Steps on a single axis. Checks against collisions for each point of 'corners'. This method assumes that startPosition is valid.
     * Immediately return false if corners are of length 0.
     *
     * @param instance      instance to check blocks from
     * @param startPosition starting position for stepping can be intermediary position from last step
     * @param axis          step direction. Works best if unit vector and aligned to an axis
     * @param stepAmount    how much to step in the direction (in blocks)
     * @param positionOut   the vector in which to store the new position
     * @param corners       the corners to check against
     * @return true if a collision has been found
     */
    private static boolean stepAxis(Instance instance, Vec startPosition, Vec axis, double stepAmount, Vec positionOut, Vec[] corners) {
        int len = corners.length;
        if (len == 0)
            return false;
        Point[] cornerPositions = new Vec[len];
        Vec[] cornersCopy = new Vec[corners.length];
        for (int i = 0; i < corners.length; i++) {
            cornersCopy[i] = corners[i];
            cornerPositions[i] = new Vec(corners[i]);
        }

        final double sign = Math.signum(stepAmount);
        final int blockLength = (int) stepAmount;
        final double remainingLength = stepAmount - blockLength;
        // used to determine if 'remainingLength' should be used
        boolean collisionFound = false;
        for (int i = 0; i < Math.abs(blockLength); i++) {
            if (!stepOnce(instance, axis, sign, cornersCopy, cornerPositions).collided) {
                collisionFound = true;
            }
            if (collisionFound) {
                break;
            }
        }

        // add remainingLength
        if (!collisionFound) {
            collisionFound = !stepOnce(instance, axis, remainingLength, cornersCopy, cornerPositions).collided;
        }

        // find the corner which moved the least
        double smallestDisplacement = Double.POSITIVE_INFINITY;
        for (int i = 0; i < corners.length; i++) {
            final double displacement = corners[i].distance(cornersCopy[i]);
            if (displacement < smallestDisplacement) {
                smallestDisplacement = displacement;
            }
        }

        positionOut = startPosition;
        positionOut.add(smallestDisplacement * axis.getX() * sign, smallestDisplacement * axis.getY() * sign, smallestDisplacement * axis.getZ() * sign);
        return collisionFound;
    }

    /**
     * Steps once (by a length of 1 block) on the given axis.
     *
     * @param instance        instance to get blocks from
     * @param axis            the axis to move along
     * @param amount
     * @param cornersCopy     the corners of the bounding box to consider (mutable)
     * @param cornerPositions the corners, converted to BlockPosition (mutable)
     * @return false if this method encountered a collision
     */
    private static StepOnceData stepOnce(Instance instance, Vec axis, double amount, Vec[] cornersCopy, Point[] cornerPositions) {
        final double sign = Math.signum(amount);
        StepOnceData data = new StepOnceData(instance, axis, amount, cornersCopy, cornerPositions);
        for (int cornerIndex = 0; cornerIndex < cornersCopy.length; cornerIndex++) {
            Vec corner = data.cornersCopy[cornerIndex];
            Point blockPos = data.cornerPositions[cornerIndex];
            corner = corner.add(axis.getX() * amount, axis.getY() * amount, axis.getZ() * amount);
            data.cornerPositions[cornerIndex] = new Vec(
                    Math.floor(corner.getX()),
                    Math.floor(corner.getY()),
                    Math.floor(corner.getZ())
            );
            blockPos = data.cornerPositions[cornerIndex];

            final TachyonChunk chunk = instance.getChunkAt(blockPos);
            if (!ChunkUtils.isLoaded(chunk)) {
                // Collision at chunk border
                data.collided = false;
                return data;
            }

            final short blockStateId = chunk.getBlockStateId(blockPos.blockX(), blockPos.blockY(), blockPos.blockZ());
            final Block block = Block.fromStateId(blockStateId);

            // TODO: block collision boxes
            // TODO: for the moment, always consider a full block
            if (block.isSolid()) {
                corner.subtract(axis.getX() * amount, axis.getY() * amount, axis.getZ() * amount);

                if (Math.abs(axis.getX()) > 10e-16) {
                    corner = corner.withX(blockPos.getX() - axis.getX() * sign);
                }
                if (Math.abs(axis.getY()) > 10e-16) {
                    corner = corner.withY(blockPos.getY() - axis.getY() * sign);
                }
                if (Math.abs(axis.getZ()) > 10e-16) {
                    corner = corner.withZ(blockPos.getZ() - axis.getZ() * sign);
                }
                data.collided = false;
                return data;
            }
        }
        data.collided = true;
        return data;
    }

    private static class StepOnceData {
        public Instance instance;
        public Vec axis;
        public double amount;
        public Vec[] cornersCopy;
        public Point[] cornerPositions;
        public boolean collided = false;

        public StepOnceData(Instance instance, Vec axis, double amount, Vec[] cornersCopy, Point[] cornerPositions) {
            this.instance = instance;
            this.axis = axis;
            this.amount = amount;
            this.cornersCopy = cornersCopy;
            this.cornerPositions = cornerPositions;
        }
    }

    /**
     * Applies world border collision.
     *
     * @param instance        the instance where the world border is
     * @param currentPosition the current position
     * @param newPosition     the future target position
     * @return the position with the world border collision applied (can be {@code newPosition} if not changed)
     */
    @NotNull
    public static Position applyWorldBorder(@NotNull Instance instance,
                                            @NotNull Position currentPosition, @NotNull Position newPosition) {
        final InstanceWorldBorder worldBorder = instance.getWorldBorder();
        final InstanceWorldBorder.CollisionAxis collisionAxis = worldBorder.getCollisionAxis(newPosition);
        return switch (collisionAxis) {
            case NONE ->
                // Apply velocity + gravity
                    newPosition;
            case BOTH ->
                // Apply Y velocity/gravity
                    new Position(currentPosition.getX(), newPosition.getY(), currentPosition.getZ());
            case X ->
                // Apply Y/Z velocity/gravity
                    new Position(currentPosition.getX(), newPosition.getY(), newPosition.getZ());
            case Z ->
                // Apply X/Y velocity/gravity
                    new Position(newPosition.getX(), newPosition.getY(), currentPosition.getZ());
        };
    }

}
