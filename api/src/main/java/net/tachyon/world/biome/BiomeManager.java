package net.tachyon.world.biome;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import java.util.Collection;

public interface BiomeManager {


    void addBiome(Biome biome);

    void removeBiome(Biome biome);

    /**
     * Returns an immutable copy of the biomes already registered.
     *
     * @return an immutable copy of the biomes already registered
     */
    @NotNull Collection<Biome> getBiomes();

    @Nullable Biome getById(int id);

    @NotNull NBTCompound toNBT();

}
