package net.tachyon.block;

public class BlockVariation {
    private final byte metadata;
    private final String displayName;

    public BlockVariation(byte metadata, String displayName) {
        this.metadata = metadata;
        this.displayName = displayName;
    }

    public byte getMetadata() {
        return metadata;
    }

    public String getDisplayName() {
        return displayName;
    }
}
