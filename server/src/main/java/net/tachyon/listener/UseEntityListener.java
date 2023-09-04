package net.tachyon.listener;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.TachyonLivingEntity;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.entity.EntityAttackEvent;
import net.tachyon.event.player.PlayerEntityInteractEvent;
import net.tachyon.network.packet.client.play.ClientInteractEntityPacket;

public class UseEntityListener {

    public static void useEntityListener(ClientInteractEntityPacket packet, TachyonPlayer player) {
        final TachyonEntity entity = TachyonEntity.getEntity(packet.targetId);
        if (entity == null)
            return;
        ClientInteractEntityPacket.Type type = packet.type;

        // Player cannot interact with entities he cannot see
        if (!entity.isViewer(player))
            return;

        if (type == ClientInteractEntityPacket.Type.ATTACK) {
            if (entity instanceof TachyonLivingEntity && ((TachyonLivingEntity) entity).isDead()) // Can't attack dead entities
                return;

            EntityAttackEvent entityAttackEvent = new EntityAttackEvent(player, entity);
            player.callEvent(EntityAttackEvent.class, entityAttackEvent);
        } else if (type == ClientInteractEntityPacket.Type.INTERACT) {
            PlayerEntityInteractEvent playerEntityInteractEvent = new PlayerEntityInteractEvent(player, entity);
            player.callEvent(PlayerEntityInteractEvent.class, playerEntityInteractEvent);
        }
        // TODO find difference with INTERACT
        //PlayerEntityInteractEvent playerEntityInteractEvent = new PlayerEntityInteractEvent(player, entity, packet.hand);
        //player.callEvent(PlayerEntityInteractEvent.class, playerEntityInteractEvent);

    }

}
