using System.Text.Json.Nodes;
using Tachyon.Network.Packet.Status.Server;

namespace Tachyon.Ping;

public class ServerListPingData
{

    public string VersionName;
    public int Protocol;
    public int MaxPlayers;
    public int OnlinePlayers;
    public string Favicon;
    public string Description;
    public bool HidePlayers;

    public ServerListPingData()
    {
        VersionName = TachyonServer.SERVER_VERSION;
        Protocol = TachyonServer.PROTOCOL_VERSION;
        MaxPlayers = 0;
        OnlinePlayers = 0;
        HidePlayers = false;
        Favicon = "";
        Description = "{\"text\":\"A Tachyon Server!\"}";
    }

    public JsonObject ToJsonObject()
    {
        JsonObject versionObj = new JsonObject();
        versionObj.Add("name", VersionName);
        versionObj.Add("protocol", Protocol);

        JsonObject playersObj = null;
        if (!HidePlayers)
        {
            playersObj = new JsonObject();
            playersObj.Add("max", MaxPlayers);
            playersObj.Add("online", OnlinePlayers);
        }
        
        JsonObject finalObj = new JsonObject();
        finalObj.Add("version", versionObj);
        finalObj.Add("players", playersObj);
        finalObj.Add("favicon", Favicon);
        finalObj.Add("description", Description);

        return finalObj;
    }

}