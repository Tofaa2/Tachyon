package net.tachyon.command.suggestion;

import org.jetbrains.annotations.NotNull;

public class SuggestionEntry {

    private final String entry;

    public SuggestionEntry(@NotNull String entry) {
        this.entry = entry;
    }

    @NotNull
    public String getEntry() {
        return entry;
    }
}
