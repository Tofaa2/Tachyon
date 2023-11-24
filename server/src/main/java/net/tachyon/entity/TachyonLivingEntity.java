package net.tachyon.entity;

import net.tachyon.attribute.Attribute;
import net.tachyon.attribute.AttributeInstance;
import net.tachyon.collision.BoundingBox;
import net.tachyon.coordinate.Point;
import net.tachyon.coordinate.Position;
import net.tachyon.coordinate.Vec;
import net.tachyon.entity.damage.DamageType;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import net.tachyon.entity.metadata.TachyonLivingEntityMeta;
import net.tachyon.event.entity.EntityDamageEvent;
import net.tachyon.event.entity.EntityDeathEvent;
import net.tachyon.event.entity.EntityFireEvent;
import net.tachyon.event.item.ArmorEquipEvent;
import net.tachyon.event.item.PickupItemEvent;
import net.tachyon.instance.TachyonChunk;
import net.tachyon.block.Block;
import net.tachyon.inventory.EquipmentHandler;
import net.tachyon.item.ItemStack;
import net.tachyon.network.ConnectionState;
import net.tachyon.network.packet.server.play.*;
import net.tachyon.network.player.PlayerConnection;
import net.tachyon.scoreboard.Team;
import net.tachyon.sound.SoundEvent;
import net.tachyon.utils.block.BlockIterator;
import net.tachyon.utils.time.CooldownUtils;
import net.tachyon.utils.time.TimeUnit;
import net.tachyon.utils.time.UpdateOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//TODO: Default attributes registration (and limitation ?)
public class TachyonLivingEntity extends TachyonEntity implements EquipmentHandler, LivingEntity {

    // Item pickup
    protected boolean canPickupItem;
    protected UpdateOption itemPickupCooldown = new UpdateOption(5, TimeUnit.TICK);
    private long lastItemPickupCheckTime;

    protected boolean isDead;

    protected DamageType lastDamageSource;

    // Bounding box used for items' pickup (see TachyonLivingEntity#setBoundingBox)
    protected BoundingBox expandedBoundingBox;

    private final Map<String, AttributeInstance> attributeModifiers = new ConcurrentHashMap<>();

    // Abilities
    protected boolean invulnerable;

    /**
     * Time at which this entity must be extinguished
     */
    private long fireExtinguishTime;

    /**
     * Last time the fire damage was applied
     */
    private long lastFireDamageTime;

    /**
     * Period, in ms, between two fire damage applications
     */
    private long fireDamagePeriod = 1000L;

    private Team team;

    private byte arrowCount;
    private float health = 1F;

    // Equipments
    private ItemStack handItem;

    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;

    /**
     * Constructor which allows to specify an UUID. Only use if you know what you are doing!
     */
    public TachyonLivingEntity(@NotNull EntityType entityType, @NotNull UUID uuid) {
        this(entityType, uuid, Position.ZERO);
        setupAttributes();
        setGravity(0.02f, 0.08f, 3.92f);
        initEquipments();
    }

    public TachyonLivingEntity(@NotNull EntityType entityType) {
        this(entityType, UUID.randomUUID());
    }

    /**
     * Constructor which allows to specify an UUID. Only use if you know what you are doing!
     */
    @Deprecated
    public TachyonLivingEntity(@NotNull EntityType entityType, @NotNull UUID uuid, @NotNull Position spawnPosition) {
        super(entityType, uuid, spawnPosition);
        setupAttributes();
        setGravity(0.02f, 0.08f, 3.92f);
        initEquipments();
    }

    @Deprecated
    public TachyonLivingEntity(@NotNull EntityType entityType, @NotNull Position spawnPosition) {
        this(entityType, UUID.randomUUID(), spawnPosition);
    }

    private void initEquipments() {
        this.handItem = ItemStack.AIR;

        this.helmet =ItemStack.AIR;
        this.chestplate = ItemStack.AIR;
        this.leggings = ItemStack.AIR;
        this.boots = ItemStack.AIR;
    }

    @NotNull
    @Override
    public ItemStack getItemInHand() {
        return handItem;
    }

    @Override
    public void setItemInHand(@NotNull ItemStack itemStack) {
        this.handItem = itemStack;
        syncEquipment(EntityEquipmentPacket.Slot.HAND);
    }

    @NotNull
    @Override
    public ItemStack getHelmet() {
        return helmet;
    }

