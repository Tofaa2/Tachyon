namespace Tachyon.Common;

public class ShowcaseClass
{
    private readonly string _name;

    public ShowcaseClass(string name)
    {
        _name = name;
    }

    public string GetName()
    {
        return _name;
    }
}