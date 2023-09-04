package net.tachyon.coordinate;

import org.jetbrains.annotations.NotNull;

public record Vec(double x, double y, double z) implements Point {

    public Vec(Vec other) {
        this(other.x, other.y, other.z);
    }

    @Override
    public @NotNull Vec withX(double x) {
        return new Vec(x, y, z);
    }

    @Override
    public @NotNull Vec withY(double y) {
        return new Vec(x, y, z);
    }

    @Override
    public @NotNull Vec withZ(double z) {
        return new Vec(x, y, z);
    }

    @Override
    public @NotNull Vec add(double x, double y, double z) {
        return new Vec(this.x + x, this.y + y, this.z + z);
    }

    @Override
    public @NotNull Vec add(@NotNull Point other) {
        return new Vec(x + other.x(), y + other.y(), z + other.z());
    }

    @Override
    public @NotNull Vec subtract(double x, double y, double z) {
        return new Vec(this.x - x, this.y - y, this.z - z);
    }

    @Override
    public @NotNull Vec subtract(@NotNull Point other) {
        return new Vec(x - other.x(), y - other.y(), z - other.z());
    }

    @Override
    public @NotNull Vec multiply(double x, double y, double z) {
        return new Vec(this.x * x, this.y * y, this.z * z);
    }

    @Override
    public @NotNull Vec multiply(@NotNull Point other) {
        return new Vec(x * other.x(), y * other.y(), z * other.z());
    }

    @Override
    public @NotNull Vec divide(double x, double y, double z) {
        return new Vec(this.x / x, this.y / y, this.z / z);
    }

    @Override
    public @NotNull Vec divide(@NotNull Point other) {
        return new Vec(x / other.x(), y / other.y(), z / other.z());
    }

    @Override
    public @NotNull Vec multiply(double factor) {
        return new Vec(x * factor, y * factor, z * factor);
    }

    @Override
    public @NotNull Vec divide(double factor) {
        return new Vec(x / factor, y / factor, z / factor);
    }

    @Override
    public @NotNull Vec negate() {
        return new Vec(-x, -y, -z);
    }

    @Override
    public @NotNull Vec normalize() {
        return new Vec(x / length(), y / length(), z / length());
    }

    public @NotNull Position toPosition() {
        return new Position(x, y, z, 0, 0);
    }

}
