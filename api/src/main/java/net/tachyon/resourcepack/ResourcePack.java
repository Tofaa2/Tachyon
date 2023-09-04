package net.tachyon.resourcepack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record ResourcePack(String url, String hash) {

    public ResourcePack(@NotNull String url, @Nullable String hash) {
        this.url = url;
        // Optional, set to empty if null
        this.hash = hash == null ? "" : hash;
    }

    /**
     * Gets the resource pack URL.
     *
     * @return the resource pack URL
     */
    public @NotNull String getUrl() {
        return url;
    }

    /**
    * Gets the resource pack hash.
    * <p>
    * WARNING: if null or empty, the player will probably waste bandwidth by re-downloading
    * the resource pack.
    *
    * @return the resource pack hash, can be empty
    */
    public @NotNull String getHash() {
        return hash;
    }
}
