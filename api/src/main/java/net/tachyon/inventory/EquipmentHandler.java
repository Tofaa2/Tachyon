package net.tachyon.inventory;

import net.tachyon.entity.Entity;

import net.tachyon.item.ItemStack;
import net.tachyon.network.packet.server.play.EntityEquipmentPacket;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an {@link Entity} which can have {@link ItemStack} in hands and armor slots.
 */
public interface EquipmentHandler {

    /**
     * Gets the {@link ItemStack} in main hand.
     *
     * @return the {@link ItemStack} in main hand
     */
    @NotNull
    ItemStack getItemInHand();

    /**
     * Changes the main hand {@link ItemStack}.
     *
     * @param itemStack the main hand {@link ItemStack}
     */
    void setItemInHand(@NotNull ItemStack itemStack);

    /**
     * Gets the helmet.
     *
     * @return the helmet
     */
    @NotNull
    ItemStack getHelmet();

    /**
     * Changes the helmet.
     *
     * @param itemStack the helmet
     */
    void setHelmet(@NotNull ItemStack itemStack);

    /**
     * Gets the chestplate.
     *
     * @return the chestplate
     */
    @NotNull
    ItemStack getChestplate();

    /**
     * Changes the chestplate.
     *
     * @param itemStack the chestplate
     */
    void setChestplate(@NotNull ItemStack itemStack);

    /**
     * Gets the leggings.
     *
     * @return the leggings
     */
    @NotNull
    ItemStack getLeggings();

    /**
     * Changes the leggings.
     *
     * @param itemStack the leggings
     */
    void setLeggings(@NotNull ItemStack itemStack);

    /**
     * Gets the boots.
     *
     * @return the boots
     */
    @NotNull
    ItemStack getBoots();

    /**
     * Changes the boots.
     *
     * @param itemStack the boots
     */
    void setBoots(@NotNull ItemStack itemStack);

    /**
     * Gets the equipment in a specific slot.
     *
     * @param slot the equipment to get the item from
     * @return the equipment {@link ItemStack}
     */
    @NotNull
    default ItemStack getEquipment(@NotNull EntityEquipmentPacket.Slot slot) {
        return switch (slot) {
            case HAND -> getItemInHand();
            case HELMET -> getHelmet();
            case CHESTPLATE -> getChestplate();
            case LEGGINGS -> getLeggings();
            case BOOTS -> getBoots();
        };
    }

    /**
     * Sends a specific equipment to viewers.
     *
     * @param slot the slot of the equipment
     */
    default void syncEquipment(@NotNull EntityEquipmentPacket.Slot slot) {
        if (!(this instanceof Entity entity)) {
            throw new IllegalStateException("Only accessible for TachyonEntity");
        }
        final ItemStack itemStack = getEquipment(slot);
        EntityEquipmentPacket entityEquipmentPacket = new EntityEquipmentPacket(entity.getEntityId(), slot, itemStack);
        entity.sendPacketToViewers(entityEquipmentPacket);
    }

    /**
     * Gets the packet with all the equipments.
     *
     * @return the packet with the equipments
     * @throws IllegalStateException if 'this' is not an {@link Entity}
     */
    @NotNull
    default EntityEquipmentPacket[] getEquipmentsPacket() {
        if (!(this instanceof Entity entity)) {
            throw new IllegalStateException("Only accessible for TachyonEntity");
        }

        final EntityEquipmentPacket.Slot[] slots = EntityEquipmentPacket.Slot.values();

        final EntityEquipmentPacket[] packets = new EntityEquipmentPacket[slots.length];

        // Fill items
        for (int i = 0; i < slots.length; i++) {
            final ItemStack equipment = getEquipment(slots[i]);

            // Create equipment packet
            EntityEquipmentPacket equipmentPacket = new EntityEquipmentPacket(entity.getEntityId(), slots[i], equipment);
            packets[i] = equipmentPacket;
        }

        return packets;
    }

}
