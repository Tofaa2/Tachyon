using Server.Util;

namespace Server.Network.PluginMessage;

public class LoginPluginRequest
{

    public readonly string Channel;
    public readonly byte[]? RequestPayload;
    public CompletableFuture<LoginPluginResponse> ResponseFuture = new();

    public LoginPluginRequest(string channel, byte[]? requestPayload)
    {
        Channel = channel;
        RequestPayload = requestPayload;
    }
}