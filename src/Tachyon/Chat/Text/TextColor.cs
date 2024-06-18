namespace Tachyon.Chat.Text;

public abstract class TextColor 
{
    
    public static TextColor FromRgb(byte r, byte g, byte b) => new RgbColor(r, g, b);
    
    public readonly int R;
    public readonly int G;
    public readonly int B;
    
    protected TextColor(int r, int g, int b)
    {
        R = r;
        G = g;
        B = b;
    }
    
    public abstract string ToString();

}

public class NamedTextColor : TextColor
{
    
    public static readonly NamedTextColor Black = new NamedTextColor("black", 0, 0, 0);
    public static readonly NamedTextColor DarkBlue = new NamedTextColor("dark_blue", 0, 0, 170);
    public static readonly NamedTextColor DarkGreen = new NamedTextColor("dark_green", 0, 170, 0);
    public static readonly NamedTextColor DarkAqua = new NamedTextColor("dark_aqua", 0, 170, 170);
    public static readonly NamedTextColor DarkRed = new NamedTextColor("dark_red", 170, 0, 0);
    public static readonly NamedTextColor DarkPurple = new NamedTextColor("dark_purple", 170, 0, 170);
    public static readonly NamedTextColor Gold = new NamedTextColor("gold", 255, 170, 0);
    public static readonly NamedTextColor Gray = new NamedTextColor("gray", 170, 170, 170);
    public static readonly NamedTextColor DarkGray = new NamedTextColor("dark_gray", 85, 85, 85);
    public static readonly NamedTextColor Blue = new NamedTextColor("blue", 85, 85, 255);
    public static readonly NamedTextColor Green = new NamedTextColor("green", 85, 255, 85);
    public static readonly NamedTextColor Aqua = new NamedTextColor("aqua", 85, 255, 255);
    public static readonly NamedTextColor Red = new NamedTextColor("red", 255, 85, 85);
    public static readonly NamedTextColor LightPurple = new NamedTextColor("light_purple", 255, 85, 255);
    public static readonly NamedTextColor Yellow = new NamedTextColor("yellow", 255, 255, 85);
    public static readonly NamedTextColor White = new NamedTextColor("white", 255, 255, 255);
    
    public static NamedTextColor GetColor(string name)
    {
        return name switch
        {
            "black" => Black,
            "dark_blue" => DarkBlue,
            "dark_green" => DarkGreen,
            "dark_aqua" => DarkAqua,
            "dark_red" => DarkRed,
            "dark_purple" => DarkPurple,
            "gold" => Gold,
            "gray" => Gray,
            "dark_gray" => DarkGray,
            "blue" => Blue,
            "green" => Green,
            "aqua" => Aqua,
            "red" => Red,
            "light_purple" => LightPurple,
            "yellow" => Yellow,
            "white" => White,
            _ => throw new ArgumentException("Invalid color name")
        };
    }

    private readonly string _name;
    
    private NamedTextColor(string name, int r, int g, int b) : base(r, g, b)
    {
        
        _name = name;
    }

    public override string ToString()
    {
        return _name;
    }
}


internal class RgbColor : TextColor
{
    public RgbColor(int r, int g, int b) : base(r, g, b)
    {
    }

    public override string ToString()
    {
        return $"#{R:X2}{G:X2}{B:X2}";
    }
}
