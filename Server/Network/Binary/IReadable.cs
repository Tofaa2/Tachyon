namespace Server.Network.Binary;

public interface IReadable
{

    void Read(BinaryBuffer buffer);
    
}