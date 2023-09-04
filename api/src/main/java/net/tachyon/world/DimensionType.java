package net.tachyon.world;

import org.jetbrains.annotations.Nullable;

/**
 * https://minecraft.gamepedia.com/Custom_dimension
 */
public enum DimensionType {
    OVERWORLD((byte) 0, true),
    NETHER((byte) -1, false),
    END((byte) 1, false);

    private final byte id;
    private final boolean hasSky;

    DimensionType(byte id, boolean hasSky) {
        this.id = id;
        this.hasSky = hasSky;
    }

    public byte getId() {
        return id;
    }

    public boolean getHasSky() {
        return hasSky;
    }

    @Nullable
    public static DimensionType fromId(byte id) {
        for (DimensionType dimensionType : values()) {
            if (dimensionType.id == id) {
                return dimensionType;
            }
        }
        return null;
    }
}
