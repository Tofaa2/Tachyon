using Tachyon.Network;
using Tachyon.Network.Packet;

namespace Tachyon;

public static class Tachyon
{

    internal static void Main(string[] args)
    {
        PacketRegistry.Init();
        NettyServer.init();
        NettyServer.Start();
    }



}