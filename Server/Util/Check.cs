namespace Server.Util;

public static class Check
{


    public static void NotNull(object? obj, string message)
    {
        if (obj == null)
        {
            throw new ArgumentNullException(message);
        }
    }

    public static void PostInit(string method)
    {
        if (global::Server.Tachyon.Process != null)
        {
            throw new InvalidOperationException($"Method {method} should not be called after initialization");

        }
    }
    
}