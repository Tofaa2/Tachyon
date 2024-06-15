namespace Tachyon.Network.Binary;

public interface IReadable
{

    void Read(NetworkBuffer reader);

}

public interface IWritable
{

    void Write(NetworkBuffer writer);

}