    @Override
    public void setHelmet(@NotNull ItemStack itemStack) {
        this.helmet = getEquipmentItem(itemStack, ArmorSlot.HELMET);
        syncEquipment(EntityEquipmentPacket.Slot.HELMET);
    }

    @NotNull
    @Override
    public ItemStack getChestplate() {
        return chestplate;
    }

    @Override
    public void setChestplate(@NotNull ItemStack itemStack) {
        this.chestplate = getEquipmentItem(itemStack, ArmorSlot.CHESTPLATE);
        syncEquipment(EntityEquipmentPacket.Slot.CHESTPLATE);
    }

    @NotNull
    @Override
    public ItemStack getLeggings() {
        return leggings;
    }

    @Override
    public void setLeggings(@NotNull ItemStack itemStack) {
        this.leggings = getEquipmentItem(itemStack, ArmorSlot.LEGGINGS);
        syncEquipment(EntityEquipmentPacket.Slot.LEGGINGS);
    }

    @NotNull
    @Override
    public ItemStack getBoots() {
        return boots;
    }

    @Override
    public void setBoots(@NotNull ItemStack itemStack) {
        this.boots = getEquipmentItem(itemStack, ArmorSlot.BOOTS);
        syncEquipment(EntityEquipmentPacket.Slot.BOOTS);
    }

    private ItemStack getEquipmentItem(@NotNull ItemStack itemStack, @NotNull ArmorSlot armorSlot) {
        ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(this, itemStack, armorSlot);
        callEvent(ArmorEquipEvent.class, armorEquipEvent);
        return armorEquipEvent.getArmorItem();
    }

