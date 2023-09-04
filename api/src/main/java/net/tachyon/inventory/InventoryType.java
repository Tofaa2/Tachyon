package net.tachyon.inventory;

/**
 * Represents a type of {@link Inventory}
 */
public enum InventoryType {

    CHEST_1_ROW("minecraft:chest", 9),
    CHEST_2_ROW("minecraft:chest", 18),
    CHEST_3_ROW("minecraft:chest", 27),
    CHEST_4_ROW("minecraft:chest", 36),
    CHEST_5_ROW("minecraft:chest", 45),
    CHEST_6_ROW("minecraft:chest", 54),
    DISPENSER("minecraft:dispenser", 9),
    ANVIL("minecraft:anvil", 3),
    BEACON("minecraft:beacon", 1),
    BREWING_STAND("minecraft:brewing_stand", 5),
    CRAFTING("minecraft:crafting_table", 10),
    ENCHANTMENT("minecraft:enchanting_table", 2),
    FURNACE("minecraft:furnace", 3),
    VILLAGER("minecraft:villager", 3),
    HOPPER("minecraft:hopper", 5),
    HORSE("EntityHorse", 2);

    private final String windowType;
    private final int slot;

    InventoryType(String windowType, int slot) {
        this.windowType = windowType;
        this.slot = slot;
    }

    public String getWindowType() {
        return windowType;
    }

    public int getAdditionalSlot() {
        return slot;
    }

}
