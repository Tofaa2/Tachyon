package net.tachyon.biome;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.tachyon.namespace.NamespaceID;
import net.tachyon.world.biome.Biome;
import net.tachyon.world.biome.BiomeManager;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;
import org.jglrxavpok.hephaistos.nbt.NBTList;
import org.jglrxavpok.hephaistos.nbt.NBTTypes;

import java.util.Collection;
import java.util.Collections;

/**
 * Allows servers to register custom dimensions. Also used during player joining to send the list of all existing dimensions.
 * <p>
 * Contains {@link Biome#PLAINS} by default but can be removed.
 */
public final class TachyonBiomeManager implements BiomeManager {

    private final Int2ObjectMap<Biome> biomes = new Int2ObjectOpenHashMap<>();

    public TachyonBiomeManager() {
        addBiome(Biome.PLAINS);
    }

    /**
     * Adds a new biome. This does NOT send the new list to players.
     *
     * @param biome the biome to add
     */
    public synchronized void addBiome(Biome biome) {
        this.biomes.put(biome.getId(), biome);
    }

    /**
     * Removes a biome. This does NOT send the new list to players.
     *
     * @param biome the biome to remove
     */
    public synchronized void removeBiome(Biome biome) {
        this.biomes.remove(biome.getId());
    }

    public Collection<Biome> getBiomes() {
        return Collections.unmodifiableCollection(biomes.values());
    }

    /**
     * Gets a biome by its id.
     *
     * @param id the id of the biome
     * @return the {@link Biome} linked to this id
     */
    public Biome getById(int id) {
        return biomes.get(id);
    }

    public Biome getByName(NamespaceID namespaceID) {
        Biome biome = null;
        for (final Biome biomeT : biomes.values()) {
            if (biomeT.getName().equals(namespaceID)) {
                biome = biomeT;
                break;
            }
        }
        return biome;
    }

    public NBTCompound toNBT() {
        NBTCompound biomes = new NBTCompound();
        biomes.setString("type", "minecraft:worldgen/biome");
        NBTList<NBTCompound> biomesList = new NBTList<>(NBTTypes.TAG_Compound);
        for (Biome biome : this.biomes.values()) {
            biomesList.add(biome.toNbt());
        }
        biomes.set("value", biomesList);
        return biomes;
    }
}
