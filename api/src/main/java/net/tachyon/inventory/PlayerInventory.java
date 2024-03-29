package net.tachyon.inventory;

import net.tachyon.Tachyon;
import net.tachyon.data.Data;
import net.tachyon.data.DataContainer;
import net.tachyon.entity.ArmorSlot;
import net.tachyon.entity.Player;
import net.tachyon.event.item.ArmorEquipEvent;
import net.tachyon.event.player.PlayerAddItemStackEvent;
import net.tachyon.event.player.PlayerSetItemStackEvent;
import net.tachyon.inventory.click.InventoryClickProcessor;
import net.tachyon.inventory.click.ClickType;
import net.tachyon.inventory.click.InventoryClickLoopHandler;
import net.tachyon.inventory.click.InventoryClickResult;
import net.tachyon.inventory.condition.InventoryCondition;
import net.tachyon.item.ItemStack;
import net.tachyon.item.rule.StackingRule;
import net.tachyon.network.packet.server.play.EntityEquipmentPacket;
import net.tachyon.network.packet.server.play.SetSlotPacket;
import net.tachyon.network.packet.server.play.WindowItemsPacket;
import net.tachyon.utils.ArrayUtils;
import net.tachyon.utils.MathUtils;
import net.tachyon.utils.validate.Check;
import net.tachyon.utils.inventory.PlayerInventoryUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents the inventory of a {@link Player}, retrieved with {@link Player#getInventory()}.
 */
public class PlayerInventory implements InventoryModifier, InventoryClickHandler, EquipmentHandler, DataContainer {

    public static final int INVENTORY_SIZE = 45;

    protected final Player player;
    protected final ItemStack[] items = new ItemStack[INVENTORY_SIZE];
    private ItemStack cursorItem = ItemStack.AIR;

    private final List<InventoryCondition> inventoryConditions = new CopyOnWriteArrayList<>();
    private final InventoryClickProcessor clickProcessor = new InventoryClickProcessor();

    private Data data;

    public PlayerInventory(@NotNull Player player) {
        this.player = player;

        ArrayUtils.fill(items, () -> ItemStack.AIR);
    }

    @NotNull
    @Override
    public ItemStack getItemStack(int slot) {
        return this.items[slot];
    }

    @NotNull
    @Override
    public ItemStack[] getItemStacks() {
        return items.clone();
    }

    @NotNull
    @Override
    public List<InventoryCondition> getInventoryConditions() {
        return inventoryConditions;
    }

    @Override
    public void addInventoryCondition(@NotNull InventoryCondition inventoryCondition) {
        InventoryCondition condition = (p, slot, clickType, inventoryConditionResult) -> {
            final int convertedSlot = PlayerInventoryUtils.convertPlayerInventorySlot(slot, PlayerInventoryUtils.OFFSET);
            inventoryCondition.accept(p, convertedSlot, clickType, inventoryConditionResult);
        };

        this.inventoryConditions.add(condition);
    }

    @Override
    public void setItemStack(int slot, @NotNull ItemStack itemStack) {
        PlayerSetItemStackEvent setItemStackEvent = new PlayerSetItemStackEvent(player, slot, itemStack);
        player.callEvent(PlayerSetItemStackEvent.class, setItemStackEvent);
        if (setItemStackEvent.isCancelled())
            return;
        slot = setItemStackEvent.getSlot();
        itemStack = setItemStackEvent.getItemStack();

        safeItemInsert(slot, itemStack);
    }

    @Override
    public synchronized boolean addItemStack(@NotNull ItemStack itemStack) {
        PlayerAddItemStackEvent addItemStackEvent = new PlayerAddItemStackEvent(player, itemStack);
        player.callEvent(PlayerAddItemStackEvent.class, addItemStackEvent);
        if (addItemStackEvent.isCancelled())
            return false;

        itemStack = addItemStackEvent.getItemStack();

        final StackingRule stackingRule = itemStack.getStackingRule();
        for (int i = 0; i < items.length - 10; i++) {
            ItemStack item = items[i];
            final StackingRule itemStackingRule = item.getStackingRule();
            if (itemStackingRule.canBeStacked(itemStack, item)) {
                final int itemAmount = itemStackingRule.getAmount(item);
                if (itemAmount == stackingRule.getMaxSize())
                    continue;
                final int itemStackAmount = itemStackingRule.getAmount(itemStack);
                final int totalAmount = itemStackAmount + itemAmount;
                if (!stackingRule.canApply(itemStack, totalAmount)) {
                    item = itemStackingRule.apply(item, itemStackingRule.getMaxSize());

                    sendSlotRefresh((short) PlayerInventoryUtils.convertToPacketSlot(i), item);
                    itemStack = stackingRule.apply(itemStack, totalAmount - stackingRule.getMaxSize());
                } else {
                    item = item.withAmount((byte) totalAmount);
                    sendSlotRefresh((short) PlayerInventoryUtils.convertToPacketSlot(i), item);
                    return true;
                }
            } else if (item.isAir()) {
                safeItemInsert(i, itemStack);
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        // Clear the item array
        for (int i = 0; i < getSize(); i++) {
            setItemStackInternal(i, ItemStack.AIR);
        }
        // Send the cleared inventory to the inventory's owner
        update();

        // Update equipments
        for (EntityEquipmentPacket entityEquipmentPacket : player.getEquipmentsPacket()) {
            this.player.sendPacketToViewersAndSelf(entityEquipmentPacket);
        }
    }

    @Override
    public int getSize() {
        return INVENTORY_SIZE;
    }

    @NotNull
    @Override
    public ItemStack getItemInHand() {
        return getItemStack(player.getHeldSlot());
    }

    @Override
    public void setItemInHand(@NotNull ItemStack itemStack) {
        safeItemInsert(player.getHeldSlot(), itemStack);
    }

    @NotNull
    @Override
    public ItemStack getHelmet() {
        return getItemStack(PlayerInventoryUtils.HELMET_SLOT);
    }

    @Override
    public void setHelmet(@NotNull ItemStack itemStack) {
        safeItemInsert(PlayerInventoryUtils.HELMET_SLOT, itemStack);
    }

    @NotNull
    @Override
    public ItemStack getChestplate() {
        return getItemStack(PlayerInventoryUtils.CHESTPLATE_SLOT);
    }

    @Override
    public void setChestplate(@NotNull ItemStack itemStack) {
        safeItemInsert(PlayerInventoryUtils.CHESTPLATE_SLOT, itemStack);
    }

    @NotNull
    @Override
    public ItemStack getLeggings() {
        return getItemStack(PlayerInventoryUtils.LEGGINGS_SLOT);
    }

    @Override
    public void setLeggings(@NotNull ItemStack itemStack) {
        safeItemInsert(PlayerInventoryUtils.LEGGINGS_SLOT, itemStack);
    }

    @NotNull
    @Override
    public ItemStack getBoots() {
        return getItemStack(PlayerInventoryUtils.BOOTS_SLOT);
    }

    @Override
    public void setBoots(@NotNull ItemStack itemStack) {
        safeItemInsert(PlayerInventoryUtils.BOOTS_SLOT, itemStack);
    }

    /**
     * Refreshes the player inventory by sending a {@link WindowItemsPacket} containing all.
     * the inventory items
     */
    public void update() {
        player.getPlayerConnection().sendPacket(createWindowItemsPacket());
    }

    /**
     * Refreshes only a specific slot with the updated item stack data.
     *
     * @param slot the slot to refresh
     */
    public void refreshSlot(short slot) {
        final int packetSlot = PlayerInventoryUtils.convertToPacketSlot(slot);
        sendSlotRefresh((short) packetSlot, getItemStack(slot));
    }

    /**
     * Gets the item in player cursor.
     *
     * @return the cursor item
     */
    @NotNull
    public ItemStack getCursorItem() {
        return cursorItem;
    }

    /**
     * Changes the player cursor item.
     *
     * @param cursorItem the new cursor item
     */
    public void setCursorItem(@NotNull ItemStack cursorItem) {
        final boolean similar = this.cursorItem.isSimilar(cursorItem);
        this.cursorItem = cursorItem;

        if (!similar) {
            final SetSlotPacket setSlotPacket = SetSlotPacket.createCursorPacket(cursorItem);
            player.getPlayerConnection().sendPacket(setSlotPacket);
        }
    }

    /**
     * Inserts an item safely (synchronized) in the appropriate slot.
     *
     * @param slot      an internal slot
     * @param itemStack the item to insert at the slot
     * @throws IllegalArgumentException if the slot {@code slot} does not exist
     * @throws NullPointerException     if {@code itemStack} is null
     */
    protected synchronized void safeItemInsert(int slot, @NotNull ItemStack itemStack) {
        Check.argCondition(!MathUtils.isBetween(slot, 0, getSize()),
                "The slot " + slot + " does not exist for player");
        Check.notNull(itemStack, "The TachyonItemStack cannot be null, you can set air instead");

        EntityEquipmentPacket.Slot equipmentSlot;

        if (slot == player.getHeldSlot()) {
            equipmentSlot = EntityEquipmentPacket.Slot.HAND;
        } else {
            ArmorEquipEvent armorEquipEvent = null;

            if (slot == PlayerInventoryUtils.HELMET_SLOT) {
                armorEquipEvent = new ArmorEquipEvent(player, itemStack, ArmorSlot.HELMET);
            } else if (slot == PlayerInventoryUtils.CHESTPLATE_SLOT) {
                armorEquipEvent = new ArmorEquipEvent(player, itemStack, ArmorSlot.CHESTPLATE);
            } else if (slot == PlayerInventoryUtils.LEGGINGS_SLOT) {
                armorEquipEvent = new ArmorEquipEvent(player, itemStack, ArmorSlot.LEGGINGS);
            } else if (slot == PlayerInventoryUtils.BOOTS_SLOT) {
                armorEquipEvent = new ArmorEquipEvent(player, itemStack, ArmorSlot.BOOTS);
            }

            if (armorEquipEvent != null) {
                ArmorSlot armorSlot = armorEquipEvent.getArmorSlot();
                equipmentSlot = EntityEquipmentPacket.Slot.fromArmorSlot(armorSlot);
                player.callEvent(ArmorEquipEvent.class, armorEquipEvent);
                itemStack = armorEquipEvent.getArmorItem();
            } else {
                equipmentSlot = null;
            }
        }

        this.items[slot] = itemStack;

        // Sync equipment
        if (equipmentSlot != null) {
            player.syncEquipment(equipmentSlot);
        }

        // Refresh slot
        update();
        // FIXME: replace update() to refreshSlot, currently not possible because our inventory click handling is not exactly the same as what the client expects
        //refreshSlot((short) slot);
    }

    protected void setItemStackInternal(int slot, ItemStack itemStack) {
        items[slot] = itemStack;
    }

    /**
     * Sets an item from a packet slot.
     *
     * @param slot      a packet slot
     * @param offset    offset (generally 9 to ignore armor and craft slots)
     * @param itemStack the item stack to set
     */
    protected void setItemStack(int slot, int offset, ItemStack itemStack) {
        final int convertedSlot = PlayerInventoryUtils.convertPlayerInventorySlot(slot, offset);
        setItemStack(convertedSlot, itemStack);
    }

    /**
     * Gets the item from a packet slot.
     *
     * @param slot   a packet slot
     * @param offset offset (generally 9 to ignore armor and craft slots)
     * @return the item in the specified slot
     */
    protected ItemStack getItemStack(int slot, int offset) {
        final int convertedSlot = PlayerInventoryUtils.convertPlayerInventorySlot(slot, offset);
        return this.items[convertedSlot];
    }

    /**
     * Refreshes an inventory slot.
     *
     * @param slot      the packet slot,
     *                  see {@link PlayerInventoryUtils#convertToPacketSlot(int)}
     * @param itemStack the item stack in the slot
     */
    protected void sendSlotRefresh(short slot, ItemStack itemStack) {
        SetSlotPacket setSlotPacket = new SetSlotPacket();
        setSlotPacket.windowId = 0;
        setSlotPacket.slot = slot;
        setSlotPacket.itemStack = itemStack;
        player.getPlayerConnection().sendPacket(setSlotPacket);
    }

    /**
     * Gets a {@link WindowItemsPacket} with all the items in the inventory.
     *
     * @return a {@link WindowItemsPacket} with inventory items
     */
    private WindowItemsPacket createWindowItemsPacket() {
        ItemStack[] convertedSlots = new ItemStack[INVENTORY_SIZE];

        for (int i = 0; i < items.length; i++) {
            final int slot = PlayerInventoryUtils.convertToPacketSlot(i);
            convertedSlots[slot] = items[i];
        }

        WindowItemsPacket windowItemsPacket = new WindowItemsPacket();
        windowItemsPacket.windowId = 0;
        windowItemsPacket.items = convertedSlots;
        return windowItemsPacket;
    }

    @Override
    public boolean leftClick(@NotNull Player player, int slot) {
        final ItemStack cursor = getCursorItem();
        final ItemStack clicked = getItemStack(PlayerInventoryUtils.convertPlayerInventorySlot(slot, PlayerInventoryUtils.OFFSET));

        final InventoryClickResult clickResult = clickProcessor.leftClick(null, player, slot, clicked, cursor);

        if (clickResult.doRefresh())
            sendSlotRefresh((short) slot, clicked);

        setItemStack(slot, PlayerInventoryUtils.OFFSET, clickResult.getClicked());
        setCursorItem(clickResult.getCursor());

        if (!clickResult.isCancel())
            callClickEvent(player, null, slot, ClickType.LEFT_CLICK, clicked, cursor);

        return !clickResult.isCancel();
    }

    @Override
    public boolean rightClick(@NotNull Player player, int slot) {
        final ItemStack cursor = getCursorItem();
        final ItemStack clicked = getItemStack(slot, PlayerInventoryUtils.OFFSET);

        final InventoryClickResult clickResult = clickProcessor.rightClick(null, player, slot, clicked, cursor);

        if (clickResult.doRefresh())
            sendSlotRefresh((short) slot, clicked);

        setItemStack(slot, PlayerInventoryUtils.OFFSET, clickResult.getClicked());
        setCursorItem(clickResult.getCursor());

        if (!clickResult.isCancel())
            callClickEvent(player, null, slot, ClickType.RIGHT_CLICK, clicked, cursor);

        return !clickResult.isCancel();
    }

    @Override
    public boolean middleClick(@NotNull Player player, int slot) {
        // TODO
        return false;
    }

    @Override
    public boolean drop(@NotNull Player player, int mode, int slot, int button) {
        final ItemStack cursor = getCursorItem();
        final boolean outsideDrop = slot == -999;
        final ItemStack clicked = outsideDrop ? ItemStack.AIR : getItemStack(slot, PlayerInventoryUtils.OFFSET);

        final InventoryClickResult clickResult = clickProcessor.drop(null, player,
                mode, slot, button, clicked, cursor);

        if (clickResult.doRefresh())
            sendSlotRefresh((short) slot, clicked);

        final ItemStack resultClicked = clickResult.getClicked();
        if (resultClicked != null && !outsideDrop)
            setItemStack(slot, PlayerInventoryUtils.OFFSET, resultClicked);
        setCursorItem(clickResult.getCursor());

        return !clickResult.isCancel();
    }

    @Override
    public boolean shiftClick(@NotNull Player player, int slot) {
        final ItemStack cursor = getCursorItem();
        final ItemStack clicked = getItemStack(slot, PlayerInventoryUtils.OFFSET);

        final boolean hotBarClick = PlayerInventoryUtils.convertToPacketSlot(slot) < 9;
        final InventoryClickResult clickResult = clickProcessor.shiftClick(null, player, slot, clicked, cursor,
                new InventoryClickLoopHandler(0, items.length, 1,
                        i -> {
                            if (hotBarClick) {
                                return i < 9 ? i + 9 : i - 9;
                            } else {
                                return PlayerInventoryUtils.convertPlayerInventorySlot(i, PlayerInventoryUtils.OFFSET);
                            }
                        },
                        index -> getItemStack(index, PlayerInventoryUtils.OFFSET),
                        (index, itemStack) -> setItemStack(index, PlayerInventoryUtils.OFFSET, itemStack)));

        if (clickResult == null)
            return false;

        if (clickResult.doRefresh())
            update();

        setCursorItem(clickResult.getCursor());

        return !clickResult.isCancel();
    }

    @Override
    public boolean changeHeld(@NotNull Player player, int slot, int key) {
        if (!getCursorItem().isAir())
            return false;

        final ItemStack heldItem = getItemStack(key);
        final ItemStack clicked = getItemStack(slot, PlayerInventoryUtils.OFFSET);

        final InventoryClickResult clickResult = clickProcessor.changeHeld(null, player, slot, key, clicked, heldItem);

        if (clickResult.doRefresh()) {
            sendSlotRefresh((short) slot, clicked);
        }

        setItemStack(slot, PlayerInventoryUtils.OFFSET, clickResult.getClicked());
        setItemStack(key, clickResult.getCursor());

        if (!clickResult.isCancel())
            callClickEvent(player, null, slot, ClickType.CHANGE_HELD, clicked, getCursorItem());

        // Weird synchronization issue when omitted
        update();

        return !clickResult.isCancel();
    }

    @Override
    public boolean dragging(@NotNull Player player, int slot, int button) {
        final ItemStack cursor = getCursorItem();
        final ItemStack clicked = slot != -999 ? getItemStack(slot, PlayerInventoryUtils.OFFSET) : ItemStack.AIR;

        final InventoryClickResult clickResult = clickProcessor.dragging(null, player,
                slot, button,
                clicked, cursor, s -> getItemStack(s, PlayerInventoryUtils.OFFSET),
                (s, item) -> setItemStack(s, PlayerInventoryUtils.OFFSET, item));

        if (clickResult == null) {
            return false;
        }

        if (clickResult.doRefresh())
            update();

        setCursorItem(clickResult.getCursor());

        return !clickResult.isCancel();
    }

    @Override
    public boolean doubleClick(@NotNull Player player, int slot) {
        final ItemStack cursor = getCursorItem();

        final InventoryClickResult clickResult = clickProcessor.doubleClick(null, player, slot, cursor,
                new InventoryClickLoopHandler(0, items.length, 1,
                        i -> i < 9 ? i + 9 : i - 9,
                        index -> items[index],
                        this::setItemStack));

        if (clickResult == null)
            return false;

        if (clickResult.doRefresh())
            update();

        setCursorItem(clickResult.getCursor());

        return !clickResult.isCancel();
    }

    @Nullable
    @Override
    public Data getData() {
        return data;
    }

    @Override
    public void setData(@Nullable Data data) {
        this.data = data;
    }
}
