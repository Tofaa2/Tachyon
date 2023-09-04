package net.tachyon.potion;

import org.jetbrains.annotations.NotNull;

public record TimedPotion(Potion potion, long startingTime) {

    public TimedPotion(@NotNull Potion potion, long startingTime) {
        this.potion = potion;
        this.startingTime = startingTime;
    }

    @NotNull
    public Potion getPotion() {
        return potion;
    }

    public long getStartingTime() {
        return startingTime;
    }
}
