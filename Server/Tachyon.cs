using System.Net;
using Server.Event;
using Server.Network.Connection;
using slf4net;
using Tachyon;

namespace Server;

public class Tachyon
{

    public static readonly ILogger LOGGER = LoggerFactory.GetLogger(typeof(Tachyon));

    private static volatile Server? _instance;
    public static IServer? Process => _instance;

    public static ConnectionManager ConnectionManager => _instance!.Connection;
    public static IEventNode<IEvent> EventHandler => _instance!.EventHandler; 
    
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