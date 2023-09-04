package net.tachyon.coordinate;

import net.tachyon.utils.MathUtils;
import org.jetbrains.annotations.NotNull;

public record Position(double x, double y, double z, float yaw, float pitch) implements Point {

    public static @NotNull Position ZERO = new Position(0, 0, 0, 0, 0);


    public Position(double x, double y, double z) {
        this(x, y, z, 0, 0);
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    @Override
    public @NotNull Position withX(double x) {
        return new Position(x, y, z, yaw, pitch);
    }

    @Override
    public @NotNull Position withY(double y) {
        return new Position(x, y, z, yaw, pitch);
    }

    @Override
    public @NotNull Position withZ(double z) {
        return new Position(x, y, z, yaw, pitch);
    }

    @Override
    public @NotNull Position add(double x, double y, double z) {
        return new Position(this.x + x, this.y + y, this.z + z, yaw, pitch);
    }

    @Override
    public @NotNull Position add(@NotNull Point other) {
        return new Position(x + other.x(), y + other.y(), z + other.z(), yaw, pitch);
    }

    @Override
    public @NotNull Position subtract(double x, double y, double z) {
        return new Position(this.x - x, this.y - y, this.z - z, yaw, pitch);
    }

    @Override
    public @NotNull Position subtract(@NotNull Point other) {
        return new Position(x - other.x(), y - other.y(), z - other.z(), yaw, pitch);
    }

    @Override
    public @NotNull Position multiply(double x, double y, double z) {
        return new Position(this.x * x, this.y * y, this.z * z, yaw, pitch);
    }

    @Override
    public @NotNull Position multiply(@NotNull Point other) {
        return new Position(x * other.x(), y * other.y(), z * other.z(), yaw, pitch);
    }

    @Override
    public @NotNull Position divide(double x, double y, double z) {
        return new Position(this.x / x, this.y / y, this.z / z, yaw, pitch);
    }

    public @NotNull Position withYaw(float yaw) {
        return new Position(x, y, z, yaw, pitch);
    }

    public @NotNull Position withPitch(float pitch) {
        return new Position(x, y, z, yaw, pitch);
    }

    public @NotNull Position add(float yaw, float pitch) {
        return new Position(x, y, z, this.yaw + yaw, this.pitch + pitch);
    }

    @Override
    public @NotNull Position divide(@NotNull Point other) {
        return new Position(x / other.x(), y / other.y(), z / other.z(), yaw, pitch);
    }

    @Override
    public @NotNull Position multiply(double factor) {
        return new Position(x * factor, y * factor, z * factor, yaw, pitch);
    }

    @Override
    public @NotNull Position divide(double factor) {
        return new Position(x / factor, y / factor, z / factor, yaw, pitch);
    }

    @Override
    public @NotNull Position negate() {
        return new Position(-x, -y, -z, yaw, pitch);
    }

    public Vec toVector() {
        return new Vec(x, y, z);
    }

    @Override
    public @NotNull Position normalize() {
        return new Position(x / length(), y / length(), z / length(), yaw, pitch);
    }

    public Vec direction() {
        return new Vec(
                Math.cos(Math.toRadians(pitch)) * Math.cos(Math.toRadians(yaw)),
                Math.sin(Math.toRadians(pitch)),
                Math.cos(Math.toRadians(pitch)) * Math.sin(Math.toRadians(yaw))
        );
    }

    public @NotNull Position withDirection(@NotNull Vec vector) {
        /*
         * Sin = Opp / Hyp
         * Cos = Adj / Hyp
         * Tan = Opp / Adj
         *
         * x = -Opp
         * z = Adj
         */
        final double _2PI = 2 * Math.PI;
        final double x = vector.getX();
        final double z = vector.getZ();

        float pitch = this.pitch;
        float yaw = this.yaw;

        if (x == 0 && z == 0) {
            pitch = vector.getY() > 0 ? -90 : 90;
            return this;
        }

        final double theta = Math.atan2(-x, z);
        yaw = (float) Math.toDegrees((theta + _2PI) % _2PI);

        final double x2 = MathUtils.square(x);
        final double z2 = MathUtils.square(z);
        final double xz = Math.sqrt(x2 + z2);
        pitch = (float) Math.toDegrees(Math.atan(-vector.getY() / xz));
        return new Position(x, y, z, yaw, pitch);
    }
}
