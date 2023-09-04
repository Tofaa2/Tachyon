package net.tachyon.listener.manager;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.network.packet.client.ClientPlayPacket;

/**
 * Small convenient interface to use method references with {@link PacketListenerManager#setListener(Class, PacketListenerConsumer)}.
 *
 * @param <T> the packet type
 */
@FunctionalInterface
public interface PacketListenerConsumer<T extends ClientPlayPacket> {
    void accept(T packet, TachyonPlayer player);
}
