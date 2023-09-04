package net.tachyon.entity;

import net.tachyon.coordinate.Point;
import net.tachyon.coordinate.Vec;
import net.tachyon.entity.metadata.ObjectDataProvider;
import net.tachyon.entity.metadata.other.TachyonExperienceOrbMeta;
import net.tachyon.entity.metadata.other.TachyonPaintingMeta;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.play.*;
import net.tachyon.namespace.NamespaceID;

public enum EntitySpawnType {
    OBJECT {
        @Override
        public ServerPacket getSpawnPacket(TachyonEntity entity) {
            SpawnObjectPacket packet = new SpawnObjectPacket();
            packet.entityId = entity.getEntityId();
            packet.type = entity.getEntityType().getProtocolId();
            packet.position = entity.getPosition();
            if (entity.getEntityMeta() instanceof ObjectDataProvider) {
                ObjectDataProvider objectDataProvider = (ObjectDataProvider) entity.getEntityMeta();
                packet.data = objectDataProvider.getObjectData();
                if (objectDataProvider.requiresVelocityPacketAtSpawn()) {
                    Point velocity = entity.getVelocityForPacket();
                    packet.velocityX = (short) velocity.getX();
                    packet.velocityY = (short) velocity.getY();
                    packet.velocityZ = (short) velocity.getZ();
                }
            }
            return packet;
        }
    },
    MOB {
        @Override
        public ServerPacket getSpawnPacket(TachyonEntity entity) {
            SpawnMobPacket packet = new SpawnMobPacket();
            packet.entityId = entity.getEntityId();
            packet.entityType = entity.getEntityType().getProtocolId();
            packet.position = entity.getPosition();
            packet.headPitch = entity.getPosition().getPitch();
            Point velocity = entity.getVelocityForPacket();
            packet.velocityX = (short) velocity.getX();
            packet.velocityY = (short) velocity.getY();
            packet.velocityZ = (short) velocity.getZ();
            packet.metadataEntries = entity.getMetadata().getEntries();
            return packet;
        }
    },
    PLAYER {
        @Override
        public ServerPacket getSpawnPacket(TachyonEntity entity) {
            SpawnPlayerPacket packet = new SpawnPlayerPacket();
            packet.entityId = entity.getEntityId();
            packet.playerUuid = entity.getUuid();
            packet.position = entity.getPosition();
            byte heldItem = 0;
            if (entity instanceof TachyonPlayer) {
                heldItem = ((TachyonPlayer) entity).getHeldSlot();
            }
            packet.heldItem = heldItem;
            packet.metadataEntries = entity.getMetadata().getEntries();
            return packet;
        }
    },
    EXPERIENCE_ORB {
        @Override
        public ServerPacket getSpawnPacket(TachyonEntity entity) {
            SpawnExperienceOrbPacket packet = new SpawnExperienceOrbPacket();
            packet.entityId = entity.getEntityId();
            packet.position = entity.getPosition();
            if (entity.getEntityMeta() instanceof TachyonExperienceOrbMeta experienceOrbMeta) {
                packet.expCount = (short) experienceOrbMeta.getCount();
            }
            return packet;
        }
    },
    PAINTING {
        @Override
        public ServerPacket getSpawnPacket(TachyonEntity entity) {
            SpawnPaintingPacket packet = new SpawnPaintingPacket();
            packet.entityId = entity.getEntityId();
            if (entity.getEntityMeta() instanceof TachyonPaintingMeta paintingMeta) {
                packet.title = NamespaceID.from(paintingMeta.getMotive().getName()).getPath();
                packet.position = new Vec(
                        Math.max(0, (paintingMeta.getMotive().getWidth() >> 1) - 1),
                        paintingMeta.getMotive().getHeight() >> 1,
                        0
                );
                switch (paintingMeta.getDirection()) {
                    case NORTH -> packet.direction = 0;
                    case WEST -> packet.direction = 1;
                    case SOUTH -> packet.direction = 2;
                    case EAST -> packet.direction = 3;
                }
            } else {
                packet.position = new Vec(0, 0, 0);
            }
            return packet;
        }
    };

    public abstract ServerPacket getSpawnPacket(TachyonEntity entity);
    
}
