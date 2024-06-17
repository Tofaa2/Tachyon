using System.Collections.Concurrent;
using Tachyon.Network.Packet.Type.Login.Server;
using Tachyon.Util;

namespace Tachyon.Network.PluginMessage;

public class LoginPluginMessageProcessor(PlayerConnection connection)
{

    private static volatile int RequestId;

    private readonly ConcurrentDictionary<int, LoginPluginRequest> _requestByMsgId = new();

    public CompletableFuture<LoginPluginResponse> Request(string channel, byte[]? payload)
    {
        LoginPluginRequest request = new(channel, payload);
        int id = Interlocked.Increment(ref RequestId);
        _requestByMsgId.TryAdd(id, request);
        connection.SendPacketNow(new ServerLoginPluginRequestPacket(id, request.Channel, request.RequestPayload));
        return request.ResponseFuture;
    }

    public void HandleResponse(int messageId, byte[] data)
    {
        if (!_requestByMsgId.TryRemove(messageId, out LoginPluginRequest? request))
        {
            throw new InvalidOperationException($"No request found for message id {messageId}");
        }

        try
        {
        }
        catch (Exception e)
        {
            throw new InvalidOperationException("Failed to handle response", e);
        }
    }

    public void AwaitReplies(long timeout)
    {
        if (_requestByMsgId.IsEmpty) return;
        
    }
    

}