package net.tachyon.inventory.types;

import net.kyori.adventure.text.Component;
import net.tachyon.inventory.Inventory;
import net.tachyon.inventory.InventoryProperty;
import net.tachyon.inventory.InventoryType;

public class BrewingStandInventory extends Inventory {

    private short brewTime;
    private short fuelTime;

    public BrewingStandInventory(Component title) {
        super(InventoryType.BREWING_STAND, title);
    }

    /**
     * Gets the brewing stand brew time.
     *
     * @return the brew time in tick
     */
    public short getBrewTime() {
        return brewTime;
    }

    /**
     * Changes the brew time.
     *
     * @param brewTime the new brew time in tick
     */
    public void setBrewTime(short brewTime) {
        this.brewTime = brewTime;
        sendProperty(InventoryProperty.BREWING_STAND_BREW_TIME, brewTime);
    }

    /**
     * Gets the brewing stand fuel time.
     *
     * @return the fuel time in tick
     */
    public short getFuelTime() {
        return fuelTime;
    }

    /**
     * Changes the fuel time.
     *
     * @param fuelTime the new fuel time in tick
     */
    public void setFuelTime(short fuelTime) {
        this.fuelTime = fuelTime;
        sendProperty(InventoryProperty.BREWING_STAND_FUEL_TIME, fuelTime);
    }

}
