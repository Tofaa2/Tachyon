using System.Net;

namespace Tachyon;

internal static class Program
{

    internal static void Main(string[] args)
    {
        Tachyon.Init();
        Tachyon.Start(IPAddress.Any, 25565);
    }
    
    
}