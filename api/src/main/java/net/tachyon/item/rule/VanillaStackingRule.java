package net.tachyon.item.rule;

import net.tachyon.item.ItemStack;
import net.tachyon.utils.MathUtils;
import org.jetbrains.annotations.NotNull;

public class VanillaStackingRule extends StackingRule {

    public VanillaStackingRule(int maxSize) {
        super(maxSize);
    }

    @Override
    public boolean canBeStacked(@NotNull ItemStack item1, @NotNull ItemStack item2) {
        return item1.isSimilar(item2);
    }

    @Override
    public boolean canApply(@NotNull ItemStack item, int newAmount) {
        return MathUtils.isBetween(newAmount, 0, getMaxSize());
    }

    @NotNull
    @Override
    public ItemStack apply(@NotNull ItemStack item, int newAmount) {
        if (newAmount <= 0) return ItemStack.AIR;
        return item.withAmount((byte) newAmount);
    }

    @Override
    public int getAmount(@NotNull ItemStack itemStack) {
        return itemStack.amount();
    }
}
