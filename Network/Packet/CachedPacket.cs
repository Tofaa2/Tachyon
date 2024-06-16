using DotNetty.Buffers;

namespace Tachyon.Network.Packet;

public class CachedPacket : IGenericPacket
{
    
    private WeakReference<CachedFramedPacket>? packet= null;
    private readonly Func<IPacket> supplier;

    public IPacket Packet()
    {
        var cache = UpdatedCache();
        if (cache == null)
        {
            return supplier();
        }

        return cache.packet;
    }

    public IByteBuffer? Body()
    {
        var cache = UpdatedCache();
        return cache?.body;
    }
    
/*
 * 
    private fun updatedCache(): CachedFramedPacket? {
        val ref = packet
        var cached: CachedFramedPacket? = null
        if (ref == null || ref.get().also { cached = it } == null) {
            val updatedPacket = supplier.get()
            cached = CachedFramedPacket(updatedPacket, PacketFraming.frame(updatedPacket))
            packet = SoftReference(cached)
        }
        return cached
    }
    ported to c#
 */

    private CachedFramedPacket? UpdatedCache()
    {
        var reference = packet;
        CachedFramedPacket? cached = null;
        if (reference == null || reference.TryGetTarget(out cached) == false)
        {
            var updatedPacket = supplier();
            cached = new CachedFramedPacket(updatedPacket, PacketFraming.Frame(updatedPacket));
            packet = new WeakReference<CachedFramedPacket?>(cached);
        }

        return cached;
    }


}

public record CachedFramedPacket(IPacket packet, IByteBuffer body);
