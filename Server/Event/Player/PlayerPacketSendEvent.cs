using Server.Event.Types;
using Server.Network.Connection;
using Server.Network.Packet;

namespace Server.Event.Player;

public class PlayerPacketSendEvent(PlayerConnection player, IClientPacket packet) : ICancellableEvent
{
    public PlayerConnection PlayerConnection { get; } = player;
    public IClientPacket Packet { get; } = packet;
    public bool IsCancelled { get; set; }
}