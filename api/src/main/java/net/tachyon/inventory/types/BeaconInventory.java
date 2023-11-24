package net.tachyon.inventory.types;

import net.kyori.adventure.text.Component;
import net.tachyon.inventory.Inventory;
import net.tachyon.inventory.InventoryProperty;
import net.tachyon.inventory.InventoryType;
import net.tachyon.potion.PotionEffect;

public class BeaconInventory extends Inventory {

    private short powerLevel;
    private PotionEffect firstPotionEffect;
    private PotionEffect secondPotionEffect;

    public BeaconInventory(Component title) {
        super(InventoryType.BEACON, title);
    }

    /**
     * Gets the beacon power level.
     *
     * @return the power level
     */
    public short getPowerLevel() {
        return powerLevel;
    }

    /**
     * Changes the beacon power level.
     *
     * @param powerLevel the new beacon power level
     */
    public void setPowerLevel(short powerLevel) {
        this.powerLevel = powerLevel;
        sendProperty(InventoryProperty.BEACON_POWER_LEVEL, powerLevel);
    }

    /**
     * Gets the first potion effect.
     *
     * @return the first potion effect, can be null
     */
    public PotionEffect getFirstPotionEffect() {
        return firstPotionEffect;
    }

    /**
     * Changes the first potion effect.
     *
     * @param firstPotionEffect the new first potion effect, can be null
     */
    public void setFirstPotionEffect(PotionEffect firstPotionEffect) {
        this.firstPotionEffect = firstPotionEffect;
        sendProperty(InventoryProperty.BEACON_FIRST_POTION, (short) firstPotionEffect.getId());
    }

    /**
     * Gets the second potion effect.
     *
     * @return the second potion effect, can be null
     */
    public PotionEffect getSecondPotionEffect() {
        return secondPotionEffect;
    }

    /**
     * Changes the second potion effect.
     *
     * @param secondPotionEffect the new second potion effect, can be null
     */
    public void setSecondPotionEffect(PotionEffect secondPotionEffect) {
        this.secondPotionEffect = secondPotionEffect;
        sendProperty(InventoryProperty.BEACON_SECOND_POTION, (short) secondPotionEffect.getId());
    }

}
