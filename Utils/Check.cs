namespace Tachyon.Utils;

public static class Check
{

    public static void State(bool boolean, string message)
    {
        if (boolean)
        {
            throw new ArgumentException(message);
        }
    }


}
