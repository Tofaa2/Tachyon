package net.tachyon;

import io.netty.buffer.ByteBuf;
import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.block.Block;
import net.tachyon.data.Data;
import net.tachyon.data.SerializableData;
import net.tachyon.data.SerializableDataImpl;
import net.tachyon.entity.EntityType;
import net.tachyon.entity.Player;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.item.Enchantment;
import net.tachyon.item.Material;
import net.tachyon.namespace.NamespaceID;
import net.tachyon.particle.Particle;
import net.tachyon.potion.PotionEffect;
import net.tachyon.registry.Registries;
import net.tachyon.world.InstanceContainer;
import net.tachyon.utils.binary.TachyonBinaryReader;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import net.tachyon.world.World;
import net.tachyon.world.chunk.TachyonChunk;
import net.tachyon.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class UnsafeServer implements Unsafe {
    @Override
    public void changeDidCloseInventory(Player player, boolean didCloseInventory) {
        ((TachyonPlayer) player).UNSAFE_changeDidCloseInventory(didCloseInventory);
    }

    @Override
    public void setBlock(Chunk chunk, int x, int y, int z, short blockStateId, short customBlockId, @Nullable Data data, boolean updatable) {
        ((TachyonChunk) chunk).UNSAFE_setBlock(x, y, z, blockStateId, customBlockId, data, updatable);
    }

    @Override
    public void addViewableChunk(Player player, Chunk chunk) {
        ((TachyonPlayer) player).getViewableChunks().add((TachyonChunk) chunk);
    }

    @Override
    public void removeViewableChunk(Player player, Chunk chunk) {
        ((TachyonPlayer) player).getViewableChunks().remove((TachyonChunk) chunk);
    }

    @Override
    public @Nullable EntityType getEntityType(NamespaceID id) {
        return Registries.getEntityType(id);
    }

    @Override
    public @NotNull SerializableData serializableDataImpl() {
        return new SerializableDataImpl();
    }

    @Override
    public @NotNull BinaryReader newBinaryReader(byte[] bytes) {
        return new TachyonBinaryReader(bytes);
    }

    @Override
    public @NotNull BinaryWriter newBinaryWriter() {
        return new TachyonBinaryWriter();
    }

    @Override
    public @NotNull BinaryWriter newBinaryWriter(ByteBuf... buffers) {
        return new TachyonBinaryWriter(buffers);
    }

    @Override
    public void refreshLastBlockChangeTime(World world) {
        if (world instanceof InstanceContainer instance) {
            instance.refreshLastBlockChangeTime();
        }
    }

    @NotNull
    @Override
    public Material getMaterial(NamespaceID id) {
        return Registries.getMaterial(id);
    }

    @Override
    public @Nullable Particle getParticle(NamespaceID id) {
        return Registries.getParticle(id);
    }

    @Override
    public @Nullable Enchantment getEnchantment(NamespaceID id) {
        return Registries.getEnchantment(id);
    }

    @Override
    public @Nullable PotionEffect getPotionEffect(NamespaceID id) {
        return Registries.getPotionEffect(id);
    }

    @Override
    public @NotNull Block getBlock(NamespaceID id) {
        return Registries.getBlock(id);
    }
}
