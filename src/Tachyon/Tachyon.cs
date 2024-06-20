using System.Net;
using Tachyon.Nbt;
using Tachyon.Network.Connection;

namespace Tachyon;

public class Tachyon
{

    public static readonly int ProtocolVersion = 767;
    public static readonly string Version = "1.21";


    private static volatile Server? _instance;
    public static IServer? Process => _instance;

    public static ConnectionManager ConnectionManager => _instance!.Connection;
    
    public static void Init()
    {
        if (_instance != null)
        {
            throw new InvalidOperationException("Server already initialized");
        }

        Server instance = new();
        instance.Init();
        _instance = instance;
    }

    public static void Start(string host, int port)
    {
        Start(IPAddress.Parse(host), port);
    }

    public static void Start(IPAddress address, int port)
    {
        if (_instance == null)
        {
            throw new InvalidOperationException("Server not initialized, call Tachyon#Init() first");
        }
        _instance!.Start(address, port);

    }
    
    public static void Stop()
    {
        if (_instance == null)
        {
            throw new InvalidOperationException("Server not initialized, call Tachyon#Init() first");
        }
        _instance!.Stop();
    }
    

}