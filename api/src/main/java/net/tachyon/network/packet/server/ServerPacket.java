package net.tachyon.network.packet.server;

import net.tachyon.binary.Writeable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet which can be sent to a player using {@link PlayerConnection#sendPacket(ServerPacket)}.
 */
public interface ServerPacket extends Writeable {

    /**
     * Gets the id of this packet.
     * <p>
     * Written in the final buffer header so it needs to match the client id.
     *
     * @return the id of this packet
     */
    int getId();

}
