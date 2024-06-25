namespace Server.Network.PluginMessage;

public record LoginPluginResponse(string Channel, bool Understood, byte[]? Payload)
{

    public static LoginPluginResponse FromPayload(string channel, byte[]? payload)
    {
        return new LoginPluginResponse(channel, payload != null, payload);
    }

}