package net.tachyon.world;

public enum LevelType {
    DEFAULT("default"), FLAT("flat"), LARGE_BIOMES("largeBiomes"), AMPLIFIED("amplified"), DEFAULT_1_1("default_1_1");

    private final String id;

    LevelType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
