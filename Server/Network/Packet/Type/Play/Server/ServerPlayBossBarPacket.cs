using Server.BossBar;
using Server.Chat.Text;
using Server.Network.Binary;
using static Server.Network.Binary.BinaryBuffer;
namespace Server.Network.Packet.Type.Play.Server;

public record ServerPlayBossBarPacket(Guid Uuid, ServerPlayBossBarPacket.IAction Action) : IServerPacket
{

    public interface IAction;
    public record Add(BossBar.BossBar Bar) : IAction;
    public record Remove() : IAction;
    public record UpdateHealth(float Health) : IAction;
    public record UpdateTitle(IComponent Title) : IAction;
    public record UpdateStyle(BossBarDivision Division, BossBarColor Color) : IAction;

    public record UpdateFlags(byte Flags) : IAction
    {
        public UpdateFlags(BossBarFlags[] flags) : this(flags.Aggregate<BossBarFlags, byte>(0, (current, flag) => (byte)(current | (byte)flag))){ }  
    }
    
    public void Write(BinaryBuffer writer)
    {
        writer.Write(UUID, Uuid);
        switch (Action)
        {
            case Add add:
                var bar = add.Bar;
                writer.Write(VAR_INT, 0);
                writer.Write(TEXT_COMPONENT, bar.Title);
                writer.Write(FLOAT, bar.Health);
                writer.Write(VAR_INT, (int) bar.Color);
                writer.Write(VAR_INT, (int) bar.Division);
                writer.Write(VAR_INT, bar.Flags);
                break;
            case Remove:
                writer.Write(VAR_INT, 1);
                break;
            case UpdateHealth updateHealth:
                writer.Write(VAR_INT, 2);
                writer.Write(FLOAT, updateHealth.Health);
                break;
            case UpdateTitle updateTitle:
                writer.Write(VAR_INT, 3);
                writer.Write(TEXT_COMPONENT, updateTitle.Title);
                break;
            case UpdateStyle updateStyle:
                writer.Write(VAR_INT, 4);
                writer.Write(VAR_INT, (int)updateStyle.Color);
                writer.Write(VAR_INT, (int) updateStyle.Division);
                break;
            case UpdateFlags updateFlags:
                writer.Write(VAR_INT, 5);
                writer.Write(VAR_INT, updateFlags.Flags);
                break;
        }
    }
}