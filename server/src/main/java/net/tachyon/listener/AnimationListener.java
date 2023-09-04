package net.tachyon.listener;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.player.PlayerHandAnimationEvent;
import net.tachyon.item.ItemStack;
import net.tachyon.network.packet.client.play.ClientAnimationPacket;

public class AnimationListener {

    public static void animationListener(ClientAnimationPacket packet, TachyonPlayer player) {
        final ItemStack itemStack = player.getItemInHand();
        //itemStack.onLeftClick(player);
        PlayerHandAnimationEvent handAnimationEvent = new PlayerHandAnimationEvent(player);
        player.callCancellableEvent(PlayerHandAnimationEvent.class, handAnimationEvent, player::swingHand);
    }

}
