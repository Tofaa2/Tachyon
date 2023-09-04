package net.tachyon.item.metadata;

import net.tachyon.item.Material;
import net.tachyon.potion.CustomPotionEffect;
import net.tachyon.utils.clone.CloneUtils;
import net.tachyon.utils.time.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;
import org.jglrxavpok.hephaistos.nbt.NBTList;
import org.jglrxavpok.hephaistos.nbt.NBTTypes;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Item meta for
 * {@link Material#POTION}.
 */
public class PotionMeta extends ItemMeta {

    // Not final because of #clone()
    private List<CustomPotionEffect> customPotionEffects = new CopyOnWriteArrayList<>();

    /**
     * Get a list of {@link CustomPotionEffect}.
     *
     * @return the custom potion effect list
     */
    @NotNull
    public List<CustomPotionEffect> getCustomPotionEffects() {
        return customPotionEffects;
    }

    @Override
    public boolean hasNbt() {
        return !customPotionEffects.isEmpty();
    }

    @Override
    public boolean isSimilar(@NotNull ItemMeta itemMeta) {
        if (!(itemMeta instanceof PotionMeta potionMeta))
            return false;
        return potionMeta.customPotionEffects.equals(customPotionEffects);
    }

    @Override
    public void read(@NotNull NBTCompound compound) {
        if (compound.containsKey("CustomPotionEffects")) {
            NBTList<NBTCompound> customEffectList = compound.getList("CustomPotionEffects");
            for (NBTCompound potionCompound : customEffectList) {
                final byte id = potionCompound.getAsByte("Id");
                final byte amplifier = potionCompound.getAsByte("Amplifier");
                final int duration = potionCompound.containsKey("Duration") ? potionCompound.getNumber("Duration").intValue() : (int) TimeUnit.SECOND.toMilliseconds(30);
                final boolean ambient = potionCompound.containsKey("Ambient") ? potionCompound.getAsByte("Ambient") == 1 : false;
                final boolean showParticles = potionCompound.containsKey("ShowParticles") ? potionCompound.getAsByte("ShowParticles") == 1 : true;

                this.customPotionEffects.add(
                        new CustomPotionEffect(id, amplifier, duration, ambient, showParticles));
            }
        }
    }

    @Override
    public void write(@NotNull NBTCompound compound) {
        if (!customPotionEffects.isEmpty()) {
            NBTList<NBTCompound> potionList = new NBTList<>(NBTTypes.TAG_Compound);

            for (CustomPotionEffect customPotionEffect : customPotionEffects) {
                NBTCompound potionCompound = new NBTCompound();
                potionCompound.setByte("Id", customPotionEffect.getId());
                potionCompound.setByte("Amplifier", customPotionEffect.getAmplifier());
                potionCompound.setInt("Duration", customPotionEffect.getDuration());
                potionCompound.setByte("Ambient", (byte) (customPotionEffect.isAmbient() ? 1 : 0));
                potionCompound.setByte("ShowParticles", (byte) (customPotionEffect.showParticles() ? 1 : 0));

                potionList.add(potionCompound);
            }

            compound.set("CustomPotionEffects", potionList);
        }

    }

    @NotNull
    @Override
    public ItemMeta clone() {
        PotionMeta potionMeta = (PotionMeta) super.clone();
        potionMeta.customPotionEffects = CloneUtils.cloneCopyOnWriteArrayList(customPotionEffects);

        return potionMeta;
    }
}
