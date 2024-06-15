using Tachyon.Network.Packet;
using Tachyon.Utils;

namespace Tachyon.Network.Registry;

public abstract class ClientPacketRegistry
{
    
    private readonly IObjectArray<Func<IClientPacket>> _array = IObjectArray<Func<IClientPacket>> .SingleThreaded<Func<IClientPacket>>(10);
    private volatile int nextId = 0;

    public void Register(int packetId, Func<IClientPacket> function)
    {
        _array.Set(packetId, function);
    }

    protected int NextId()
    {
        return Interlocked.Increment(ref nextId);
    }
    
    public IClientPacket CreatePacket(int packetId)
    {
        var function = _array.Get(packetId);
        if (function == null)
        {
            throw new Exception("Unknown packet id " + packetId);
        }

        var packet = function();
        return packet;
    }
    
}