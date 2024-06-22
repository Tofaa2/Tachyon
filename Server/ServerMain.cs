namespace Server;

public class ServerMain
{
    private readonly string _name;

    public ServerMain(string name)
    {
        _name = name;
    }
    
    public string GetName()
    {
        return _name;
    }
}