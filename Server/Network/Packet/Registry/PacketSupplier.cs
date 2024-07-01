using System.Collections.Concurrent;
using Server.Network.Binary;

namespace Server.Network.Packet.Registry;

internal abstract class PacketSupplier
{
    
    private volatile int _nextId = -1;
    private ConcurrentDictionary<int, Func<IClientPacket>> _clientPackets = new();
    private ConcurrentDictionary<System.Type, int> _serverPackets = new();
    
    
    public int? GetServerPacketId<T>() where T : IServerPacket
    {
        return _serverPackets[typeof(T)];
    }
    
    public int? GetServerPacketId(System.Type type) => _serverPackets[type];
    
    public IClientPacket? GetClientPacket(int id)
    {
        if (id < 0 || id >= _clientPackets.Count)
            return null;
        return _clientPackets[id]();
    }

    public IClientPacket? GetClientPacket(int id, BinaryBuffer reader)
    {
        var packet = GetClientPacket(id);
        if (packet == null)
            return null;
        packet.Read(reader);
        return packet;
    }
    
    public void RegisterServer<T>(int id) where T : IServerPacket
    {
        _serverPackets[typeof(T)] = id;
    }
    
    public void RegisterClient(int id, Func<IClientPacket> supplier)
    {
        _clientPackets[id] = supplier;
    }
    
    
    protected int NextId()
    {
        return Interlocked.Increment(ref _nextId);
    }

}