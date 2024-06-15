
namespace Tachyon;

public class NamespacedKey {

    public static NamespacedKey Minecraft(string key)
    {
        return new NamespacedKey("minecraft", key);
    }

    public static NamespacedKey FromString(string input)
    {
        string[] split = input.Split(':');
        if (split.Length != 2)
        {
            throw new ArgumentException("Invalid path provided for namespace");
        }
        return new NamespacedKey(split[0], split[1]);
    }

    public static NamespacedKey New(string path, string key) {
        return new NamespacedKey(path, key);
    }
    
    public readonly string Key;
    public readonly string Path;
    public readonly string Full;

    private NamespacedKey(string path, string key)
    {
        Key = key;
        Path = path;
        Full = path + ":" + key;
    }

}