    @Override
    public void update(long time) {
        if (isOnFire()) {
            if (time > fireExtinguishTime) {
                setOnFire(false);
            } else {
                if (time - lastFireDamageTime > fireDamagePeriod) {
                    damage(DamageType.ON_FIRE, 1.0f);
                    lastFireDamageTime = time;
                }
            }
        }

        // Items picking
        if (canPickupItems() && !CooldownUtils.hasCooldown(time, lastItemPickupCheckTime, itemPickupCooldown)) {
            this.lastItemPickupCheckTime = time;

            final TachyonChunk chunk = getChunk(); // TODO check surrounding chunks
            final Set<TachyonEntity> entities = instance.getChunkEntities(chunk);
            for (TachyonEntity entity : entities) {
                if (entity instanceof TachyonItemEntity itemEntity) {

                    // Do not pickup if not visible
                    if (this instanceof TachyonPlayer && !entity.isViewer((TachyonPlayer) this))
                        continue;

                    if (!itemEntity.isPickable())
                        continue;

                    final BoundingBox itemBoundingBox = itemEntity.getBoundingBox();
                    if (expandedBoundingBox.intersect(itemBoundingBox)) {
                        if (itemEntity.shouldRemove() || itemEntity.isRemoveScheduled())
                            continue;
                        PickupItemEvent pickupItemEvent = new PickupItemEvent(this, itemEntity);
                        callCancellableEvent(PickupItemEvent.class, pickupItemEvent, () -> {
                            final ItemStack item = itemEntity.getItemStack();

                            CollectItemPacket collectItemPacket = new CollectItemPacket(itemEntity.getEntityId(), getEntityId());
                            sendPacketToViewersAndSelf(collectItemPacket);
                            entity.remove();
                        });
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public byte getArrowCount() {
        return this.arrowCount;
    }

    /**
     * {@inheritDoc}
     */
    public void setArrowCount(byte arrowCount) {
        this.arrowCount = arrowCount;
        TachyonLivingEntityMeta meta = getLivingEntityMeta();
        if (meta != null) {
            meta.setArrowCount(arrowCount);
        }
    }

    /**
     * Gets if the entity is invulnerable.
     *
     * @return true if the entity is invulnerable
     */
    public boolean isInvulnerable() {
        return invulnerable;
    }

    /**
     * Makes the entity vulnerable or invulnerable.
     *
     * @param invulnerable should the entity be invulnerable
     */
    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }

    /**
     * Kills the entity, trigger the {@link EntityDeathEvent} event.
     */
    public void kill() {
        refreshIsDead(true); // So the entity isn't killed over and over again
        triggerStatus((byte) 3); // Start death animation status
        setHealth(0);

        // Reset velocity
        velocity = Vec.ZERO;

        EntityDeathEvent entityDeathEvent = new EntityDeathEvent(this);
        callEvent(EntityDeathEvent.class, entityDeathEvent);
    }

    /**
     * Sets fire to this entity for a given duration.
     *
     * @param duration duration in ticks of the effect
     */
    public void setFireForDuration(int duration) {
        setFireForDuration(duration, TimeUnit.TICK);
    }

    /**
     * Sets fire to this entity for a given duration.
     *
     * @param duration duration of the effect
     * @param unit     unit used to express the duration
     * @see #setOnFire(boolean) if you want it to be permanent without any event callback
     */
    public void setFireForDuration(int duration, TimeUnit unit) {
        EntityFireEvent entityFireEvent = new EntityFireEvent(this, duration, unit);

        // Do not start fire event if the fire needs to be removed (< 0 duration)
        if (duration > 0) {
            callCancellableEvent(EntityFireEvent.class, entityFireEvent, () -> {
                final long fireTime = entityFireEvent.getFireTime(TimeUnit.MILLISECOND);
                setOnFire(true);
                fireExtinguishTime = System.currentTimeMillis() + fireTime;
            });
        } else {
            fireExtinguishTime = System.currentTimeMillis();
        }
    }

    /**
     * Damages the entity by a value, the type of the damage also has to be specified.
     *
     * @param type  the damage type
     * @param value the amount of damage
     * @return true if damage has been applied, false if it didn't
     */
    public boolean damage(@NotNull DamageType type, float value) {
        if (isDead())
            return false;
        if (isInvulnerable() || isImmune(type)) {
            return false;
        }

        EntityDamageEvent entityDamageEvent = new EntityDamageEvent(this, type, value);
        callCancellableEvent(EntityDamageEvent.class, entityDamageEvent, () -> {
            // Set the last damage type since the event is not cancelled
            this.lastDamageSource = entityDamageEvent.getDamageType();

            float remainingDamage = entityDamageEvent.getDamage();

            EntityAnimationPacket entityAnimationPacket = new EntityAnimationPacket(getEntityId(), EntityAnimationPacket.Animation.TAKE_DAMAGE);
            sendPacketToViewersAndSelf(entityAnimationPacket);

            // Additional hearts support
            if (this instanceof TachyonPlayer player) {
                final float additionalHearts = player.getAdditionalHearts();
                if (additionalHearts > 0) {
                    if (remainingDamage > additionalHearts) {
                        remainingDamage -= additionalHearts;
                        player.setAdditionalHearts(0);
                    } else {
                        player.setAdditionalHearts(additionalHearts - remainingDamage);
                        remainingDamage = 0;
                    }
                }
            }

            // Set the final entity health
            setHealth(getHealth() - remainingDamage);

            // play damage sound
            final SoundEvent sound = type.getSound(this);
            if (sound != null) {
                SoundEffectPacket damageSoundPacket = new SoundEffectPacket();
                damageSoundPacket.soundName = sound.getId();
                damageSoundPacket.position = getPosition();
                damageSoundPacket.pitch = 1.0f;
                damageSoundPacket.volume = 1.0f;
                sendPacketToViewersAndSelf(damageSoundPacket);
            }
        });

        return !entityDamageEvent.isCancelled();
    }

    /**
     * Is this entity immune to the given type of damage?
     *
     * @param type the type of damage
     * @return true if this entity is immune to the given type of damage
     */
    public boolean isImmune(@NotNull DamageType type) {
        return false;
    }

    /**
     * Gets the entity health.
     *
     * @return the entity health
     */
    public float getHealth() {
        return this.health;
    }

    /**
     * Changes the entity health, kill it if {@code health} is &gt;= 0 and is not dead yet.
     *
     * @param health the new entity health
     */
    public void setHealth(float health) {
        this.health = Math.min(health, getMaxHealth());
        if (this.health <= 0 && !isDead) {
            kill();
        }
        TachyonLivingEntityMeta meta = getLivingEntityMeta();
        if (meta != null) {
            meta.setHealth(this.health);
        }
    }

    /**
     * Gets the last damage source which damaged of this entity.
     *
     * @return the last damage source, null if not any
     */
    @Nullable
    public DamageType getLastDamageSource() {
        return lastDamageSource;
    }

    /**
     * Gets the entity max health from {@link #getAttributeValue(Attribute)} {@link Attribute#MAX_HEALTH}.
     *
     * @return the entity max health
     */
    public float getMaxHealth() {
        return getAttributeValue(Attribute.MAX_HEALTH);
    }

    /**
     * Sets the heal of the entity as its max health.
     * <p>
     * Retrieved from {@link #getAttributeValue(Attribute)} with the attribute {@link Attribute#MAX_HEALTH}.
     */
    public void heal() {
        setHealth(getAttributeValue(Attribute.MAX_HEALTH));
    }

    /**
     * Retrieves the attribute instance and its modifiers.
     *
     * @param attribute the attribute instance to get
     * @return the attribute instance
     */
    @NotNull
    public AttributeInstance getAttribute(@NotNull Attribute attribute) {
        return attributeModifiers.computeIfAbsent(attribute.getKey(),
                s -> new AttributeInstance(attribute, this::onAttributeChanged));
    }

    /**
     * Callback used when an attribute instance has been modified.
     *
     * @param attributeInstance the modified attribute instance
     */
    protected void onAttributeChanged(@NotNull AttributeInstance attributeInstance) {
        if (attributeInstance.getAttribute().isShared()) {
            boolean self = false;
            if (this instanceof TachyonPlayer) {
                TachyonPlayer player = (TachyonPlayer) this;
                PlayerConnection playerConnection = player.playerConnection;
                // connection null during Player initialization (due to #super call)
                self = playerConnection != null && playerConnection.getConnectionState() == ConnectionState.PLAY;
            }
            if (self) {
                sendPacketToViewersAndSelf(getPropertiesPacket());
            } else {
                sendPacketToViewers(getPropertiesPacket());
            }
        }
    }

    /**
     * Retrieves the attribute value.
     *
     * @param attribute the attribute value to get
     * @return the attribute value
     */
    public float getAttributeValue(@NotNull Attribute attribute) {
        AttributeInstance instance = attributeModifiers.get(attribute.getKey());
        return (instance != null) ? instance.getValue() : attribute.getDefaultValue();
    }

    /**
     * Gets if the entity is dead or not.
     *
     * @return true if the entity is dead
     */
    @Override
    public boolean isDead() {
        return isDead;
    }

    /**
     * Gets if the entity is able to pickup items.
     *
     * @return true if the entity is able to pickup items
     */
    @Override
    public boolean canPickupItems() {
        return canPickupItem;
    }

    /**
     * When set to false, the entity will not be able to pick {@link TachyonItemEntity} on the ground.
     *
     * @param canPickupItem can the entity pickup item
     */
    @Override
    public void setCanPickupItems(boolean canPickupItem) {
        this.canPickupItem = canPickupItem;
    }

    @Override
    public boolean addViewer0(@NotNull Player p) {
        TachyonPlayer player = (TachyonPlayer) p;
        if (!super.addViewer0(player)) {
            return false;
        }
        final PlayerConnection playerConnection = player.getPlayerConnection();
        for (EntityEquipmentPacket entityEquipmentPacket : getEquipmentsPacket()) {
            playerConnection.sendPacket(entityEquipmentPacket);
        }
        playerConnection.sendPacket(getPropertiesPacket());
        return true;
    }

    @Override
    public void setBoundingBox(double x, double y, double z) {
        super.setBoundingBox(x, y, z);
        this.expandedBoundingBox = getBoundingBox().expand(1, 0.5f, 1);
    }

    /**
     * Sends a {@link EntityAnimationPacket} to swing the main hand
     * (can be used for attack animation).
     */
    @Override
    public void swingHand() {
        EntityAnimationPacket animationPacket = new EntityAnimationPacket(getEntityId(), EntityAnimationPacket.Animation.SWING_ARM);
        sendPacketToViewers(animationPacket);
    }

    /**
     * Used to change the {@code isDead} internal field.
     *
     * @param isDead the new field value
     */
    protected void refreshIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    /**
     * Gets an {@link EntityPropertiesPacket} for this entity with all of its attributes values.
     *
     * @return an {@link EntityPropertiesPacket} linked to this entity
     */
    @NotNull
    protected EntityPropertiesPacket getPropertiesPacket() {
        // Get all the attributes which should be sent to the client
        final AttributeInstance[] instances = attributeModifiers.values().stream()
                .filter(i -> i.getAttribute().isShared())
                .toArray(AttributeInstance[]::new);



        EntityPropertiesPacket.Property[] properties = new EntityPropertiesPacket.Property[instances.length];
        for (int i = 0; i < properties.length; ++i) {
            EntityPropertiesPacket.Property property = new EntityPropertiesPacket.Property();

            final float value = instances[i].getBaseValue();

            property.instance = instances[i];
            property.attribute = instances[i].getAttribute();
            property.value = value;

            properties[i] = property;
        }
        return new EntityPropertiesPacket(getEntityId(), properties);
    }

    /**
     * All attributes that should be registered on every living entity.
     */
    private static final Attribute[] BASE_ATTRIBUTES = new Attribute[]{
            Attribute.MAX_HEALTH,
            Attribute.KNOCKBACK_RESISTANCE,
            Attribute.MOVEMENT_SPEED,
            Attribute.ATTACK_DAMAGE
    };

    /**
     * Sets all the attributes on a base living entity.
     */
    protected void setupAttributes() {
        for (Attribute attribute : BASE_ATTRIBUTES) {
            final AttributeInstance attributeInstance = new AttributeInstance(attribute, this::onAttributeChanged);
            this.attributeModifiers.put(attribute.getKey(), attributeInstance);
        }
    }

    @Override
    protected void handleVoid() {
        // Kill if in void
        if (getInstance().isInVoid(this.position)) {
            damage(DamageType.VOID, 10f);
        }
    }

    /**
     * Gets the time in ms between two fire damage applications.
     *
     * @return the time in ms
     * @see #setFireDamagePeriod(long, TimeUnit)
     */
    public long getFireDamagePeriod() {
        return fireDamagePeriod;
    }


    /**
     * {@inheritDoc}
     */
    public void setFireDamagePeriod(long fireDamagePeriod, @NotNull TimeUnit timeUnit) {
        fireDamagePeriod = timeUnit.toMilliseconds(fireDamagePeriod);
        this.fireDamagePeriod = fireDamagePeriod;
    }

    /**
     * Changes the {@link Team} for the entity.
     *
     * @param team The new team
     */
    public void setTeam(Team team) {
        if (this.team == team) return;

        String member;

        if (this instanceof TachyonPlayer player) {
            member = player.getUsername();
        } else {
            member = this.uuid.toString();
        }

        if (this.team != null) {
            this.team.removeMember(member);
        }

        this.team = team;
        if (team != null) {
            team.addMember(member);
        }
    }

    /**
     * Gets the {@link Team} of the entity.
     *
     * @return the {@link Team}
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Gets the line of sight in {@link net.tachyon.coordinate.Point} of the entity.
     *
     * @param maxDistance The max distance to scan
     * @return A list of {@link Point} in this entities line of sight
     */
    public List<Point> getLineOfSight(int maxDistance) {
        List<Point> blocks = new ArrayList<>();
        Iterator<Point> it = new BlockIterator(this, maxDistance);
        while (it.hasNext()) {
            Point position = it.next();
            if (Block.fromStateId(getInstance().getBlockStateId(position)) != Block.AIR) blocks.add(position);
        }
        return blocks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasLineOfSight(Entity entity) {
        Vec start = getPosition().toVector().add(0D, getEyeHeight(), 0D);
        Vec end = entity.getPosition().toVector().add(0D, getEyeHeight(), 0D);
        Vec direction = end.subtract(start);
        int maxDistance = (int) Math.ceil(direction.length());

        BlockIterator it = new BlockIterator(start, direction.normalize(), 0D, maxDistance);
        while (it.hasNext()) {
            Block block = Block.fromStateId(getInstance().getBlockStateId(it.next()));
            if (!block.isAir() && !block.isLiquid()) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public Point getTargetBlockPosition(int maxDistance) {
        Iterator<Point> it = new BlockIterator(this, maxDistance);
        while (it.hasNext()) {
            Point position = it.next();
            if (Block.fromStateId(getInstance().getBlockStateId(position)) != Block.AIR) return position;
        }
        return null;
    }

    /**
     * Gets {@link TachyonEntityMeta} of this entity casted to {@link TachyonLivingEntityMeta}.
     *
     * @return null if meta of this entity does not inherit {@link TachyonLivingEntityMeta}, casted value otherwise.
     */
    public TachyonLivingEntityMeta getLivingEntityMeta() {
        if (this.entityMeta instanceof TachyonLivingEntityMeta) {
            return (TachyonLivingEntityMeta) this.entityMeta;
        }
        return null;
    }

}
