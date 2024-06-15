

using System.Diagnostics;

namespace Tachyon;

internal static class Program
{

    static void Main(string[] args)
    {
        var config = new TachyonConfig(10, "localhost", 25565, 10000, false, 30000);
        var server =new TachyonServer(config);
        
        server.Init();
        server.Start();
        Task consoleTask = new Task(ReadConsole);
        consoleTask.Wait();
    }

    private static void ReadConsole()
    {
        var running = true;
        while (running)
        {
            var input = Console.ReadLine();
            if (input != null)
            {
                Console.WriteLine("received from console: " + input);
            }
            if (input == "stop")
            {
                running = false;
                Console.WriteLine("Stopping");
            }
        }
    }

}