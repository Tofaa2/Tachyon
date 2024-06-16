using System.Runtime.Serialization;

namespace Tachyon.Network;

public enum ConnectionState
{
    HANDSHAKE,
    STATUS,
    LOGIN,
    CONFIGURATION,
    PLAY
    
}