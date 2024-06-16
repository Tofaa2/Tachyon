namespace Tachyon;

public record TachyonConfig(

    int WorkerThreadCount,
    string Host,
    int Port,
    int MaxPlayers,
    bool HideOnline,
    int PacketSizeLimit
)
{ }