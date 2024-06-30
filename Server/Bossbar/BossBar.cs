using Server.Chat.Text;

namespace Server.BossBar;

public record BossBar(
    Guid Uuid,
    IComponent Title,
    float Health,
    float MaxHealth,
    BossBarColor Color,
    BossBarDivision Division,
    byte Flags
)
{
    
    public BossBar WithTitle(IComponent title) => this with { Title = title };
    public BossBar WithHealth(float health) => this with { Health = health };
    public BossBar WithMaxHealth(float maxHealth) => this with { MaxHealth = maxHealth };
    public BossBar WithColor(BossBarColor color) => this with { Color = color };
    public BossBar WithDivision(BossBarDivision division) => this with { Division = division };
    public BossBar WithFlags(byte flags) => this with { Flags = flags };

    public bool HasFlag(BossBarFlags flag)
    {
        byte flagValue = (byte) flag;
        return (Flags & flagValue) == flagValue;
    }
    
    public BossBar AddFlag(BossBarFlags flag)
    {
        byte flagValue = (byte) flag;
        return this with { Flags = (byte) (Flags | flagValue) };
    }

    public BossBar RemoveFlag(BossBarFlags flag)
    {
        byte flagValue = (byte)flag;
        return this with { Flags = (byte)(Flags & ~flagValue) };
    }
    
    public BossBar WithFlags(BossBarFlags[] flags)
    {
        var newFlags = flags.Aggregate<BossBarFlags, byte>(0, (current, flag) => (byte)(current | (byte)flag));
        return this with { Flags = newFlags };
    }

}