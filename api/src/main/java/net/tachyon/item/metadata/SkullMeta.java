package net.tachyon.item.metadata;

import net.tachyon.entity.Player;
import net.tachyon.entity.PlayerSkin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;
import org.jglrxavpok.hephaistos.nbt.NBTList;
import org.jglrxavpok.hephaistos.nbt.NBTTypes;

/**
 * Represents a skull that can have an owner.
 */
public class SkullMeta extends ItemMeta {

    private String skullOwner;
    private PlayerSkin playerSkin;

    /**
     * Sets the owner of the skull.
     *
     * @param player The new owner of the skull.
     * @return {@code true} if the owner was successfully set, otherwise {@code false}.
     */
    public boolean setOwningPlayer(@NotNull Player player) {
        if (player.getSkin() != null) {
            this.skullOwner = player.getUsername();
            this.playerSkin = player.getSkin();
            return true;
        }
        return false;
    }

    /**
     * Retrieves the owner of the head.
     *
     * @return The head's owner.
     */
    @Nullable
    public String getSkullOwner() {
        return skullOwner;
    }

    /**
     * Changes the owner of the head.
     *
     * @param skullOwner The new head owner.
     */
    public void setSkullOwner(@NotNull String skullOwner) {
        this.skullOwner = skullOwner;
    }

    /**
     * Retrieves the skin of the head.
     *
     * @return The head's skin.
     */
    @Nullable
    public PlayerSkin getPlayerSkin() {
        return playerSkin;
    }

    /**
     * Changes the skin of the head.
     *
     * @param playerSkin The new skin for the head.
     */
    public void setPlayerSkin(@NotNull PlayerSkin playerSkin) {
        this.playerSkin = playerSkin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNbt() {
        return this.skullOwner != null || playerSkin != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSimilar(@NotNull ItemMeta itemMeta) {
        if (!(itemMeta instanceof SkullMeta))
            return false;
        final SkullMeta skullMeta = (SkullMeta) itemMeta;
        return skullMeta.playerSkin == playerSkin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void read(@NotNull NBTCompound compound) {
        if (compound.containsKey("SkullOwner")) {
            NBTCompound skullOwnerCompound = compound.getCompound("SkullOwner");

            if (skullOwnerCompound.containsKey("Name")) {
                this.skullOwner = skullOwnerCompound.getString("Name");
            }

            if (skullOwnerCompound.containsKey("Properties")) {
                NBTCompound propertyCompound = skullOwnerCompound.getCompound("Properties");

                if (propertyCompound.containsKey("textures")) {
                    NBTList<NBTCompound> textures = propertyCompound.getList("textures");
                    if (textures != null) {
                        NBTCompound nbt = textures.get(0);
                        this.playerSkin = new PlayerSkin(nbt.getString("Value"), nbt.getString("Signature"));
                    }
                }

            }

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(@NotNull NBTCompound compound) {
        NBTCompound skullOwnerCompound = new NBTCompound();
        // Sets the identifier for the skull
        skullOwnerCompound.setString("Name", this.skullOwner);

        if (this.playerSkin == null) {
            this.playerSkin = PlayerSkin.fromUuid(this.skullOwner);
        }

        if (this.playerSkin != null) {
            NBTList<NBTCompound> textures = new NBTList<>(NBTTypes.TAG_Compound);
            String value = this.playerSkin.getTextures() == null ? "" : this.playerSkin.getTextures();
            String signature = this.playerSkin.getSignature() == null ? "" : this.playerSkin.getSignature();
            textures.add(new NBTCompound().setString("Value", value).setString("Signature", signature));
            skullOwnerCompound.set("Properties", new NBTCompound().set("textures", textures));
        }

        compound.set("SkullOwner", skullOwnerCompound);

    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ItemMeta clone() {
        SkullMeta skullMeta = (SkullMeta) super.clone();
        skullMeta.skullOwner = this.skullOwner;
        skullMeta.playerSkin = this.playerSkin;
        return skullMeta;
    }


}
