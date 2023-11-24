package net.tachyon.item.metadata;

import net.tachyon.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

// TODO for which item
public class SpawnEggMeta extends ItemMeta {

    private EntityType entityType;

    @Override
    public boolean hasNbt() {
        return entityType != null;
    }

    @Override
    public boolean isSimilar(@NotNull ItemMeta itemMeta) {
        if (!(itemMeta instanceof SpawnEggMeta spawnEggMeta))
            return false;
        return spawnEggMeta.entityType == entityType;
    }

    @Override
    public void read(@NotNull NBTCompound compound) {
        if (compound.containsKey("EntityTag")) {
            // TODO
        }
    }

    @Override
    public void write(@NotNull NBTCompound compound) {
        if (!hasNbt())
            return;
        NBTCompound entityCompound = new NBTCompound();
        if (entityType != null) {
            entityCompound.setString("id", entityType.getNamespaceID());
        }

    }

    @NotNull
    @Override
    public ItemMeta clone() {
        SpawnEggMeta spawnEggMeta = (SpawnEggMeta) super.clone();
        spawnEggMeta.entityType = entityType;
        return spawnEggMeta;
    }
}
