package net.tachyon.command.builder;

import net.kyori.adventure.text.format.TextColor;
import net.tachyon.command.builder.arguments.Argument;
import net.tachyon.entity.EntityType;
import net.tachyon.block.Block;
import net.tachyon.item.Enchantment;
import net.tachyon.item.ItemStack;
import net.tachyon.particle.Particle;
import net.tachyon.potion.PotionEffect;
import net.tachyon.utils.entity.EntityFinder;
import net.tachyon.utils.location.RelativeBlockPosition;
import net.tachyon.utils.location.RelativeVec;
import net.tachyon.utils.math.FloatRange;
import net.tachyon.utils.math.IntRange;
import net.tachyon.utils.time.UpdateOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.hephaistos.nbt.NBT;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import java.util.HashMap;
import java.util.Map;

/**
 * @deprecated renamed to {@link CommandContext}
 */
@Deprecated
public class Arguments {

    protected Map<String, Object> args = new HashMap<>();

    private CommandData returnData;

    @NotNull
    public <T> T get(@NotNull Argument<T> argument) {
        return get(argument.getId());
    }

    @SuppressWarnings("unchecked")

    public <T> T get(@NotNull String identifier) {
        return (T) args.computeIfAbsent(identifier, s -> {
            throw new NullPointerException(
                    "The argument with the id '" + identifier + "' has no value assigned, be sure to check your arguments id, your syntax, and that you do not change the argument id dynamically.");
        });
    }

    public boolean has(@NotNull Argument<?> argument) {
        return args.containsKey(argument.getId());
    }

    public boolean has(@NotNull String identifier) {
        return args.containsKey(identifier);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    public boolean getBoolean(@NotNull String id) {
        return (boolean) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    public long getLong(@NotNull String id) {
        return (long) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    public int getInteger(@NotNull String id) {
        return (int) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    public double getDouble(@NotNull String id) {
        return (double) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    public float getFloat(@NotNull String id) {
        return (float) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public String getString(@NotNull String id) {
        return (String) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public String getWord(@NotNull String id) {
        return getString(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public String[] getStringArray(@NotNull String id) {
        return (String[]) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public TextColor getColor(@NotNull String id) {
        return (TextColor) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public UpdateOption getTime(@NotNull String id) {
        return (UpdateOption) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public Enchantment getEnchantment(@NotNull String id) {
        return (Enchantment) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public Particle getParticle(@NotNull String id) {
        return (Particle) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public PotionEffect getPotionEffect(@NotNull String id) {
        return (PotionEffect) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public EntityType getEntityType(@NotNull String id) {
        return (EntityType) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public Block getBlockState(@NotNull String id) {
        return (Block) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public IntRange getIntRange(@NotNull String id) {
        return (IntRange) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public FloatRange getFloatRange(@NotNull String id) {
        return (FloatRange) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public EntityFinder getEntities(@NotNull String id) {
        return (EntityFinder) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public ItemStack getItemStack(@NotNull String id) {
        return (ItemStack) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public NBTCompound getNbtCompound(@NotNull String id) {
        return (NBTCompound) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public NBT getNBT(@NotNull String id) {
        return (NBT) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public RelativeBlockPosition getRelativeBlockPosition(@NotNull String id) {
        return (RelativeBlockPosition) getObject(id);
    }

    /**
     * @deprecated use {@link #get(Argument)}.
     */
    @Deprecated
    @NotNull
    public RelativeVec getRelativeVector(@NotNull String id) {
        return (RelativeVec) getObject(id);
    }

    /**
     * @deprecated use {@link #get(String)}.
     */
    @Deprecated
    @NotNull
    public Object getObject(@NotNull String id) {
        return args.computeIfAbsent(id, s -> {
            throw new NullPointerException(
                    "The argument with the id '" + id + "' has no value assigned, be sure to check your arguments id, your syntax, and that you do not change the argument id dynamically.");
        });
    }

    @Nullable
    public CommandData getReturnData() {
        return returnData;
    }

    public void setReturnData(@Nullable CommandData returnData) {
        this.returnData = returnData;
    }

    @NotNull
    public Map<String, Object> getMap() {
        return args;
    }

    /**
     * @deprecated use {@link CommandContext#setArg(String, Object, String)}
     */
    @Deprecated
    public void setArg(@NotNull String id, Object value) {
        this.args.put(id, value);
    }

    public void copy(@NotNull Arguments arguments) {
        this.args = arguments.args;
    }

    protected void clear() {
        this.args.clear();
    }

    protected void retrieveDefaultValues(@Nullable Map<String, Object> defaultValuesMap) {
        if (defaultValuesMap == null)
            return;

        for (Map.Entry<String, Object> entry : defaultValuesMap.entrySet()) {
            final String key = entry.getKey();
            if (!args.containsKey(key))
                this.args.put(key, entry.getValue());
        }

    }
}
