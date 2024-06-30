using System.Text.Json;
using System.Text.Json.Nodes;
using Server.Chat.Text;

namespace Server.Ping;

public class ServerListPingResponse
{

    public string Version { get; set; } = MinecraftConstants.VERSION_NAME;
    public int Protocol { get; set; } = MinecraftConstants.PROTOCOL_VERSION;
    public int MaxPlayers { get; set; } = 10000;
    public int OnlinePlayers { get; set; } = 0;

    public IComponent Motd { get; set; } = IComponent.Text("A Tachyon Server!");
    public string Favicon { get; set; } = "";
    public bool PlayersHidden { get; set; } = false;
    public IList<ServerListSampleEntry> SamplePlayers { get; } = new List<ServerListSampleEntry>();

    public void AddSamplePlayer(ServerListSampleEntry samplePlayer)
    {
        SamplePlayers.Add(samplePlayer);
    }

    public void AddSamplePlayer(Guid uuid, string username)
    {
        AddSamplePlayer(new ServerListSampleEntry(uuid, username));
    }


    public override string ToString()
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
        response.Add("description", Motd.GetRawJson());
        return response.ToJsonString();
    }


}