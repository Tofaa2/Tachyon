package net.tachyon.instance;

import net.tachyon.block.Block;
import net.tachyon.coordinate.Point;
import net.tachyon.network.packet.server.play.ExplosionPacket;
import net.tachyon.utils.PacketUtils;
import net.tachyon.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Abstract explosion.
 * Instance can provide a supplier through {@link Instance#setExplosionSupplier}
 */
public abstract class Explosion {

    private final float centerX;
    private final float centerY;
    private final float centerZ;
    private final float strength;

    public Explosion(float centerX, float centerY, float centerZ, float strength) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.centerZ = centerZ;
        this.strength = strength;
    }

    public float getStrength() {
        return strength;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public float getCenterZ() {
        return centerZ;
    }

    /**
     * Prepares the list of blocks that will be broken. Also pushes and damage entities affected by this explosion
     *
     * @param instance instance to perform this explosion in
     * @return list of blocks that will be broken.
     */
    protected abstract List<Point> prepare(World instance);

    /**
     * Performs the explosion and send the corresponding packet
     *
     * @param instance instance to perform this explosion in
     */
    public void apply(@NotNull World instance) {
        List<Point> blocks = prepare(instance);

        byte[] records = new byte[3 * blocks.size()];
        for (int i = 0; i < blocks.size(); i++) {
            final Point pos = blocks.get(i);
            instance.setBlock(pos, Block.AIR);
            final byte x = (byte) (pos.getX() - Math.floor(getCenterX()));
            final byte y = (byte) (pos.getY() - Math.floor(getCenterY()));
            final byte z = (byte) (pos.getZ() - Math.floor(getCenterZ()));
            records[i * 3] = x;
            records[i * 3 + 1] = y;
            records[i * 3 + 2] = z;
        }

        ExplosionPacket packet = new ExplosionPacket(getCenterX(), getCenterY(), getCenterZ(), getStrength(), records, 0.0f, 0.0f, 0.0f);
        postExplosion(instance, blocks, packet);

        // TODO send only to close players
        PacketUtils.sendGroupedPacket(instance.getPlayers(), packet);

        postSend(instance, blocks);
    }

    /**
     * Called after removing blocks and preparing the packet, but before sending it.
     *
     * @param instance the instance in which the explosion occurs
     * @param blocks   the block positions returned by prepare
     * @param packet   the explosion packet to sent to the client. Be careful with what you're doing.
     *                 It is initialized with the center and radius of the explosion. The positions in 'blocks' are also
     *                 stored in the packet before this call, but you are free to modify 'records' to modify the blocks sent to the client.
     *                 Just be careful, you might just crash the server or the client. Or you're lucky, both at the same time.
     */
    protected void postExplosion(World instance, List<Point> blocks, ExplosionPacket packet) {
    }

    /**
     * Called after sending the explosion packet. Can be used to (re)set blocks that have been destroyed.
     * This is necessary to do after the packet being sent, because the client sets the positions received to air.
     *
     * @param instance the instance in which the explosion occurs
     * @param blocks   the block positions returned by prepare
     */
    protected void postSend(World instance, List<Point> blocks) {
    }
}
