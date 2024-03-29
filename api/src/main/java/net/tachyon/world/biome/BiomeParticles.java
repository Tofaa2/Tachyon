package net.tachyon.world.biome;

import net.tachyon.block.Block;
import net.tachyon.block.BlockAlternative;
import net.tachyon.namespace.NamespaceID;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import java.util.Map;

public class BiomeParticles {

    private final float probability;
    private final ParticleOptions options;

    public BiomeParticles(float probability, ParticleOptions options) {
        this.probability = probability;
        this.options = options;
    }

    public NBTCompound toNbt() {
        NBTCompound nbt = new NBTCompound();
        nbt.setFloat("probability", probability);
        nbt.set("options", options.toNbt());
        return nbt;
    }

    public interface ParticleOptions {
        NBTCompound toNbt();
    }

    public static class BlockParticle implements ParticleOptions {

        //TODO also can be falling_dust
        private static final String type = "block";

        private final BlockAlternative block;

        public BlockParticle(BlockAlternative block) {
            this.block = block;
        }

        @Override
        public NBTCompound toNbt() {
            NBTCompound nbtCompound = new NBTCompound();
            Block block1 = Block.fromStateId(block.getId());
            nbtCompound.setString("type", type);
            nbtCompound.setString("Name", block1.getName());
            Map<String, String> propertiesMap = block.createPropertiesMap();
            if (propertiesMap.size() != 0) {
                NBTCompound properties = new NBTCompound();
                propertiesMap.forEach(properties::setString);
                nbtCompound.set("Properties", properties);
            }
            return nbtCompound;
        }

    }

    public static class DustParticle implements ParticleOptions {

        private static final String type = "dust";

        private final float red;
        private final float green;
        private final float blue;
        private final float scale;

        public DustParticle(float red, float green, float blue, float scale) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.scale = scale;
        }

        @Override
        public NBTCompound toNbt() {
            NBTCompound nbtCompound = new NBTCompound();
            nbtCompound.setString("type", type);
            nbtCompound.setFloat("r", red);
            nbtCompound.setFloat("g", green);
            nbtCompound.setFloat("b", blue);
            nbtCompound.setFloat("scale", scale);
            return nbtCompound;
        }

    }

//    public static class ItemParticle implements ParticleOptions {
//
//        private static final String type = "item";
//
//        private final ItemStack item;
//
//        public ItemParticle(ItemStack item) {
//            this.item = item;
//        }
//
//        @Override
//        public NBTCompound toNbt() {
//            //todo test count might be wrong type
//            NBTCompound nbtCompound = item.toNBT();
//            nbtCompound.setString("type", type);
//            return nbtCompound;
//        }
//
//    }

    public static class NormalParticle implements ParticleOptions {

        private final NamespaceID type;

        public NormalParticle(@NotNull NamespaceID type) {
            this.type = type;
        }

        @Override
        public NBTCompound toNbt() {
            NBTCompound nbtCompound = new NBTCompound();
            nbtCompound.setString("type", type.toString());
            return nbtCompound;
        }

    }
}
