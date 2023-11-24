package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.entity.Player;
import net.tachyon.network.packet.client.ClientPlayPacket;
import org.jetbrains.annotations.NotNull;

public class ClientSettingsPacket extends ClientPlayPacket {

    public String locale;
    public byte viewDistance;
    public Player.ChatMode chatMode;
    public boolean chatColors;
    public byte displayedSkinParts;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.locale = reader.readSizedString(128);
        this.viewDistance = reader.readByte();
        this.chatMode = Player.ChatMode.values()[reader.readVarInt()];
        this.chatColors = reader.readBoolean();
        this.displayedSkinParts = reader.readByte();
    }
}
