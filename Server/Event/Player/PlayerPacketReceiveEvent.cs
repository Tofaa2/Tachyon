using Server.Event.Types;
using Server.Network.Connection;
using Server.Network.Packet;

namespace Server.Event.Player;

public class PlayerPacketReceiveEvent(PlayerConnection player, IServerPacket packet) : ICancellableEvent
{
    public PlayerConnection PlayerConnection { get; } = player;
    public IServerPacket Packet { get; } = packet;
    public bool IsCancelled { get; set; }
}