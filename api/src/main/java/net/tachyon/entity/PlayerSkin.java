package net.tachyon.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.tachyon.utils.mojang.MojangUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Contains all the data required to store a skin.
 * <p>
 * Can be applied to a player with {@link Player#setSkin(PlayerSkin)}
 * or in the linked event {@link PlayerSkinInitEvent}.
 */
public record PlayerSkin(@NotNull String textures, @NotNull String signature) {

    public PlayerSkin(String textures, String signature) {
        this.textures = textures;
        this.signature = signature;
    }

    /**
     * Gets a skin from a Mojang UUID.
     *
     * @param uuid Mojang UUID
     * @return a player skin based on the UUID, null if not found
     */
    @Nullable
    public static PlayerSkin fromUuid(@NotNull String uuid) {
        final JsonObject jsonObject = MojangUtils.fromUuid(uuid);
        final JsonArray propertiesArray = jsonObject.get("properties").getAsJsonArray();

        for (JsonElement jsonElement : propertiesArray) {
            final JsonObject propertyObject = jsonElement.getAsJsonObject();
            final String name = propertyObject.get("name").getAsString();
            if (!name.equals("textures"))
                continue;
            final String textureValue = propertyObject.get("value").getAsString();
            final String signatureValue = propertyObject.get("signature").getAsString();
            return new PlayerSkin(textureValue, signatureValue);
        }
        return null;
    }

    /**
     * Gets a skin from a Minecraft username.
     *
     * @param username the Minecraft username
     * @return a skin based on a Minecraft username, null if not found
     */
    @Nullable
    public static PlayerSkin fromUsername(@NotNull String username) {
        final JsonObject jsonObject = MojangUtils.fromUsername(username);
        final String uuid = jsonObject.get("id").getAsString();
        // Retrieve the skin data from the mojang uuid
        return fromUuid(uuid);
    }

    /**
     * Gets the skin textures value.
     *
     * @return the textures value
     */
    public String getTextures() {
        return textures;
    }

    /**
     * Gets the skin signature.
     *
     * @return the skin signature
     */
    public String getSignature() {
        return signature;
    }

    @Override
    public String toString() {
        return "PlayerSkin{" +
                "textures='" + textures + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PlayerSkin that = (PlayerSkin) object;
        return Objects.equals(textures, that.textures) &&
                Objects.equals(signature, that.signature);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(textures, signature);
    }

}
