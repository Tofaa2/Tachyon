package net.tachyon;

import io.netty.buffer.ByteBuf;
import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.block.Block;
import net.tachyon.data.Data;
import net.tachyon.data.SerializableData;
import net.tachyon.entity.EntityType;
import net.tachyon.entity.Player;
import net.tachyon.item.Enchantment;
import net.tachyon.item.Material;
import net.tachyon.namespace.NamespaceID;
import net.tachyon.network.packet.server.play.EntityEquipmentPacket;
import net.tachyon.particle.Particle;
import net.tachyon.potion.PotionEffect;
import net.tachyon.world.World;
import net.tachyon.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Unsafe {

    /**
     * Pretends the player did close the inventory they had open.
     * @param player
     * @param didCloseInventory
     */
    void changeDidCloseInventory(Player player, boolean didCloseInventory);

    void setBlock(Chunk chunk, int x, int y, int z, short blockStateId, short customBlockId, @Nullable Data data, boolean updatable);

    void addViewableChunk(Player player, Chunk chunk);

    void removeViewableChunk(Player player, Chunk chunk);

    @NotNull SerializableData serializableDataImpl();

    @NotNull BinaryReader newBinaryReader(byte[] bytes);

    @NotNull BinaryWriter newBinaryWriter();

    @NotNull BinaryWriter newBinaryWriter(ByteBuf... buffers);

    void refreshLastBlockChangeTime(World world);

    @Nullable EntityType getEntityType(NamespaceID id);

    default @Nullable EntityType getEntityType(String id) {
        return getEntityType(NamespaceID.from(id));
    }

    @NotNull Material getMaterial(NamespaceID id);

    default @NotNull Material getMaterial(String id) {
        return getMaterial(NamespaceID.from(id));
    }

    @Nullable Particle getParticle(NamespaceID id);

    default @Nullable Particle getParticle(String id) {
        return getParticle(NamespaceID.from(id));
    }

    @Nullable Enchantment getEnchantment(NamespaceID id);

    default @Nullable Enchantment getEnchantment(String id) {
        return getEnchantment(NamespaceID.from(id));
    }

    @Nullable PotionEffect getPotionEffect(NamespaceID id);

    default @Nullable PotionEffect getPotionEffect(String id) {
        return getPotionEffect(NamespaceID.from(id));
    }

    @NotNull Block getBlock(NamespaceID id);

    default @NotNull Block getBlock(String id) {
        return getBlock(NamespaceID.from(id));
    }
 }
