package net.tachyon.entity.metadata;

// https://wiki.vg/Object_Data
public interface ObjectDataProvider {

    int getObjectData();

    boolean requiresVelocityPacketAtSpawn();

}
