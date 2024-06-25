namespace Server.Network.Packet;

public interface IPacket
{
    public string ToString()
    {
        return this.GetType().Name;
    }
    
}