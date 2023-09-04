package net.tachyon.item.metadata;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

public class MapMeta extends ItemMeta {

    private TextColor mapColor = null;

    public MapMeta() {
    }

    /**
     * Gets the map color.
     *
     * @return the map color
     */
    public TextColor getMapColor() {
        return mapColor;
    }

    /**
     * Changes the map color.
     * <p>
     * WARNING: RGB colors are not supported.
     *
     * @param mapColor the new map color
     */
    public void setMapColor(TextColor mapColor) {
        if (!(mapColor instanceof NamedTextColor)) {
            throw new IllegalArgumentException("Expected NamedTextColor");
        }
        this.mapColor = mapColor;
    }

    @Override
    public boolean hasNbt() {
        return true;
    }

    @Override
    public boolean isSimilar(@NotNull ItemMeta itemMeta) {
        if (!(itemMeta instanceof MapMeta))
            return false;

        final MapMeta mapMeta = (MapMeta) itemMeta;
        return mapMeta.mapColor == mapColor;
    }

    @Override
    public void read(@NotNull NBTCompound compound) {
        if (compound.containsKey("display")) {
            final NBTCompound displayCompound = compound.getCompound("display");
            if (displayCompound.containsKey("MapColor")) {
                final int color = displayCompound.getAsInt("MapColor");
                this.mapColor = NamedTextColor.ofExact(color);
            }
        }

    }

    @Override
    public void write(@NotNull NBTCompound compound) {
        {
            NBTCompound displayCompound;
            if (compound.containsKey("display")) {
                displayCompound = compound.getCompound("display");
            } else {
                displayCompound = new NBTCompound();
            }
            if (mapColor != null) {
                displayCompound.setInt("MapColor", mapColor.value());
            }
        }
    }

    @NotNull
    @Override
    public ItemMeta clone() {
        MapMeta mapMeta = (MapMeta) super.clone();
        mapMeta.setMapColor(mapColor);
        return mapMeta;
    }

}
