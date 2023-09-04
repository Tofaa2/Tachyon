package net.tachyon.event.item;

import net.tachyon.entity.ArmorSlot;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.event.Event;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ArmorEquipEvent extends Event {

    private final TachyonEntity entity;
    private ItemStack armorItem;
    private final ArmorSlot armorSlot;

    public ArmorEquipEvent(@NotNull TachyonEntity entity, @NotNull ItemStack armorItem, @NotNull ArmorSlot armorSlot) {
        this.entity = entity;
        this.armorItem = armorItem;
        this.armorSlot = armorSlot;
    }

    @NotNull
    public TachyonEntity getEntity() {
        return entity;
    }

    @NotNull
    public ItemStack getArmorItem() {
        return armorItem;
    }

    public void setArmorItem(@NotNull ItemStack armorItem) {
        this.armorItem = armorItem;
    }

    @NotNull
    public ArmorSlot getArmorSlot() {
        return armorSlot;
    }

}
