package net.tachyon.network.packet.client.handler;

import net.tachyon.network.packet.client.play.*;

public class ClientPlayPacketsHandler extends ClientPacketsHandler {

    public ClientPlayPacketsHandler() {
        register(0x00, ClientKeepAlivePacket::new);
        register(0x01, ClientChatMessagePacket::new);
        register(0x02, ClientInteractEntityPacket::new);
        register(0x03, ClientPlayerPacket::new);
        register(0x04, ClientPlayerPositionPacket::new);
        register(0x05, ClientPlayerLookPacket::new);
        register(0x06, ClientPlayerPositionAndLookPacket::new);
        register(0x07, ClientPlayerDiggingPacket::new);
        register(0x08, ClientPlayerBlockPlacementPacket::new);
        register(0x09, ClientHeldItemChangePacket::new);
        register(0x0A, ClientAnimationPacket::new);
        register(0x0B, ClientEntityActionPacket::new);
        register(0x0C, ClientSteerVehiclePacket::new);
        register(0x0D, ClientCloseWindow::new);
        register(0x0E, ClientClickWindowPacket::new);
        register(0x0F, ClientWindowConfirmationPacket::new);
        register(0x10, ClientCreativeInventoryActionPacket::new);
        register(0x11, ClientEnchantItemPacket::new);
        register(0x12, ClientUpdateSignPacket::new);
        register(0x13, ClientPlayerAbilitiesPacket::new);
        register(0x14, ClientTabCompletePacket::new);
        register(0x15, ClientSettingsPacket::new);
        register(0x16, ClientStatusPacket::new);
        register(0x17, ClientPluginMessagePacket::new);
        register(0x18, ClientSpectatePacket::new);
        register(0x19, ClientSpectatePacket::new);
        register(0x20, ClientResourcePackStatusPacket::new);
    }
}
