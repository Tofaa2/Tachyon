using System.Text.Json.Nodes;
using Org.BouncyCastle.Tls;

namespace Tachyon.Ping;

public class ServerListPingResponse
{

    public string Version { get; set; }
    public int Protocol { get; set; }
    public int MaxPlayers { get; set; }
    public int OnlinePlayers { get; set; }
    public string JsonMotd { get; set; }
    public string Favicon { get; set; }
    public bool PlayersHidden { get; set; }
    public IList<ServerListSampleEntry> SamplePlayers { get; private set; }

    public ServerListPingResponse()
    {
        Version = Tachyon.Version;
        Protocol = Tachyon.ProtocolVersion;
        Favicon = "";
        PlayersHidden = false;
        OnlinePlayers = 0;
        MaxPlayers = 10000;
        JsonMotd = "A Tachyon Server!";
        SamplePlayers = new List<ServerListSampleEntry>();
    }
    
    public void AddSamplePlayer(ServerListSampleEntry samplePlayer)
    {
        SamplePlayers.Add(samplePlayer);
    }

    public void AddSamplePlayer(Guid uuid, string username)
    {
        AddSamplePlayer(new ServerListSampleEntry(uuid, username));
    }


    public string ToString()
    {
        JsonObject versionObject = new();
        versionObject.Add("name", Version);
        versionObject.Add("protocol", Protocol);

        JsonObject? playersObject = null;
        if (!PlayersHidden)
        {
            playersObject = new();
            playersObject.Add("max", MaxPlayers);
            playersObject.Add("online", OnlinePlayers);
            
            JsonArray samplePlayersArray = new();
            foreach (ServerListSampleEntry samplePlayer in SamplePlayers)
            {
                JsonObject samplePlayerObject = new();
                samplePlayerObject.Add("id", samplePlayer.UniqueId.ToString());
                samplePlayerObject.Add("name", samplePlayer.Username);
                samplePlayersArray.Add(samplePlayerObject);
            }
            playersObject.Add("sample", samplePlayersArray);
        }
        
        JsonObject response = new();
        response.Add("version", versionObject);
        response.Add("players", playersObject);
        response.Add("favicon", Favicon);
        response.Add("description", JsonMotd);

        return response.ToJsonString();
    }


}