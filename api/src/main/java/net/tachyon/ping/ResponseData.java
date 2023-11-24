package net.tachyon.ping;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import net.tachyon.Server;
import net.tachyon.chat.Adventure;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents the data sent to the player when refreshing the server list.
 *
 * <p>Filled by {@link ResponseDataConsumer} before being sent to the player by {@link Server#getResponseDataConsumer()}.
 */
public class ResponseData {

    private final JsonObject jsonObject;

    private final JsonObject versionObject;
    private final JsonObject playersObject;
    private final JsonArray sampleArray;
    private final JsonObject descriptionObject;
    private final List<PingPlayer> pingPlayers;
    private String name;
    private int protocol;
    private int maxPlayer;
    private int online;
    private Component description;

    private String favicon;

    /**
     * Constructs a new {@link ResponseData}.
     */
    public ResponseData() {
        this.jsonObject = new JsonObject();
        this.versionObject = new JsonObject();
        this.playersObject = new JsonObject();
        this.sampleArray = new JsonArray();
        this.descriptionObject = new JsonObject();
        this.pingPlayers = new ArrayList<>();
    }

    /**
     * Sets the name for the response.
     *
     * @param name The name for the response data.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the response protocol version.
     *
     * @param protocol The protocol version for the response data.
     */
    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    /**
     * Sets the response maximum player count.
     *
     * @param maxPlayer The maximum player count for the response data.
     */
    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    /**
     * Sets the response online count.
     *
     * @param online The online count for the response data.
     */
    public void setOnline(int online) {
        this.online = online;
    }

    /**
     * Adds a player to the response.
     *
     * @param name The name of the player.
     * @param uuid The unique identifier of the player.
     */
    public void addPlayer(String name, UUID uuid) {
        PingPlayer pingPlayer = new PingPlayer();
        pingPlayer.name = name;
        pingPlayer.uuid = uuid;
        this.pingPlayers.add(pingPlayer);
    }

    /**
     * Removes all the ping players from this {@link #pingPlayers}. The {@link #pingPlayers} list
     * will be empty this call returns.
     */
    public void clearPlayers() {
        this.pingPlayers.clear();
    }

    /**
     * Sets the response description.
     *
     * @param description The description for the response data.
     */
    public void setDescription(String description) {
        this.description = Component.text(description);
    }

    public void setDescription(@NotNull Component description) {
        this.description = description;
    }

    /**
     * Sets the response favicon.
     *
     * @param favicon The favicon for the response data.
     */
    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    /**
     * Converts the response data into a {@link JsonObject}.
     *
     * @return The converted response data as a json tree.
     */
    @NotNull
    public JsonObject build() {
        versionObject.addProperty("name", name);
        versionObject.addProperty("protocol", protocol);

        playersObject.addProperty("max", maxPlayer);
        playersObject.addProperty("online", online);

        for (PingPlayer pingPlayer : pingPlayers) {
            JsonObject pingPlayerObject = new JsonObject();
            pingPlayerObject.addProperty("name", pingPlayer.name);
            pingPlayerObject.addProperty("id", pingPlayer.uuid.toString());
            sampleArray.add(pingPlayerObject);
        }
        playersObject.add("sample", sampleArray);

        descriptionObject.addProperty("text", Adventure.COMPONENT_SERIALIZER.serialize(description));

        jsonObject.add("version", versionObject);
        jsonObject.add("players", playersObject);
        jsonObject.add("description", descriptionObject);
        jsonObject.addProperty("favicon", favicon);
        return jsonObject;
    }

    /**
     * Represents a player line in the server list hover.
     */
    private static class PingPlayer {
        private String name;
        private UUID uuid;
    }
}
