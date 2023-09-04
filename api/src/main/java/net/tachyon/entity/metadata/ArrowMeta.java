package net.tachyon.entity.metadata;

public interface ArrowMeta extends ProjectileMeta, ObjectDataProvider {

    boolean isCritical();

    void setCritical(boolean value);

}
