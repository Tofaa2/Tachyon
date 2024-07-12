using System.Reflection;

namespace Server.Util;

public static class ResourceHelper
{


    public static string GetResourceAsString(string path)
    {
        var asm = Assembly.GetExecutingAssembly();
        using var stream = asm.GetManifestResourceStream(path);
        Check.NotNull(stream, "Stream is null");
        using StreamReader reader = new(stream!);
        return reader.ReadToEnd();
    }


}
